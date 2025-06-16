package com.claudecode.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import org.jetbrains.plugins.terminal.TerminalView
import javax.swing.JComponent

class ClaudeCodeToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val terminalView = TerminalView.getInstance(project)
        val contentFactory = ContentFactory.getInstance()
        
        val terminalWidget = terminalView.createLocalShellWidget(
            project.basePath ?: System.getProperty("user.home"),
            "Claude Code Terminal",
            true
        )
        
        val content = contentFactory.createContent(
            terminalWidget.component,
            "",
            false
        )
        
        toolWindow.contentManager.addContent(content)
        
        terminalWidget.executeCommand("claude-code")
    }
    
    override fun isApplicable(project: Project): Boolean = true
}