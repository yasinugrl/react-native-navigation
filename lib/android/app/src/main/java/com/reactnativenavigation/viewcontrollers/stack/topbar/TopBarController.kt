package com.reactnativenavigation.viewcontrollers.stack.topbar

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.viewpager.widget.ViewPager
import com.reactnativenavigation.options.AnimationOptions
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.ViewUtils
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.ButtonController
import com.reactnativenavigation.viewcontrollers.stack.topbar.title.TitleBarReactViewController
import com.reactnativenavigation.views.stack.StackLayout
import com.reactnativenavigation.views.stack.topbar.TopBar
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBar

open class TopBarController {
    lateinit var view: TopBar
    private lateinit var titleBar: TitleBar
    @VisibleForTesting var animator: TopBarAnimator = TopBarAnimator()

    val height: Int
        get() = view.height
    val rightButtonsCount: Int
        get() = view.rightButtonsCount
    val leftButton: Drawable?
        get() = titleBar.navigationIcon

    fun getRightButton(index: Int): MenuItem = titleBar.getRightButton(index)

    fun createView(context: Context, parent: StackLayout): TopBar {
        if (!::view.isInitialized) {
            view = createTopBar(context, parent)
            titleBar = view.titleBar
            animator.bindView(view)
        }
        return view
    }

    protected open fun createTopBar(context: Context, stackLayout: StackLayout): TopBar {
        return TopBar(context)
    }

    fun initTopTabs(viewPager: ViewPager?) = view.initTopTabs(viewPager)

    fun clearTopTabs() = view.clearTopTabs()

    fun getPushAnimation(
            appearingOptions: Options,
            additionalDy: Float = 0f
    ): Animator {
        return animator.getPushAnimation(
                appearingOptions.animations.push.topBar,
                appearingOptions.topBar.visible,
                additionalDy
        )
    }

    fun getPopAnimation(appearingOptions: Options, disappearingOptions: Options): Animator {
        return animator.getPopAnimation(disappearingOptions.animations.pop.topBar, appearingOptions.topBar.visible)
    }

    fun show() {
        if (ViewUtils.isVisible(view) || animator.isAnimatingShow()) return
        view.visibility = View.VISIBLE
    }

    fun showAnimate(options: AnimationOptions, additionalDy: Float) {
        if (ViewUtils.isVisible(view) || animator.isAnimatingShow()) return
        animator.show(options, additionalDy)
    }

    fun hide() {
        if (!animator.isAnimatingHide()) view.visibility = View.GONE
    }

    fun hideAnimate(options: AnimationOptions, additionalDy: Float) {
        if (!ViewUtils.isVisible(view) || animator.isAnimatingHide()) return
        animator.hide(options, additionalDy)
    }

    fun setTitleComponent(component: TitleBarReactViewController) {
        view.setTitleComponent(component.view)
    }

    fun applyRightButtons(toAdd: List<ButtonController>) {
        view.clearRightButtons()
        CollectionUtils.forEachIndexed(toAdd) { b: ButtonController, i: Int -> b.addToMenu(titleBar, (toAdd.size - i) * 10) }
    }

    fun mergeRightButtons(toAdd: List<ButtonController>, toRemove: List<ButtonController>?) {
        CollectionUtils.forEach(toRemove) { btn: ButtonController? -> view.removeRightButton(btn) }
        CollectionUtils.forEachIndexed(toAdd) { b: ButtonController, i: Int -> b.addToMenu(titleBar, (toAdd.size - i) * 10) }
    }

    fun setLeftButtons(leftButtons: List<ButtonController?>?) {
        titleBar.setLeftButtons(leftButtons)
    }

}