package com.reactnativenavigation.viewcontrollers.child

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.StatusBarUtils
import com.reactnativenavigation.viewcontrollers.navigator.Navigator
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.NoOpYellowBoxDelegate
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.viewcontrollers.viewcontroller.overlay.ViewControllerOverlay
import com.reactnativenavigation.views.component.Component

abstract class ChildController<T : ViewGroup>(
        activity: Activity,
        val childRegistry: ChildControllersRegistry,
        id: String,
        private val presenter: Presenter,
        initialOptions: Options
) : ViewController<T>(activity, id, NoOpYellowBoxDelegate(activity), initialOptions, ViewControllerOverlay(activity)) {

    override val view: T by lazy {
        val res = super.view
        res.fitsSystemWindows = true
        ViewCompat.setOnApplyWindowInsetsListener(res) { view: View, insets: WindowInsetsCompat -> onApplyWindowInsets(view, insets) }
        res
    }


    @CallSuper
    override fun setDefaultOptions(defaultOptions: Options?) {
        presenter.setDefaultOptions(defaultOptions)
    }

    override fun onViewWillAppear() {
        super.onViewWillAppear()
        childRegistry.onViewAppeared(this)
    }

    override fun onViewDisappear() {
        super.onViewDisappear()
        childRegistry.onViewDisappear(this)
    }

    fun onViewBroughtToFront() {
        presenter.onViewBroughtToFront(resolveCurrentOptions())
    }

    override fun applyOptions(options: Options?) {
        super.applyOptions(options)
        presenter.applyOptions(this, resolveCurrentOptions())
    }

    override fun mergeOptions(options: Options?) {
        if (options === Options.EMPTY) return
        if (isViewShown) presenter.mergeOptions(view, options)
        super.mergeOptions(options)
        performOnParentController(Func1 { parentController: ParentController<*> -> parentController.mergeChildOptions(options, this) })
    }

    override fun destroy() {
        if (!isDestroyed && view is Component) {
            performOnParentController(Func1 { parent: ParentController<*> -> parent.onChildDestroyed(this) })
        }
        super.destroy()
        childRegistry.onChildDestroyed(this)
    }

    val isRoot: Boolean
        get() = parentController == null &&
                this !is Navigator && view!!.parent != null

    private fun onApplyWindowInsets(view: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        StatusBarUtils.saveStatusBarHeight(insets.systemWindowInsetTop)
        return applyWindowInsets(findController(view), insets)
    }

    protected open fun applyWindowInsets(view: ViewController<*>?, insets: WindowInsetsCompat): WindowInsetsCompat {
        return insets.replaceSystemWindowInsets(
                insets.systemWindowInsetLeft,
                0,
                insets.systemWindowInsetRight,
                insets.systemWindowInsetBottom
        )
    }

}