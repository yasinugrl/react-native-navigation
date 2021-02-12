package com.reactnativenavigation.viewcontrollers.stack

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.annotation.Size
import androidx.annotation.VisibleForTesting
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.react.CommandListener
import com.reactnativenavigation.react.CommandListenerAdapter
import com.reactnativenavigation.react.Constants
import com.reactnativenavigation.react.events.EventEmitter
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.CompatUtils
import com.reactnativenavigation.utils.CoordinatorLayoutUtils
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.ObjectUtils
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.stack.topbar.TopBarController
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.BackButtonHelper
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.ButtonController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.component.Component
import com.reactnativenavigation.views.stack.StackBehaviour
import com.reactnativenavigation.views.stack.StackLayout
import com.reactnativenavigation.views.stack.fab.Fab
import com.reactnativenavigation.views.stack.fab.FabMenu
import com.reactnativenavigation.views.stack.topbar.TopBar
import java.util.*

class StackController(
        activity: Activity,
        children: List<ViewController<ViewGroup>>,
        childRegistry: ChildControllersRegistry,
        private val eventEmitter: EventEmitter,
        private val topBarController:
        TopBarController,
        private val animator: StackAnimator,
        id: String,
        initialOptions: Options,
        private val backButtonHelper: BackButtonHelper,
        private val stackPresenter: StackPresenter,
        presenter: Presenter,
        private val fabPresenter: FabPresenter
) : ParentController<StackLayout>(activity!!, childRegistry!!, id!!, presenter!!, initialOptions!!) {
    private var stack = IdStack<ViewController<ViewGroup>>()
    override val isRendered: Boolean
        get() {
            if (isEmpty) return false
            if (currentChild!!.isDestroyed) return false
            val currentChild = currentChild!!.view
            return if (currentChild is Component) {
                super.isRendered && stackPresenter.isRendered(currentChild)
            } else super.isRendered
        }

    override fun setDefaultOptions(defaultOptions: Options?) {
        super.setDefaultOptions(defaultOptions)
        stackPresenter.defaultOptions = defaultOptions
    }

    override val currentChild: ViewController<*>?
        get() = stack.peek()

    override fun onAttachToParent() {
        if (!isEmpty && !currentChild!!.isDestroyed && !isViewShown) {
            stackPresenter.applyChildOptions(resolveCurrentOptions(), this, currentChild)
        }
    }

    override fun mergeOptions(options: Options?) {
        if (isViewShown) stackPresenter.mergeOptions(options, this, currentChild)
        super.mergeOptions(options)
    }

    override fun applyChildOptions(options: Options?, child: ViewController<*>?) {
        super.applyChildOptions(options, child)
        stackPresenter.applyChildOptions(resolveCurrentOptions(), this, child)
        fabPresenter.applyOptions(this.options.fabOptions, child!!, view!!)
        performOnParentController(Func1 { parent: ParentController<*> ->
            parent.applyChildOptions(
                    this.options.copy()
                            .clearTopBarOptions()
                            .clearAnimationOptions()
                            .clearFabOptions()
                            .clearTopTabOptions()
                            .clearTopTabsOptions(),
                    child
            )
        }
        )
    }

    override fun mergeChildOptions(options: Options?, child: ViewController<*>?) {
        super.mergeChildOptions(options, child)
        if (child?.isViewShown == true && peek() === child) {
            stackPresenter.mergeChildOptions(options, resolveCurrentOptions(), this, child)
            if (options!!.fabOptions.hasValue()) {
                fabPresenter.mergeOptions(options.fabOptions, child, view!!)
            }
        }
        performOnParentController(Func1 { parent: ParentController<*> ->
            parent.mergeChildOptions(
                    options!!.copy()
                            .clearTopBarOptions()
                            .clearAnimationOptions()
                            .clearFabOptions()
                            .clearTopTabOptions()
                            .clearTopTabsOptions(),
                    child
            )
        }
        )
    }

    override fun onChildDestroyed(child: ViewController<*>?) {
        super.onChildDestroyed(child)
        stackPresenter.onChildDestroyed(child)
    }

    fun push(child: ViewController<*>, listener: CommandListener) {
        if (findController(child.id) != null) {
            listener.onError("A stack can't contain two children with the same id")
            return
        }
        val toRemove = stack.peek()
        if (size() > 0) backButtonHelper.addToPushedChild(child)
        child.parentController = this
        stack.push(child.id, child)
        val resolvedOptions = resolveCurrentOptions(stackPresenter.defaultOptions)
        addChildToStack(child, resolvedOptions)
        if (toRemove != null) {
            val animation = resolvedOptions.animations.push
            if (animation.enabled.isTrueOrUndefined) {
                animator.push(
                        child,
                        toRemove,
                        resolvedOptions,
                        stackPresenter.getAdditionalPushAnimations(this, child, resolvedOptions),
                        Runnable { onPushAnimationComplete(child, toRemove, listener) })
            } else {
                child.onViewDidAppear()
                view!!.removeView(toRemove.view)
                listener.onSuccess(child.id)
            }
        } else {
            listener.onSuccess(child.id)
        }
    }

    private fun onPushAnimationComplete(toAdd: ViewController<*>, toRemove: ViewController<*>, listener: CommandListener) {
        toAdd.onViewDidAppear()
        if (peek() != toRemove) view!!.removeView(toRemove.view)
        listener.onSuccess(toAdd.id)
    }

    private fun addChildToStack(child: ViewController<*>, resolvedOptions: Options) {
        child.setWaitForRender(resolvedOptions.animations.push.waitForRender)
        if (size() == 1) stackPresenter.applyInitialChildLayoutOptions(resolvedOptions)
        view!!.addView(child.view, view!!.childCount - 1, CoordinatorLayoutUtils.matchParentWithBehaviour(StackBehaviour(this)))
    }

    fun setRoot(@Size(min = 1) children: List<ViewController<*>>, listener: CommandListener) {
        animator.cancelPushAnimations()
        val toRemove = stack.peek()
        val stackToDestroy: IdStack<*> = stack
        stack = IdStack()
        val child = CollectionUtils.requireLast(children)
        if (children.size == 1) {
            backButtonHelper.clear(child)
        } else {
            backButtonHelper.addToPushedChild(child)
        }
        child.parentController = this
        stack.push(child.id, child)
        val resolvedOptions = resolveCurrentOptions(stackPresenter.defaultOptions)
        addChildToStack(child, resolvedOptions)
        val listenerAdapter: CommandListener = object : CommandListenerAdapter() {
            override fun onSuccess(childId: String) {
                child.onViewDidAppear()
                destroyStack(stackToDestroy)
                if (children.size > 1) {
                    for (i in 0 until children.size - 1) {
                        stack[children[i].id, children[i]] = i
                        children[i].parentController = this@StackController
                        if (i == 0) {
                            backButtonHelper.clear(children[i])
                        } else {
                            backButtonHelper.addToPushedChild(children[i])
                        }
                    }
                    startChildrenBellowTopChild()
                }
                listener.onSuccess(childId)
            }
        }
        if (toRemove != null && resolvedOptions.animations.setStackRoot.enabled.isTrueOrUndefined) {
            if (resolvedOptions.animations.setStackRoot.waitForRender.isTrue) {
                child.view.alpha = 0f
                child.addOnAppearedListener(Runnable {
                    animator.setRoot(
                            child,
                            toRemove,
                            resolvedOptions,
                            stackPresenter.getAdditionalSetRootAnimations(this, child, resolvedOptions),
                            Runnable { listenerAdapter.onSuccess(child.id) }
                    )
                }
                )
            } else {
                animator.setRoot(child,
                        toRemove,
                        resolvedOptions,
                        stackPresenter.getAdditionalSetRootAnimations(this, child, resolvedOptions),
                        Runnable { listenerAdapter.onSuccess(child.id) })
            }
        } else {
            listenerAdapter.onSuccess(child.id)
        }
    }

    private fun destroyStack(stack: IdStack<*>) {
        for (s in stack) {
            (stack[s] as ViewController<*>).destroy()
        }
    }

    fun pop(mergeOptions: Options?, listener: CommandListener?) {
        if (!canPop()) {
            listener!!.onError("Nothing to pop")
            return
        }
        peek()!!.mergeOptions(mergeOptions)
        val disappearingOptions = resolveCurrentOptions(stackPresenter.defaultOptions)
        val disappearing = stack.pop()
        val appearing = stack.peek()
        disappearing!!.onViewWillDisappear()
        val appearingView = appearing!!.view
        if (appearingView.layoutParams == null) {
            appearingView.layoutParams = CoordinatorLayoutUtils.matchParentWithBehaviour(StackBehaviour(this))
        }
        if (appearingView.parent == null) {
            view!!.addView(appearingView, 0)
        }
        if (disappearingOptions.animations.pop.enabled.isTrueOrUndefined) {
            val appearingOptions = resolveChildOptions(appearing)!!.withDefaultOptions(stackPresenter.defaultOptions)
            animator.pop(
                    appearing,
                    disappearing,
                    disappearingOptions,
                    stackPresenter.getAdditionalPopAnimations(appearingOptions, disappearingOptions),
                    Runnable { finishPopping(appearing, disappearing, listener) }
            )
        } else {
            finishPopping(appearing, disappearing, listener)
        }
    }

    private fun finishPopping(appearing: ViewController<*>?, disappearing: ViewController<*>?, listener: CommandListener?) {
        appearing!!.onViewDidAppear()
        disappearing!!.destroy()
        listener!!.onSuccess(disappearing.id)
        eventEmitter.emitScreenPoppedEvent(disappearing.id)
    }

    fun popTo(viewController: ViewController<*>, mergeOptions: Options?, listener: CommandListener) {
        if (!stack.containsId(viewController.id) || peek() == viewController) {
            listener.onError("Nothing to pop")
            return
        }
        animator.cancelPushAnimations()
        var currentControlId: String
        for (i in stack.size() - 2 downTo 0) {
            currentControlId = stack[i]!!.id
            if (currentControlId == viewController.id) {
                break
            }
            val controller = stack[currentControlId]
            stack.remove(controller!!.id)
            controller.destroy()
        }
        pop(mergeOptions, listener)
    }

    fun popToRoot(mergeOptions: Options?, listener: CommandListener) {
        if (!canPop()) {
            listener.onSuccess("")
            return
        }
        animator.cancelPushAnimations()
        val iterator: Iterator<String> = stack.iterator()
        iterator.next()
        while (stack.size() > 2) {
            val controller = stack[iterator.next()]
            if (!stack.isTop(controller!!.id)) {
                stack.remove(iterator, controller.id)
                controller.destroy()
            }
        }
        pop(mergeOptions, listener)
    }

    fun peek(): ViewController<*>? {
        return stack.peek()
    }

    fun size(): Int {
        return stack.size()
    }

    val isEmpty: Boolean
        get() = stack.isEmpty

    fun isChildInTransition(child: ViewController<*>?): Boolean {
        return animator.isChildInTransition(child)
    }

    override fun handleBack(listener: CommandListener?): Boolean {
        if (canPop()) {
            pop(Options.EMPTY, listener)
            return true
        }
        return false
    }

    @VisibleForTesting
    fun canPop(): Boolean {
        return stack.size() > 1
    }

    override fun createView(): StackLayout {
        val stackLayout = StackLayout(activity, topBarController, id)
        stackPresenter.bindView(topBarController, bottomTabsController)
        addInitialChild(stackLayout)
        return stackLayout
    }

    private fun addInitialChild(stackLayout: StackLayout) {
        if (isEmpty) return
        val child = peek()!!.view
        child.id = CompatUtils.generateViewId()
        peek()!!.addOnAppearedListener(Runnable { startChildrenBellowTopChild() })
        stackPresenter.applyInitialChildLayoutOptions(resolveCurrentOptions())
        stackLayout.addView(child, 0, CoordinatorLayoutUtils.matchParentWithBehaviour(StackBehaviour(this)))
    }

    private fun startChildrenBellowTopChild() {
        val children: ArrayList<ViewController<*>?> = ArrayList(childControllers)
        for (i in children.size - 2 downTo 0) {
            children[i]?.start()
        }
    }

    private fun onNavigationButtonPressed(buttonId: String) {
        if (Constants.BACK_BUTTON_ID == buttonId) {
            pop(Options.EMPTY, CommandListenerAdapter())
        } else {
            sendOnNavigationButtonPressed(buttonId)
        }
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        peek()!!.sendOnNavigationButtonPressed(buttonId)
    }

    override val childControllers: Collection<ViewController<ViewGroup>>
        get() = stack.values()

    override fun setupTopTabsWithViewPager(viewPager: ViewPager?) {
        topBarController.initTopTabs(viewPager)
    }

    override fun clearTopTabs() {
        topBarController.clearTopTabs()
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ViewGroup, dependency: View): Boolean {
        ObjectUtils.perform<ViewController<ViewGroup>?>(findController(child), { controller: ViewController<ViewGroup>? ->
            if (dependency is TopBar) stackPresenter.applyTopInsets(this, controller)
            if (dependency is Fab || dependency is FabMenu) CoordinatorLayoutUtils.updateBottomMargin(dependency, bottomInset)
        })
        return false
    }

    override fun getTopInset(child: ViewController<*>?): Int {
        return stackPresenter.getTopInset(resolveChildOptions(child!!))
    }

    @get:RestrictTo(RestrictTo.Scope.TESTS)
    val topBar: TopBar
        get() = topBarController.view

    @get:RestrictTo(RestrictTo.Scope.TESTS)
    val stackLayout: StackLayout
        get() = view

    init {
        stackPresenter.setButtonOnClickListener(object : ButtonController.OnClickListener {
            override fun onPress(buttonId: String?) = buttonId?.let { onNavigationButtonPressed(it) } ?: Unit
        })
        for (child in children) {
            child.parentController = this
            stack.push(child.id, child)
            if (size() > 1) backButtonHelper.addToPushedChild(child)
        }
    }
}