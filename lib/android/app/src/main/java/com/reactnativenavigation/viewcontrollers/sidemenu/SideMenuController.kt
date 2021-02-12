package com.reactnativenavigation.viewcontrollers.sidemenu

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.annotation.RestrictTo
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.SideMenuRootOptions
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.react.CommandListener
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.ObjectUtils
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.sidemenu.SideMenu
import com.reactnativenavigation.views.sidemenu.SideMenuRoot
import java.util.*

open class SideMenuController(
        activity: Activity,
        childRegistry: ChildControllersRegistry,
        id: String,
        initialOptions: Options,
        private val sideMenuPresenter: SideMenuPresenter,
        presenter: Presenter
) : ParentController<SideMenuRoot>(activity, childRegistry, id, presenter, initialOptions), DrawerListener {
    private var center: ViewController<ViewGroup>? = null
    private var left: ViewController<ViewGroup>? = null
    private var right: ViewController<ViewGroup>? = null
    private var prevLeftSlideOffset = 0f
    private var prevRightSlideOffset = 0f
    override val currentChild: ViewController<*>?
        get() {
            if (!isDestroyed) {
                if (view.isDrawerOpen(Gravity.LEFT)) {
                    return left
                } else if (view.isDrawerOpen(Gravity.RIGHT)) {
                    return right
                }
            }
            return center
        }

    override fun createView(): SideMenuRoot {
        val sideMenu = SideMenu(activity)
        sideMenuPresenter.bindView(sideMenu)
        sideMenu.addDrawerListener(this)
        val root = SideMenuRoot(activity)
        root.addSideMenu(sideMenu, this)
        return root
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        center!!.sendOnNavigationButtonPressed(buttonId)
    }

    override val childControllers: Collection<ViewController<*>>
        get() {
            val children = ArrayList<ViewController<*>>()
            if (center != null) children.add(center!!)
            if (left != null) children.add(left!!)
            if (right != null) children.add(right!!)
            return children
        }

    override fun applyOptions(options: Options?) {
        super.applyOptions(options)
        sideMenuPresenter.applyOptions(options)
    }

    override fun applyChildOptions(options: Options?, child: ViewController<*>?) {
        super.applyChildOptions(options, child)
        sideMenuPresenter.applyChildOptions(resolveCurrentOptions())
        performOnParentController(Func1 { parent: ParentController<*> -> parent.applyChildOptions(this.options, child) })
    }

    override fun mergeChildOptions(options: Options?, child: ViewController<*>?) {
        super.mergeChildOptions(options, child)
        sideMenuPresenter.mergeOptions(options!!.sideMenuRootOptions)
        mergeLockMode(initialOptions, options.sideMenuRootOptions)
        performOnParentController(Func1 { parent: ParentController<*> -> parent.mergeChildOptions(options, child) })
    }

    override fun onViewWillAppear() {
        super.onViewWillAppear()
        left?.performOnView(Func1 { view: View? -> view?.requestLayout() })
        right?.performOnView(Func1 { view: View? -> view?.requestLayout() })
    }

    override fun mergeOptions(options: Options?) {
        super.mergeOptions(options)
        sideMenuPresenter.mergeOptions(options?.sideMenuRootOptions)
    }

    override fun resolveCurrentOptions(): Options {
        var options = super.resolveCurrentOptions()
        if (isDrawerOpen(Gravity.LEFT) || isDrawerOpen(Gravity.RIGHT)) {
            options = options.mergeWith(center?.resolveCurrentOptions())
        }
        return options
    }

    private fun isDrawerOpen(gravity: Int): Boolean {
        return !isDestroyed && view?.isDrawerOpen(gravity)
    }

    override fun onDrawerOpened(drawerView: View) {
        val view = getMatchingView(drawerView)
        view?.mergeOptions(getOptionsWithVisibility(isLeftMenu(drawerView), true))
    }

    override fun onDrawerClosed(drawerView: View) {
        val view = getMatchingView(drawerView)
        view?.mergeOptions(getOptionsWithVisibility(isLeftMenu(drawerView), false))
    }

    override fun onDrawerSlide(drawerView: View, @FloatRange(from = 0.0, to = 1.0) slideOffset: Float) {
        val gravity = getSideMenuGravity(drawerView)
        if (gravity == Gravity.LEFT) {
            dispatchSideMenuVisibilityEvents(left, prevLeftSlideOffset, slideOffset)
            prevLeftSlideOffset = slideOffset
        } else if (gravity == Gravity.RIGHT) {
            dispatchSideMenuVisibilityEvents(right, prevRightSlideOffset, slideOffset)
            prevRightSlideOffset = slideOffset
        }
    }

    override fun onDrawerStateChanged(newState: Int) {}
    override fun handleBack(listener: CommandListener?): Boolean {
        return sideMenuPresenter.handleBack() || center?.handleBack(listener) == true || super.handleBack(listener)
    }

    override fun findController(child: View): ViewController<*>? {
        return if (view?.isSideMenu(child)) this else super.findController(child)
    }

    fun setCenterController(centerController: ViewController<*>?) {
        center = centerController
        view?.setCenter(center)
    }

    fun setLeftController(controller: ViewController<*>?) {
        left = controller
        view?.setLeft(left, options)
        sideMenuPresenter.bindLeft(left)
    }

    fun setRightController(controller: ViewController<*>?) {
        right = controller
        view?.setRight(right, options)
        sideMenuPresenter.bindRight(right)
    }

    private fun getMatchingView(drawerView: View): ViewController<*>? {
        return if (isLeftMenu(drawerView)) left else right
    }

    private fun isLeftMenu(drawerView: View): Boolean {
        return left != null && drawerView == left?.view
    }

    private fun getSideMenuGravity(drawerView: View): Int {
        return (drawerView.layoutParams as DrawerLayout.LayoutParams).gravity
    }

    private fun getOptionsWithVisibility(isLeft: Boolean, visible: Boolean): Options {
        val options = Options()
        if (isLeft) {
            options.sideMenuRootOptions.left.visible = Bool(visible)
        } else {
            options.sideMenuRootOptions.right.visible = Bool(visible)
        }
        return options
    }

    private fun dispatchSideMenuVisibilityEvents(drawer: ViewController<*>?, prevOffset: Float, offset: Float) {
        if (prevOffset < 1 && offset == 1f) {
            drawer?.onViewDidAppear()
        } else if (prevOffset == 0f && offset > 0) {
            drawer?.onViewWillAppear()
        } else if (prevOffset > 0 && offset == 0f) {
            drawer?.onViewDisappear()
        }
    }

    private fun mergeLockMode(out: Options, sideMenu: SideMenuRootOptions) {
        ObjectUtils.perform(sideMenu.left.enabled[null], { enabled: Boolean? -> out.sideMenuRootOptions.left.enabled = Bool(enabled) })
        ObjectUtils.perform(sideMenu.right.enabled[null], { enabled: Boolean? -> out.sideMenuRootOptions.right.enabled = Bool(enabled) })
    }

    @get:RestrictTo(RestrictTo.Scope.TESTS)
    val sideMenu: SideMenu
        get() = sideMenuPresenter.sideMenu

}