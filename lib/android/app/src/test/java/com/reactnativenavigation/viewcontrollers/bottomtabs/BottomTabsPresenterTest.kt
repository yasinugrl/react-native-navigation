package com.reactnativenavigation.viewcontrollers.bottomtabs

import com.nhaarman.mockitokotlin2.*
import com.reactnativenavigation.BaseTest
import com.reactnativenavigation.mocks.SimpleViewController
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.options.params.Colour
import com.reactnativenavigation.options.params.Number
import com.reactnativenavigation.options.params.Text
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.bottomtabs.BottomTabs
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

class BottomTabsPresenterTest : BaseTest() {
    private lateinit var tabs: List<ViewController<*>>
    private lateinit var uut: BottomTabsPresenter
    private lateinit var bottomTabs: BottomTabs
    private lateinit var animator: BottomTabsAnimator
    private lateinit var tabSelector: TabSelector

    override fun beforeEach() {
        val activity = newActivity()
        val childRegistry = ChildControllersRegistry()
        val child1 = spy(SimpleViewController(activity, childRegistry, "child1", Options()))
        val child2 = spy(SimpleViewController(activity, childRegistry, "child2", Options()))
        tabs = listOf(child1, child2)
        bottomTabs = mock()
        animator = spy(BottomTabsAnimator(bottomTabs))
        uut = BottomTabsPresenter(tabs, Options(), animator)
        tabSelector = mock()
        uut.bindView(bottomTabs, tabSelector)
    }

    @Test
    fun mergeChildOptions_onlyDeclaredOptionsAreApplied() { // default options are not applied on merge
        val defaultOptions = Options()
        defaultOptions.bottomTabsOptions.visible = Bool(false)
        uut.setDefaultOptions(defaultOptions)
        val options = Options()
        options.bottomTabsOptions.backgroundColor = Colour(10)
        uut.mergeChildOptions(options, tabs[0])
        verify(bottomTabs).setBackgroundColor(options.bottomTabsOptions.backgroundColor.get())
        verifyNoMoreInteractions(bottomTabs)
    }

    @Test
    fun mergeChildOptions_visibilityIsAppliedOnlyIfChildIsShown() {
        assertThat(tabs[0].isViewShown).isFalse()
        assertThat(bottomTabs.isHidden).isFalse()

        val options = Options()
        options.bottomTabsOptions.visible = Bool(false)
        uut.mergeChildOptions(options, tabs[0])
        verify(animator, never()).hide()

        whenever(tabs[0].isViewShown).thenAnswer { true }
        uut.mergeChildOptions(options, tabs[0])
        verify(animator).hide(any(), any(), anyOrNull())
    }

    @Test
    fun applyChildOptions_currentTabIndexIsConsumedAfterApply() {
        val defaultOptions = Options()
        defaultOptions.bottomTabsOptions.currentTabIndex = Number(1)
        uut.setDefaultOptions(defaultOptions)
        uut.applyChildOptions(Options.EMPTY, tabs[0])
        verify(tabSelector).selectTab(1)
        uut.applyChildOptions(Options.EMPTY, tabs[0])
        verifyNoMoreInteractions(tabSelector)
    }

    @Test
    fun applyChildOptions_currentTabIdIsConsumedAfterApply() {
        val defaultOptions = Options()
        defaultOptions.bottomTabsOptions.currentTabId = Text(tabs[1].id)
        uut.setDefaultOptions(defaultOptions)
        uut.applyChildOptions(Options.EMPTY, tabs[0])
        verify(tabSelector).selectTab(1)
        uut.applyChildOptions(Options.EMPTY, tabs[0])
        verifyNoMoreInteractions(tabSelector)
    }
}