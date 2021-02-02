package com.reactnativenavigation.viewcontrollers.viewcontroller

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.ViewTreeObserver
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.annotation.VisibleForTesting
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.options.params.NullBool
import com.reactnativenavigation.react.CommandListener
import com.reactnativenavigation.utils.*
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.Functions.FuncR1
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.stack.StackController
import com.reactnativenavigation.viewcontrollers.viewcontroller.overlay.ViewControllerOverlay
import com.reactnativenavigation.views.BehaviourAdapter
import com.reactnativenavigation.views.component.Component
import com.reactnativenavigation.views.component.Renderable
import java.util.*

abstract class ViewController<T : ViewGroup?>(
        private val activity: Activity,
        val id: String,
        private val yellowBoxDelegate: YellowBoxDelegate,
        var initialOptions: Options,
        private var overlay: ViewControllerOverlay
) : ViewTreeObserver.OnGlobalLayoutListener, ViewGroup.OnHierarchyChangeListener, BehaviourAdapter {
    private val onAppearedListeners: MutableList<Runnable> = ArrayList()
    private var appearEventPosted = false
    private var isFirstLayout = true
    private var waitForRender: Bool = NullBool()

    interface ViewVisibilityListener {
        /**
         * @return true if the event is consumed, false otherwise
         */
        fun onViewAppeared(view: View?): Boolean

        /**
         * @return true if the event is consumed, false otherwise
         */
        fun onViewDisappear(view: View?): Boolean
    }

    var options: Options

    //    val newOptions: com.reactnativenavigation.newoptions.Options = com.reactnativenavigation.newoptions.Options()
    var view: T? = null
        get() {
            if (field == null) {
                if (isDestroyed) {
                    throw RuntimeException("Tried to create view after it has already been destroyed")
                }
                field = createView()
                field?.setOnHierarchyChangeListener(this)
                field?.viewTreeObserver?.addOnGlobalLayoutListener(this)
            }
            return field
        }
    private var parentController: ParentController<out ViewGroup>? = null
    private var isShown = false
    var isDestroyed = false
        private set
    private var viewVisibilityListener: ViewVisibilityListener = ViewVisibilityListenerAdapter()
    abstract val currentComponentName: String?
    fun setOverlay(overlay: ViewControllerOverlay) {
        this.overlay = overlay
    }

    open fun setWaitForRender(waitForRender: Bool) {
        this.waitForRender = waitForRender
    }

    open val scrollEventListener: ScrollEventListener?
        get() = null

    fun addOnAppearedListener(onAppearedListener: Runnable) {
        if (isShown) {
            onAppearedListener.run()
        } else {
            onAppearedListeners.add(onAppearedListener)
        }
    }

    fun removeOnAppearedListener(onAppearedListener: Runnable?) {
        onAppearedListeners.remove(onAppearedListener)
    }

    abstract fun createView(): T
    fun setViewVisibilityListener(viewVisibilityListener: ViewVisibilityListener) {
        this.viewVisibilityListener = viewVisibilityListener
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    open fun ensureViewIsCreated() {
        this.view
    }

    open fun handleBack(listener: CommandListener?): Boolean {
        return false
    }

    fun addOverlay(v: View?, layoutParams: ViewGroup.LayoutParams?) {
        ObjectUtils.perform(view, { view: T -> overlay.add(view as ViewGroup, v!!, layoutParams!!) })
    }

    fun removeOverlay(view: View?) {
        overlay.remove(view!!)
    }


    @CheckResult
    open fun resolveCurrentOptions(): Options? {
        return options
    }

    @CheckResult
    open fun resolveCurrentOptions(defaultOptions: Options?): Options? {
        return options.copy().withDefaultOptions(defaultOptions)
    }

    @CallSuper
    open fun mergeOptions(options: Options?) {
        initialOptions = initialOptions.mergeWith(options)
        this.options = this.options.mergeWith(options)
        if (getParentController() != null) {
            this.options.clearOneTimeOptions()
            initialOptions.clearOneTimeOptions()
        }
    }

    @CallSuper
    open fun applyOptions(options: Options?) {
    }

    open fun setDefaultOptions(defaultOptions: Options?) {}
    open fun getActivity(): Activity? {
        return activity
    }

    fun performOnView(task: Func1<View?>) {
        if (view != null) task.run(view)
    }

    fun performOnParentController(task: Func1<ParentController<*>>) {
        if (parentController != null) task.run(parentController)
    }

    fun getParentController(): ParentController<*>? {
        return parentController
    }

    fun requireParentController(): ParentController<*>? {
        return parentController
    }

    fun setParentController(parentController: ParentController<*>?) {
        this.parentController = parentController
    }

    fun performOnParentStack(task: Func1<StackController?>) {
        if (parentController is StackController) {
            task.run(parentController as StackController?)
        } else if (this is StackController) {
            task.run(this as StackController)
        } else performOnParentController(Func1 { parent: ParentController<*> -> parent.performOnParentStack(task) })
    }


    fun detachView() {
        if (view == null || view?.parent == null) return
        (view?.parent as ViewManager).removeView(view)
    }

    fun attachView(parent: ViewGroup, index: Int) {
        if (view == null) return
        if (view?.parent == null) parent.addView(view, index)
    }

    fun isSameId(id: String?): Boolean {
        return StringUtils.isEqual(this.id, id)
    }

    open fun findController(id: String?): ViewController<*>? {
        return if (isSameId(id)) this else null
    }

    open fun findController(child: View): ViewController<*>? {
        return if (view === child) this else null
    }

    open fun containsComponent(component: Component): Boolean {
        return this.view == component
    }

    @CallSuper
    open fun onViewWillAppear() {
        isShown = true
        applyOptions(options)
        performOnParentController(Func1 { parentController: ParentController<*> ->
            parentController.clearOptions()
            if (this.view is Component) parentController.applyChildOptions(options, this)
        })
        if (!onAppearedListeners.isEmpty() && !appearEventPosted) {
            appearEventPosted = true
            UiThread.post {
                CollectionUtils.forEach(onAppearedListeners) { obj: Runnable -> obj.run() }
                onAppearedListeners.clear()
            }
        }
    }

    open fun onViewDidAppear() {}
    open fun onViewWillDisappear() {}

    @CallSuper
    open fun onViewDisappear() {
        isShown = false
    }

    @CallSuper
    open fun destroy() {
        if (isShown) {
            isShown = false
            onViewDisappear()
        }
        yellowBoxDelegate.destroy()
        if (view is Destroyable) {
            (view as Destroyable).destroy()
        }
        if (view != null) {
            view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
            view?.setOnHierarchyChangeListener(null)
            if (view?.parent is ViewGroup) {
                (view?.parent as ViewManager).removeView(view)
            }
            view = null
            isDestroyed = true
        }
    }

    override fun onGlobalLayout() {
        if (isFirstLayout) {
            onAttachToParent()
            isFirstLayout = false
        }
        if (!isShown && isViewShown) {
            if (!viewVisibilityListener.onViewAppeared(view)) {
                isShown = true
                onViewWillAppear()
            }
        } else if (isShown && !isViewShown) {
            if (!viewVisibilityListener.onViewDisappear(view)) {
                isShown = false
                onViewDisappear()
            }
        }
    }

    open fun onAttachToParent() {}
    override fun onChildViewAdded(parent: View, child: View) {
        yellowBoxDelegate.onChildViewAdded(parent, child)
    }

    override fun onChildViewRemoved(view: View, view1: View) {}
    fun runOnPreDraw(task: Func1<T>?) {
        if (!isDestroyed) UiUtils.runOnPreDrawOnce(this.view, task)
    }

    abstract fun sendOnNavigationButtonPressed(buttonId: String?)
    open val isViewShown: Boolean
        get() = !isDestroyed && view != null &&
                view?.isShown == true &&
                isRendered

    open val isRendered: Boolean
        get() = view != null && (waitForRender.isFalseOrUndefined ||
                view !is Renderable ||
                (view as Renderable).isRendered)

    open fun start() {}
    fun applyOnController(controller: ViewController<*>?, task: Func1<ViewController<*>?>) {
        if (controller != null) task.run(controller)
    }

    @CallSuper
    override fun onMeasureChild(parent: CoordinatorLayout, child: ViewGroup, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        ObjectUtils.perform<ViewController<*>?>(findController(child), { obj: ViewController<*>? -> obj!!.applyTopInset() })
        return false
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ViewGroup, dependency: View): Boolean {
        return false
    }

    open fun applyTopInset() {}
    open val topInset: Int
        get() = 0

    open fun applyBottomInset() {}
    val bottomInset: Int
        get() = ObjectUtils.perform<ParentController<out ViewGroup>?, Int>(parentController, 0, FuncR1 { p: ParentController<out ViewGroup>? -> p?.getBottomInset(this) })

    init {
        options = initialOptions.copy()
    }
}