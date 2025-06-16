package com.claudecode.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.wm.ToolWindowManager

class ClaudeCodeStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        // Get the tool window manager
        val toolWindowManager = ToolWindowManager.getInstance(project)
        val toolWindow = toolWindowManager.getToolWindow("ClaudeCode")
        
        // Tool window will be automatically restored if it was visible when project closed
        // This is handled by IntelliJ's window state persistence
        
        // Optionally, you can force it to be visible on first run
        toolWindow?.let {
            // IntelliJ automatically restores the previous state
            // If you want to always show it minimized on startup:
            // it.show()
        }
    }
}