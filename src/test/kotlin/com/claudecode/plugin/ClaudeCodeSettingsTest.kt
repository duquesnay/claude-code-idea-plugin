package com.claudecode.plugin

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test

class ClaudeCodeSettingsTest : BasePlatformTestCase() {
    
    @Test
    fun testDefaultSettings() {
        val settings = ClaudeCodeSettings()
        
        assertTrue("Continue conversation should be true by default", settings.continueConversation)
        assertFalse("Use custom model should be false by default", settings.useCustomModel)
        assertEquals("Default model should be 'sonnet'", "sonnet", settings.customModel)
        assertEquals("Additional flags should be empty by default", "", settings.additionalFlags)
    }
    
    @Test
    fun testBuildCommandWithDefaults() {
        val settings = ClaudeCodeSettings()
        val command = settings.buildCommand()
        
        assertEquals("Default command should be 'claude -c'", "claude -c", command)
    }
    
    @Test
    fun testBuildCommandWithoutContinue() {
        val settings = ClaudeCodeSettings()
        settings.continueConversation = false
        val command = settings.buildCommand()
        
        assertEquals("Command without continue should be 'claude'", "claude", command)
    }
    
    @Test
    fun testBuildCommandWithCustomModel() {
        val settings = ClaudeCodeSettings()
        settings.useCustomModel = true
        settings.customModel = "opus"
        val command = settings.buildCommand()
        
        assertEquals("Command with model should be 'claude -c --model opus'", "claude -c --model opus", command)
    }
    
    @Test
    fun testBuildCommandWithAdditionalFlags() {
        val settings = ClaudeCodeSettings()
        settings.additionalFlags = "--debug --verbose"
        val command = settings.buildCommand()
        
        assertEquals("Command with flags should be 'claude -c --debug --verbose'", "claude -c --debug --verbose", command)
    }
    
    @Test
    fun testBuildCommandComplexScenario() {
        val settings = ClaudeCodeSettings()
        settings.continueConversation = false
        settings.useCustomModel = true
        settings.customModel = "sonnet-20241022"
        settings.additionalFlags = "--debug --add-dir /tmp"
        val command = settings.buildCommand()
        
        assertEquals(
            "Complex command should be built correctly",
            "claude --model sonnet-20241022 --debug --add-dir /tmp",
            command
        )
    }
    
    @Test
    fun testStateManagement() {
        val settings1 = ClaudeCodeSettings()
        settings1.continueConversation = false
        settings1.useCustomModel = true
        settings1.customModel = "test-model"
        settings1.additionalFlags = "--test"
        
        val settings2 = ClaudeCodeSettings()
        settings2.loadState(settings1)
        
        assertEquals(settings1.continueConversation, settings2.continueConversation)
        assertEquals(settings1.useCustomModel, settings2.useCustomModel)
        assertEquals(settings1.customModel, settings2.customModel)
        assertEquals(settings1.additionalFlags, settings2.additionalFlags)
    }
    
    @Test
    fun testEmptyModelHandling() {
        val settings = ClaudeCodeSettings()
        settings.useCustomModel = true
        settings.customModel = ""
        val command = settings.buildCommand()
        
        // Should not include --model flag with empty model
        assertEquals("Command should not include empty model", "claude -c", command)
    }
    
    @Test
    fun testWhitespaceInAdditionalFlags() {
        val settings = ClaudeCodeSettings()
        settings.additionalFlags = "  --debug    --verbose  "
        val command = settings.buildCommand()
        
        assertEquals(
            "Extra whitespace should be handled correctly",
            "claude -c --debug --verbose",
            command
        )
    }
}