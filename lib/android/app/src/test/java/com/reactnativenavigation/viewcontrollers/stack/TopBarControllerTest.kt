package com.reactnativenavigation.viewcontrollers.stack

import android.app.Activity
import android.content.Context
import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.reactnativenavigation.BaseTest
import com.reactnativenavigation.options.ButtonOptions
import com.reactnativenavigation.options.params.Text
import com.reactnativenavigation.react.Constants
import com.reactnativenavigation.react.ReactView
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.TitleBarHelper
import com.reactnativenavigation.utils.resetViewProperties
import com.reactnativenavigation.viewcontrollers.stack.topbar.TopBarController
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.ButtonController
import com.reactnativenavigation.views.stack.StackLayout
import com.reactnativenavigation.views.stack.topbar.TopBar
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.*

class TopBarControllerTest : BaseTest() {
    private lateinit var uut: TopBarController
    private lateinit var activity: Activity
    private lateinit var leftButton: ButtonOptions
    private lateinit var textButton1: ButtonOptions
    private lateinit var textButton2: ButtonOptions
    private lateinit var componentButton: ButtonOptions
    private val topBar: View
        get() = uut.view

    override fun beforeEach() {
        activity = newActivity()
        uut = createTopBarController()
        val stack = mock<StackLayout>()
        uut.createView(activity, stack)
        createButtons()
    }

    @Test
    fun setButton_setsTextButton() {
        uut.applyRightButtons(rightButtons(textButton1)!!)
        uut.setLeftButtons(leftButton(leftButton))
        assertThat(uut.getRightButton(0).title.toString()).isEqualTo(textButton1.text.get())
    }

    @Test
    fun setButton_setsCustomButton() {
        uut.setLeftButtons(leftButton(leftButton))
        uut.applyRightButtons(rightButtons(componentButton)!!)
        val btnView = uut.getRightButton(0).actionView as ReactView
        assertThat(btnView.componentName).isEqualTo(componentButton.component.name.get())
    }

    @Test
    fun applyRightButtons_emptyButtonsListClearsRightButtons() {
        uut.setLeftButtons(ArrayList())
        uut.applyRightButtons(rightButtons(componentButton, textButton1)!!)
        uut.setLeftButtons(ArrayList())
        uut.applyRightButtons(ArrayList())
        assertThat(uut.rightButtonsCount).isEqualTo(0)
    }

    @Test
    fun applyRightButtons_previousButtonsAreCleared() {
        uut.applyRightButtons(rightButtons(textButton1, componentButton)!!)
        assertThat(uut.rightButtonsCount).isEqualTo(2)
        uut.applyRightButtons(rightButtons(textButton2)!!)
        assertThat(uut.rightButtonsCount).isEqualTo(1)
    }

    @Test
    fun applyRightButtons_buttonsAreAddedInReverseOrderToMatchOrderOnIOs() {
        uut.setLeftButtons(ArrayList())
        uut.applyRightButtons(rightButtons(textButton1, componentButton)!!)
        assertThat(uut.getRightButton(1).title.toString()).isEqualTo(textButton1.text.get())
    }

    @Test
    fun applyRightButtons_componentButtonIsReapplied() {
        val initialButtons = rightButtons(componentButton)
        uut.applyRightButtons(initialButtons!!)
        assertThat(uut.getRightButton(0).itemId).isEqualTo(componentButton.intId)
        uut.applyRightButtons(rightButtons(textButton1)!!)
        assertThat(uut.getRightButton(0).itemId).isEqualTo(textButton1.intId)
        uut.applyRightButtons(initialButtons)
        assertThat(uut.getRightButton(0).itemId).isEqualTo(componentButton.intId)
    }

    @Test
    fun mergeRightButtons_componentButtonIsNotAddedIfAlreadyAddedToMenu() {
        val initialButtons = rightButtons(componentButton)
        uut.applyRightButtons(initialButtons!!)
        uut.mergeRightButtons(initialButtons, emptyList())
    }

    @Test
    fun setLeftButtons_emptyButtonsListClearsLeftButton() {
        uut.setLeftButtons(leftButton(leftButton))
        uut.applyRightButtons(rightButtons(componentButton)!!)
        assertThat(uut.leftButton).isNotNull()
        uut.setLeftButtons(ArrayList())
        uut.applyRightButtons(rightButtons(textButton1)!!)
        assertThat(uut.leftButton).isNull()
    }

    @Test
    fun show() {
        uut.hide()
        assertGone(topBar)

        uut.show()
        verify(topBar).resetViewProperties()
        assertVisible(topBar)
    }

    private fun createButtons() {
        leftButton = ButtonOptions()
        leftButton.id = Constants.BACK_BUTTON_ID
        textButton1 = createTextButton("1")
        textButton2 = createTextButton("2")
        componentButton = ButtonOptions()
        componentButton.id = "customBtn"
        componentButton.component.name = Text("com.rnn.customBtn")
        componentButton.component.componentId = Text("component4")
    }

    private fun createTextButton(id: String): ButtonOptions {
        val button = ButtonOptions()
        button.id = id
        button.text = Text("txt$id")
        return button
    }

    private fun leftButton(leftButton: ButtonOptions?): List<ButtonController?> {
        return listOf(TitleBarHelper.createButtonController(activity, leftButton))
    }

    private fun rightButtons(vararg buttons: ButtonOptions): List<ButtonController>? {
        return CollectionUtils.map(listOf(*buttons)) { button: ButtonOptions? -> TitleBarHelper.createButtonController(activity, button) }
    }

    private fun createTopBarController() = spy(object : TopBarController() {
        override fun createTopBar(context: Context, stackLayout: StackLayout): TopBar {
            return spy(super.createTopBar(context, stackLayout))
        }
    })
}