package com.claudecode.plugin

import com.intellij.icons.AllIcons
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import com.intellij.ui.content.ContentFactory
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import org.jetbrains.plugins.terminal.TerminalView
import javax.swing.JComponent
import javax.swing.KeyStroke
import javax.swing.Timer

class ClaudeCodeToolWindowFactory : ToolWindowFactory {
    private var sessionCounter = 1
    
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // Add actions to the tool window
        setupToolWindowActions(project, toolWindow)
        
        // Create initial session
        createNewClaudeSession(project, toolWindow)
    }
    
    private fun setupToolWindowActions(project: Project, toolWindow: ToolWindow) {
        val actionGroup = DefaultActionGroup().apply {
            add(NewSessionAction(project, toolWindow))
            addSeparator()
            add(OpenSettingsAction())
        }
        
        if (toolWindow is ToolWindowEx) {
            toolWindow.setAdditionalGearActions(actionGroup)
        }
    }
    
    private fun createNewClaudeSession(project: Project, toolWindow: ToolWindow, tabName: String? = null) {
        val terminalView = TerminalView.getInstance(project)
        val contentFactory = ContentFactory.getInstance()
        
        try {
            val terminalWidget = terminalView.createLocalShellWidget(
                project.basePath ?: System.getProperty("user.home"),
                "Claude Terminal ${sessionCounter}"
            )
            
            val displayName = tabName ?: "Claude ${sessionCounter++}"
            val content = contentFactory.createContent(
                terminalWidget.component,
                displayName,
                true // Make tabs closeable
            )
            
            toolWindow.contentManager.addContent(content)
            toolWindow.contentManager.setSelectedContent(content)
            
            // Schedule command execution after terminal is ready
            ApplicationManager.getApplication().invokeLater {
                // Give the terminal time to fully initialize
                Timer(2000) {
                    ApplicationManager.getApplication().invokeLater {
                        if (!project.isDisposed && !Disposer.isDisposed(terminalWidget)) {
                            try {
                                val settings = ClaudeCodeSettings.getInstance()
                                val command = settings.buildCommand()
                                
                                // Execute the command
                                terminalWidget.executeCommand(command)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }.apply {
                    isRepeats = false
                    start()
                }
            }
            
            // Register proper disposal
            Disposer.register(content, Disposable {
                // Cleanup when content is disposed
            })
            
        } catch (e: Exception) {
            // Handle any initialization errors gracefully
            e.printStackTrace()
        }
    }
    
    override fun isApplicable(project: Project): Boolean = true
    
    // Action to create new Claude session
    private inner class NewSessionAction(
        private val project: Project,
        private val toolWindow: ToolWindow
    ) : AnAction("New Claude Session", "Create a new Claude session", AllIcons.General.Add) {
        
        init {
            // Add keyboard shortcut
            val shortcut = KeyboardShortcut(
                KeyStroke.getKeyStroke("ctrl shift N"),
                null
            )
            registerCustomShortcutSet(CustomShortcutSet(shortcut), toolWindow.component)
        }
        
        override fun actionPerformed(e: AnActionEvent) {
            createNewClaudeSession(project, toolWindow)
        }
    }
    
    // Action to open settings
    private class OpenSettingsAction : AnAction(
        "Claude Settings",
        "Open Claude Code settings",
        AllIcons.General.Settings
    ) {
        override fun actionPerformed(e: AnActionEvent) {
            ShowSettingsUtil.getInstance().showSettingsDialog(
                e.project,
                ClaudeCodeSettingsConfigurable::class.java
            )
        }
    }
}