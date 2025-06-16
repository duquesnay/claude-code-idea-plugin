package com.claudecode.plugin

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "ClaudeCodeSettings",
    storages = [Storage("ClaudeCodePlugin.xml")]
)
@Service
class ClaudeCodeSettings : PersistentStateComponent<ClaudeCodeSettings> {
    var continueConversation: Boolean = false
    var useCustomModel: Boolean = false
    var customModel: String = "sonnet"
    var additionalFlags: String = ""
    
    override fun getState(): ClaudeCodeSettings = this
    
    override fun loadState(state: ClaudeCodeSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
    
    companion object {
        fun getInstance(): ClaudeCodeSettings = service()
    }
    
    fun buildCommand(): String {
        val parts = mutableListOf("claude")
        
        if (continueConversation) {
            parts.add("-c")
        }
        
        if (useCustomModel && customModel.isNotBlank()) {
            parts.add("--model")
            parts.add(customModel)
        }
        
        if (additionalFlags.isNotBlank()) {
            parts.addAll(additionalFlags.split(" ").filter { it.isNotBlank() })
        }
        
        return parts.joinToString(" ")
    }
}