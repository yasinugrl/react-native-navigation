package com.reactnativenavigation.viewcontrollers.navigator

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.facebook.react.ReactInstanceManager
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.react.CommandListener
import com.reactnativenavigation.react.CommandListenerAdapter
import com.reactnativenavigation.react.events.EventEmitter
import com.reactnativenavigation.utils.CompatUtils
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.modal.ModalStack
import com.reactnativenavigation.viewcontrollers.overlay.OverlayManager
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.stack.StackController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.RootPresenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.viewcontrollers.viewcontroller.overlay.RootOverlay

class Navigator(
        activity: Activity,
        childRegistry: ChildControllersRegistry,
        private val modalStack: ModalStack,
        private val overlayManager: OverlayManager,
        private val rootPresenter: RootPresenter
) : ParentController<ViewGroup>(activity, childRegistry, "navigator" + CompatUtils.generateViewId(), Presenter(activity, Options()), Options()) {

    private var root: ViewController<ViewGroup>? = null
    private var previousRoot: ViewController<ViewGroup>? = null

    val rootLayout: CoordinatorLayout = CoordinatorLayout(activity)

    @get:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    val modalsLayout: CoordinatorLayout = CoordinatorLayout(activity)

    @get:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    val overlaysLayout: CoordinatorLayout = CoordinatorLayout(activity)

    private var contentLayout: ViewGroup? = null
    private var defaultOptions: Options = Options()
    var removeSplashView = true


    override fun setDefaultOptions(defaultOptions: Options?) {
        super.setDefaultOptions(defaultOptions)
        if (defaultOptions != null) {
            this.defaultOptions = defaultOptions
        }
        modalStack.setDefaultOptions(defaultOptions)
    }

    fun getDefaultOptions(): Options? {
        return defaultOptions
    }

    fun setEventEmitter(eventEmitter: EventEmitter?) {
        modalStack.setEventEmitter(eventEmitter)
    }

    fun setContentLayout(contentLayout: ViewGroup) {
        this.contentLayout = contentLayout
        contentLayout.addView(rootLayout)
        modalsLayout.visibility = View.GONE
        contentLayout.addView(modalsLayout)
        overlaysLayout.visibility = View.GONE
        contentLayout.addView(overlaysLayout)
    }

    fun bindViews() {
        modalStack.setModalsLayout(modalsLayout)
        modalStack.setRootLayout(rootLayout)
        rootPresenter.setRootContainer(rootLayout)
    }

    override fun createView(): ViewGroup {
        return rootLayout
    }

    override val childControllers: Collection<ViewController<ViewGroup>>
        get() = root?.let { listOf(it) } ?: emptyList()

    override fun handleBack(listener: CommandListener?): Boolean {
        if (modalStack.isEmpty && root == null) return false
        return if (modalStack.isEmpty) root!!.handleBack(listener) else modalStack.handleBack(listener, root)
    }

    override val currentChild: ViewController<*>?
        get() = root

    override fun destroy() {
        destroyViews()
        super.destroy()
    }

    fun destroyViews() {
        modalStack.destroy()
        overlayManager.destroy(overlaysLayout)
        destroyRoot()
    }

    private fun destroyRoot() {
        if (root != null) root!!.destroy()
        root = null
    }

    private fun destroyPreviousRoot() {
        if (previousRoot != null) previousRoot!!.destroy()
        previousRoot = null
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {}
    fun setRoot(viewController: ViewController<*>?, commandListener: CommandListener?, reactInstanceManager: ReactInstanceManager?) {
        previousRoot = root
        modalStack.destroy()
        view
        root = viewController
        root!!.setOverlay(RootOverlay(activity, contentLayout!!))
        rootPresenter.setRoot(root, defaultOptions, object : CommandListenerAdapter(commandListener) {
            override fun onSuccess(childId: String) {
                root!!.onViewDidAppear()
                if (removeSplashView) contentLayout!!.removeViewAt(0)
                removeSplashView = false
                destroyPreviousRoot()
                super.onSuccess(childId)
            }
        }, reactInstanceManager)
    }

    fun mergeOptions(componentId: String?, options: Options?) {
        val target = findController(componentId)
        target?.mergeOptions(options)
    }

    fun push(id: String, viewController: ViewController<ViewGroup>, listener: CommandListener) {
        applyOnStack(id, listener, Func1 { stack: StackController? -> stack?.push(viewController, listener) })
    }

    fun setStackRoot(id: String, children: List<ViewController<ViewGroup>>, listener: CommandListener) {
        applyOnStack(id, listener, Func1 { stack: StackController? -> stack?.setRoot(children, listener) })
    }

    fun pop(id: String, mergeOptions: Options?, listener: CommandListener) {
        applyOnStack(id, listener, Func1 { stack: StackController? -> stack?.pop(mergeOptions, listener) })
    }

    fun popToRoot(id: String, mergeOptions: Options?, listener: CommandListener) {
        applyOnStack(id, listener, Func1 { stack: StackController? -> stack?.popToRoot(mergeOptions, listener) })
    }

    fun popTo(id: String, mergeOptions: Options?, listener: CommandListener) {
        val target = findController(id)
        if (target != null) {
            target.performOnParentStack(Func1 { stack: StackController? -> stack!!.popTo(target, mergeOptions, listener) })
        } else {
            listener.onError("Failed to execute stack command. Stack by $id not found.")
        }
    }

    fun showModal(viewController: ViewController<*>?, listener: CommandListener?) {
        modalStack.showModal(viewController, root, listener)
    }

    fun dismissModal(componentId: String?, listener: CommandListener) {
        if (removeSplashView && modalStack.size() == 1) {
            listener.onError("Can not dismiss modal if root is not set and only one modal is displayed.")
            return
        }
        modalStack.dismissModal(componentId, root, listener)
    }

    fun dismissAllModals(mergeOptions: Options?, listener: CommandListener?) {
        modalStack.dismissAllModals(root, mergeOptions, listener)
    }

    fun showOverlay(overlay: ViewController<*>?, listener: CommandListener?) {
        overlayManager.show(overlaysLayout, overlay, listener)
    }

    fun dismissOverlay(componentId: String?, listener: CommandListener?) {
        overlayManager.dismiss(overlaysLayout, componentId, listener)
    }

    fun dismissAllOverlays(listener: CommandListener?) {
        overlayManager.dismissAll(overlaysLayout, listener)
    }

    override fun findController(id: String?): ViewController<ViewGroup>? {
        var controllerById = super.findController(id)
        if (controllerById == null) {
            controllerById = modalStack.findControllerById(id)
        }
        if (controllerById == null) {
            controllerById = overlayManager.findControllerById(id)
        }
        return controllerById
    }

    private fun applyOnStack(fromId: String, listener: CommandListener, task: Func1<StackController?>) {
        val from = findController(fromId)
        if (from != null) {
            if (from is StackController) {
                task.run(from as StackController?)
            } else {
                from.performOnParentStack(task)
            }
        } else {
            listener.onError("Failed to execute stack command. Stack $fromId not found.")
        }
    }

}