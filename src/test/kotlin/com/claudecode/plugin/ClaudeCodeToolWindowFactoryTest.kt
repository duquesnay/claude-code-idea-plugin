package com.claudecode.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.ui.content.ContentManager
import org.junit.Test
import org.mockito.Mockito.*

class ClaudeCodeToolWindowFactoryTest : BasePlatformTestCase() {

    @Test
    fun testCreateToolWindowContent() {
        val factory = ClaudeCodeToolWindowFactory()
        val toolWindow = mock(ToolWindow::class.java)
        val contentManager = mock(ContentManager::class.java)
        val disposable = mock(com.intellij.openapi.Disposable::class.java)
        
        `when`(toolWindow.contentManager).thenReturn(contentManager)
        `when`(toolWindow.disposable).thenReturn(disposable)
        
        factory.createToolWindowContent(project, toolWindow)
        
        verify(contentManager).addContent(any())
    }

    @Test
    fun testToolWindowIsStripeTitle() {
        val factory = ClaudeCodeToolWindowFactory()
        assertFalse(factory.isStripeTitle)
    }

    @Test
    fun testToolWindowInitialization() {
        val factory = ClaudeCodeToolWindowFactory()
        val toolWindow = mock(ToolWindow::class.java)
        val contentManager = mock(ContentManager::class.java)
        val disposable = mock(com.intellij.openapi.Disposable::class.java)
        
        `when`(toolWindow.contentManager).thenReturn(contentManager)
        `when`(toolWindow.disposable).thenReturn(disposable)
        
        assertDoesNotThrow {
            factory.createToolWindowContent(project, toolWindow)
        }
    }

    @Test
    fun testContentFactoryCreatesContent() {
        val factory = ClaudeCodeToolWindowFactory()
        val toolWindow = mock(ToolWindow::class.java)
        val contentManager = mock(ContentManager::class.java)
        val disposable = mock(com.intellij.openapi.Disposable::class.java)
        
        `when`(toolWindow.contentManager).thenReturn(contentManager)
        `when`(toolWindow.disposable).thenReturn(disposable)
        
        factory.createToolWindowContent(project, toolWindow)
        
        verify(contentManager, times(1)).addContent(argThat { content ->
            content.displayName == "Claude Code" && 
            content.isCloseable == false
        })
    }

    private fun assertDoesNotThrow(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            fail("Expected no exception but got: ${e.message}")
        }
    }
}