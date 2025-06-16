package com.claudecode.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.ui.content.ContentManager
import org.junit.Test
import org.mockito.Mockito.*

class ClaudeCodeToolWindowFactoryTest : BasePlatformTestCase() {

    @Test
    fun testToolWindowIsApplicable() {
        val factory = ClaudeCodeToolWindowFactory()
        assertTrue("Tool window should always be applicable", factory.isApplicable(project))
    }

    @Test
    fun testFactoryCanBeCreated() {
        val factory = ClaudeCodeToolWindowFactory()
        assertNotNull("Factory should be created successfully", factory)
    }

    @Test
    fun testCreateToolWindowContentHandlesErrors() {
        // This test verifies that the factory handles errors gracefully
        val factory = ClaudeCodeToolWindowFactory()
        val toolWindow = mock(ToolWindow::class.java)
        val contentManager = mock(ContentManager::class.java)
        val disposable = mock(com.intellij.openapi.Disposable::class.java)
        
        `when`(toolWindow.contentManager).thenReturn(contentManager)
        `when`(toolWindow.disposable).thenReturn(disposable)
        
        // The method should not throw even if terminal creation fails
        assertDoesNotThrow {
            factory.createToolWindowContent(project, toolWindow)
        }
    }

    @Test
    fun testToolWindowFactoryInterface() {
        val factory = ClaudeCodeToolWindowFactory()
        
        // Verify it implements the required interface
        assertTrue("Should implement ToolWindowFactory", factory is ToolWindowFactory)
        
        // Verify required methods exist
        assertTrue("Should have createToolWindowContent method", 
            factory.javaClass.methods.any { it.name == "createToolWindowContent" })
        assertTrue("Should have isApplicable method", 
            factory.javaClass.methods.any { it.name == "isApplicable" })
    }

    private fun assertDoesNotThrow(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            fail("Expected no exception but got: ${e.message}")
        }
    }
}