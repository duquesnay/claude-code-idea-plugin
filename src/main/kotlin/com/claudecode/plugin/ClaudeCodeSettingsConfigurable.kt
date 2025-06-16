package com.claudecode.plugin

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import javax.swing.*

class ClaudeCodeSettingsConfigurable : Configurable {
    private var settingsComponent: ClaudeCodeSettingsComponent? = null
    
    override fun getDisplayName(): String = "Claude Code"
    
    override fun createComponent(): JComponent? {
        settingsComponent = ClaudeCodeSettingsComponent()
        return settingsComponent?.panel
    }
    
    override fun isModified(): Boolean {
        val settings = ClaudeCodeSettings.getInstance()
        val component = settingsComponent ?: return false
        
        return component.continueConversation != settings.continueConversation ||
               component.useCustomModel != settings.useCustomModel ||
               component.customModel != settings.customModel ||
               component.additionalFlags != settings.additionalFlags
    }
    
    override fun apply() {
        val settings = ClaudeCodeSettings.getInstance()
        val component = settingsComponent ?: return
        
        settings.continueConversation = component.continueConversation
        settings.useCustomModel = component.useCustomModel
        settings.customModel = component.customModel
        settings.additionalFlags = component.additionalFlags
    }
    
    override fun reset() {
        val settings = ClaudeCodeSettings.getInstance()
        val component = settingsComponent ?: return
        
        component.continueConversation = settings.continueConversation
        component.useCustomModel = settings.useCustomModel
        component.customModel = settings.customModel
        component.additionalFlags = settings.additionalFlags
    }
    
    override fun disposeUIResources() {
        settingsComponent = null
    }
}