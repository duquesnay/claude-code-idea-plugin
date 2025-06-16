package com.claudecode.plugin

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import org.mockito.Mockito.*

class ClaudeCodeSettingsConfigurableTest : BasePlatformTestCase() {
    
    @Test
    fun testDisplayName() {
        val configurable = ClaudeCodeSettingsConfigurable()
        assertEquals("Display name should be 'Claude Code'", "Claude Code", configurable.displayName)
    }
    
    @Test
    fun testCreateComponent() {
        val configurable = ClaudeCodeSettingsConfigurable()
        val component = configurable.createComponent()
        
        assertNotNull("Component should not be null", component)
    }
    
    @Test
    fun testIsModifiedWhenNotChanged() {
        val configurable = ClaudeCodeSettingsConfigurable()
        configurable.createComponent()
        configurable.reset()
        
        assertFalse("Should not be modified after reset", configurable.isModified)
    }
    
    @Test
    fun testIsModifiedWhenChanged() {
        val configurable = ClaudeCodeSettingsConfigurable()
        val component = configurable.createComponent()
        assertNotNull(component)
        
        // Reset to sync with current settings
        configurable.reset()
        
        // Now modify through reflection (since settingsComponent is private)
        val field = configurable.javaClass.getDeclaredField("settingsComponent")
        field.isAccessible = true
        val settingsComponent = field.get(configurable) as ClaudeCodeSettingsComponent
        
        // Change a value
        settingsComponent.continueConversation = !settingsComponent.continueConversation
        
        assertTrue("Should be modified after change", configurable.isModified)
    }
    
    @Test
    fun testApply() {
        val settings = ClaudeCodeSettings.getInstance()
        val originalContinue = settings.continueConversation
        
        try {
            val configurable = ClaudeCodeSettingsConfigurable()
            configurable.createComponent()
            
            // Get the component via reflection
            val field = configurable.javaClass.getDeclaredField("settingsComponent")
            field.isAccessible = true
            val settingsComponent = field.get(configurable) as ClaudeCodeSettingsComponent
            
            // Change values
            settingsComponent.continueConversation = !originalContinue
            settingsComponent.useCustomModel = true
            settingsComponent.customModel = "test-model"
            settingsComponent.additionalFlags = "--test-flag"
            
            // Apply changes
            configurable.apply()
            
            // Verify settings were updated
            assertEquals(!originalContinue, settings.continueConversation)
            assertTrue(settings.useCustomModel)
            assertEquals("test-model", settings.customModel)
            assertEquals("--test-flag", settings.additionalFlags)
            
        } finally {
            // Restore original settings
            settings.continueConversation = originalContinue
            settings.useCustomModel = false
            settings.customModel = "sonnet"
            settings.additionalFlags = ""
        }
    }
    
    @Test
    fun testReset() {
        val settings = ClaudeCodeSettings.getInstance()
        val configurable = ClaudeCodeSettingsConfigurable()
        configurable.createComponent()
        
        // Set specific values in settings
        settings.continueConversation = false
        settings.useCustomModel = true
        settings.customModel = "opus"
        settings.additionalFlags = "--verbose"
        
        // Reset should load these values into the component
        configurable.reset()
        
        // Get the component via reflection
        val field = configurable.javaClass.getDeclaredField("settingsComponent")
        field.isAccessible = true
        val settingsComponent = field.get(configurable) as ClaudeCodeSettingsComponent
        
        // Verify component has the settings values
        assertEquals(false, settingsComponent.continueConversation)
        assertEquals(true, settingsComponent.useCustomModel)
        assertEquals("opus", settingsComponent.customModel)
        assertEquals("--verbose", settingsComponent.additionalFlags)
    }
    
    @Test
    fun testDisposeUIResources() {
        val configurable = ClaudeCodeSettingsConfigurable()
        configurable.createComponent()
        
        // Get the component via reflection before disposal
        val field = configurable.javaClass.getDeclaredField("settingsComponent")
        field.isAccessible = true
        assertNotNull(field.get(configurable))
        
        // Dispose
        configurable.disposeUIResources()
        
        // Verify component is null after disposal
        assertNull(field.get(configurable))
    }
}