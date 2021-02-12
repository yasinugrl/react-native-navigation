package com.reactnativenavigation.viewcontrollers.component

import android.app.Activity
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.utils.Functions.FuncR1
import com.reactnativenavigation.utils.ObjectUtils
import com.reactnativenavigation.utils.StatusBarUtils
import com.reactnativenavigation.viewcontrollers.child.ChildController
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ReactViewCreator
import com.reactnativenavigation.viewcontrollers.viewcontroller.ScrollEventListener
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.component.ComponentLayout
import java.util.*
import kotlin.math.max

open class ComponentViewController(activity: Activity?,
                                   childRegistry: ChildControllersRegistry,
                                   id: String,
                                   override val currentComponentName: String,
                                   private val viewCreator: ReactViewCreator,
                                   initialOptions: Options,
                                   presenter: Presenter,
                                   private val componentPresenter: ComponentPresenter) : ChildController<ComponentLayout>(activity!!, childRegistry, id, presenter, initialOptions) {

    private enum class VisibilityState {
        Appear, Disappear
    }

    private var lastVisibilityState = VisibilityState.Disappear
    override fun start() {
        if (!isDestroyed) view.start()
    }

    override fun setDefaultOptions(defaultOptions: Options?) {
        super.setDefaultOptions(defaultOptions)
        componentPresenter.setDefaultOptions(defaultOptions)
    }

    override val scrollEventListener: ScrollEventListener?
        get() = ObjectUtils.perform(view, null, { obj: ComponentLayout -> obj.scrollEventListener })

    override fun onViewDidAppear() {
        super.onViewDidAppear()
        if (lastVisibilityState == VisibilityState.Disappear) view!!.sendComponentStart()
        lastVisibilityState = VisibilityState.Appear
    }

    override fun onViewDisappear() {
        lastVisibilityState = VisibilityState.Disappear
        view.sendComponentStop()
        super.onViewDisappear()
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        view.sendOnNavigationButtonPressed(buttonId)
    }

    override fun applyOptions(options: Options?) {
        if (isRoot) applyTopInset()
        super.applyOptions(options)
        view.applyOptions(options)
        componentPresenter.applyOptions(view, resolveCurrentOptions(componentPresenter.defaultOptions))
    }

    override val isViewShown: Boolean
        get() = super.isViewShown && view!!.isReady

    override fun createView(): ComponentLayout {
        val view = viewCreator.create(activity, id, currentComponentName) as ComponentLayout
        return view.asView() as ComponentLayout
    }

    override fun mergeOptions(options: Options?) {
        if (options === Options.EMPTY) return
        if (isViewShown) componentPresenter.mergeOptions(view, options)
        super.mergeOptions(options)
    }

    override fun applyTopInset() {
        componentPresenter.applyTopInsets(view!!, topInset)
    }

    override val topInset: Int
        get() {
            val resolveCurrentOptions = resolveCurrentOptions(componentPresenter.defaultOptions)
            val statusBarInset = if (resolveCurrentOptions.statusBar.isHiddenOrDrawBehind) 0 else StatusBarUtils.getStatusBarHeight(activity)
            val perform = ObjectUtils.perform<ParentController<ViewGroup>, Int>(parentController, 0, { p: ParentController<ViewGroup>? -> p?.getTopInset(this) })
            return statusBarInset + perform
        }

    override fun applyBottomInset() {
        componentPresenter.applyBottomInset(view!!, bottomInset)
    }

    override fun applyWindowInsets(view: ViewController<*>?, insets: WindowInsetsCompat): WindowInsetsCompat {
        if (view != null) {
            ViewCompat.onApplyWindowInsets(view.view!!, insets.replaceSystemWindowInsets(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    max(insets.systemWindowInsetBottom - bottomInset, 0)
            ))
        }
        return insets
    }

    override fun destroy() {
        val blurOnUnmount = options.modal.blurOnUnmount.isTrue
        if (blurOnUnmount) {
            blurActivityFocus()
        }
        super.destroy()
    }

    private fun blurActivityFocus() {
        val activity: Activity? = activity
        val focusView = activity?.currentFocus
        focusView?.clearFocus()
    }

}