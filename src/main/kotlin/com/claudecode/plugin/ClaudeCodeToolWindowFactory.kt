package com.claudecode.plugin

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import org.jetbrains.plugins.terminal.TerminalView
import javax.swing.JComponent
import javax.swing.Timer

class ClaudeCodeToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val terminalView = TerminalView.getInstance(project)
        val contentFactory = ContentFactory.getInstance()
        
        try {
            val terminalWidget = terminalView.createLocalShellWidget(
                project.basePath ?: System.getProperty("user.home"),
                "Claude Code Terminal"
            )
            
            val content = contentFactory.createContent(
                terminalWidget.component,
                "Claude Code",
                false
            )
            
            toolWindow.contentManager.addContent(content)
            
            // Delay command execution to ensure terminal is fully initialized
            Timer(500) { 
                ApplicationManager.getApplication().invokeLater {
                    if (!project.isDisposed && !Disposer.isDisposed(terminalWidget)) {
                        try {
                            val settings = ClaudeCodeSettings.getInstance()
                            val command = settings.buildCommand()
                            terminalWidget.executeCommand(command)
                        } catch (e: Exception) {
                            // Terminal might be disposed, ignore
                        }
                    }
                }
            }.apply {
                isRepeats = false
                start()
            }
            
            // Register proper disposal
            Disposer.register(toolWindow.disposable, Disposable {
                // Cleanup when tool window is disposed
            })
            
        } catch (e: Exception) {
            // Handle any initialization errors gracefully
            e.printStackTrace()
        }
    }
    
    override fun isApplicable(project: Project): Boolean = true
}