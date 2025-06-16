package com.claudecode.plugin

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class ClaudeCodeSettingsComponent {
    private val continueConversationCheckBox = JBCheckBox("Continue previous conversation on startup (-c)")
    private val useCustomModelCheckBox = JBCheckBox("Use custom model")
    private val customModelField = JBTextField()
    private val additionalFlagsField = JBTextField()
    
    init {
        customModelField.isEnabled = false
        useCustomModelCheckBox.addActionListener {
            customModelField.isEnabled = useCustomModelCheckBox.isSelected
        }
    }
    
    val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponent(JBLabel("Claude Command Options"))
        .addVerticalGap(10)
        .addComponent(continueConversationCheckBox)
        .addVerticalGap(5)
        .addComponent(useCustomModelCheckBox)
        .addLabeledComponent("Model (e.g., sonnet, opus):", customModelField)
        .addVerticalGap(10)
        .addLabeledComponent("Additional flags:", additionalFlagsField)
        .addComponentToRightColumn(JBLabel("e.g., --debug --verbose"))
        .addVerticalGap(20)
        .addComponent(JBLabel("Note: The command will be built as: claude [options]"))
        .addComponentFillVertically(JPanel(), 0)
        .panel
    
    var continueConversation: Boolean
        get() = continueConversationCheckBox.isSelected
        set(value) { continueConversationCheckBox.isSelected = value }
    
    var useCustomModel: Boolean
        get() = useCustomModelCheckBox.isSelected
        set(value) { 
            useCustomModelCheckBox.isSelected = value
            customModelField.isEnabled = value
        }
    
    var customModel: String
        get() = customModelField.text
        set(value) { customModelField.text = value }
    
    var additionalFlags: String
        get() = additionalFlagsField.text
        set(value) { additionalFlagsField.text = value }
}