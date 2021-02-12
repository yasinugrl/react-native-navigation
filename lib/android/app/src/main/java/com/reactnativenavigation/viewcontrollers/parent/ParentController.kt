package com.reactnativenavigation.viewcontrollers.parent

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.viewpager.widget.ViewPager
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.ObjectUtils
import com.reactnativenavigation.viewcontrollers.bottomtabs.BottomTabsController
import com.reactnativenavigation.viewcontrollers.child.ChildController
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.component.Component

abstract class ParentController<out T : ViewGroup>(
        activity: Activity,
        childRegistry: ChildControllersRegistry,
        id: String,
        presenter: Presenter,
        initialOptions: Options
) : ChildController<T>(activity, childRegistry, id, presenter, initialOptions) {

    override val currentComponentName: String?
        get() = currentChild?.currentComponentName

    abstract val currentChild: ViewController<ViewGroup>?
    abstract val childControllers: Collection<ViewController<ViewGroup>>
    //TODO why do wee need this type of relation ?
    protected val bottomTabsController: BottomTabsController?
        get() = if (this is BottomTabsController) {
            this
        } else ObjectUtils.perform(parentController, null, { obj: ParentController<ViewGroup> -> obj.bottomTabsController })


    override fun setWaitForRender(waitForRender: Bool) {
        super.setWaitForRender(waitForRender)
        applyOnController(currentChild, Func1 { currentChild: ViewController<ViewGroup>? -> currentChild?.setWaitForRender(waitForRender) })
    }

    override fun setDefaultOptions(defaultOptions: Options?) {
        super.setDefaultOptions(defaultOptions)
        childControllers.forEach {
            it.setDefaultOptions(defaultOptions)
        }
    }

    override fun onViewDidAppear() {
        currentChild?.onViewDidAppear()
    }

    @CheckResult
    override fun resolveCurrentOptions(): Options {
        return if (CollectionUtils.isNullOrEmpty(childControllers)) initialOptions else currentChild
                ?.resolveCurrentOptions()
                ?.copy()
                ?.withDefaultOptions(initialOptions)?:initialOptions
    }

    fun resolveChildOptions(child: ViewController<*>): Options {
        return if (child === this) resolveCurrentOptions() else child
                .resolveCurrentOptions()
                .copy()
                .withDefaultOptions(initialOptions)
    }

    @CheckResult
    override fun resolveCurrentOptions(defaultOptions: Options?): Options {
        return resolveCurrentOptions()!!.withDefaultOptions(defaultOptions)
    }

    fun isCurrentChild(child: ViewController<*>): Boolean {
        return currentChild === child
    }


    override fun findController(id: String?): ViewController<ViewGroup>? {
        val fromSuper = super.findController(id)
        if (fromSuper != null) return fromSuper
        for (child in childControllers) {
            val fromChild = child!!.findController(id)
            if (fromChild != null) return fromChild
        }
        return null
    }

    override fun findController(child: View): ViewController<ViewGroup>? {
        val fromSuper = super.findController(child)
        if (fromSuper != null) return fromSuper
        for (childController in childControllers) {
            val fromChild = childController!!.findController(child)
            if (fromChild != null) return fromChild
        }
        return null
    }

    override fun containsComponent(component: Component): Boolean {
        if (super.containsComponent(component)) {
            return true
        }
        for (child in childControllers) {
            if (child!!.containsComponent(component)) return true
        }
        return false
    }

    @CallSuper
    open fun applyChildOptions(options: Options?, child: ViewController<*>?) {
        this.options = initialOptions.mergeWith(options)
    }

    @CallSuper
    open fun mergeChildOptions(options: Options?, child: ViewController<*>?) {
    }

    override fun destroy() {
        super.destroy()
        CollectionUtils.forEach(childControllers) { obj -> obj?.destroy() }
    }

    @CallSuper
    fun clearOptions() {
        performOnParentController(Func1 { obj: ParentController<*> -> obj.clearOptions() })
        options = initialOptions.copy().clearOneTimeOptions()
    }

    open fun setupTopTabsWithViewPager(viewPager: ViewPager?) {}
    open fun clearTopTabs() {}
    override val isRendered: Boolean
        get() = currentChild != null && currentChild!!.isRendered

    open fun onChildDestroyed(child: ViewController<*>?) {}
    override fun applyTopInset() {
        CollectionUtils.forEach(childControllers) { obj -> obj?.applyTopInset() }
    }

    open fun getTopInset(child: ViewController<*>?): Int {
        return ObjectUtils.perform(parentController, 0, { p: ParentController<ViewGroup> -> p.getTopInset(child) })
    }

    override fun applyBottomInset() {
        CollectionUtils.forEach(childControllers) { obj-> obj?.applyBottomInset() }
    }

    open fun getBottomInset(child: ViewController<*>?): Int {
        return ObjectUtils.perform(parentController, 0, { p: ParentController<ViewGroup> -> p.getBottomInset(child) })
    }

}