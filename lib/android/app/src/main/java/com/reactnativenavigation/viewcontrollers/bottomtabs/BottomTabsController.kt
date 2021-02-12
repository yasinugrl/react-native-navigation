package com.reactnativenavigation.viewcontrollers.bottomtabs

import android.animation.Animator
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.react.CommandListener
import com.reactnativenavigation.react.events.EventEmitter
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.utils.ImageLoader
import com.reactnativenavigation.utils.ObjectUtils
import com.reactnativenavigation.viewcontrollers.bottomtabs.attacher.BottomTabsAttacher
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.bottomtabs.BottomTabs
import com.reactnativenavigation.views.bottomtabs.BottomTabsContainer
import com.reactnativenavigation.views.bottomtabs.BottomTabsLayout

open class BottomTabsController(
        activity: Activity,
        private val tabs: List<ViewController<ViewGroup>>,
        childRegistry: ChildControllersRegistry,
        private val eventEmitter: EventEmitter,
        private val
        imageLoader: ImageLoader,
        id: String,
        initialOptions: Options,
        presenter: Presenter,
        private val tabsAttacher: BottomTabsAttacher,
        private val bottomTabsPresenter: BottomTabsPresenter,
        private val tabPresenter: BottomTabPresenter
) : ParentController<BottomTabsLayout>(activity, childRegistry, id, presenter, initialOptions), AHBottomNavigation.OnTabSelectedListener, TabSelector {
    override val currentChild: ViewController<ViewGroup>
        get() = tabs[if (!::bottomTabs.isInitialized) 0 else bottomTabs.currentItem]

    @get:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    @set:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    lateinit var bottomTabsContainer: BottomTabsContainer

    @get:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    @set:RestrictTo(RestrictTo.Scope.TESTS, RestrictTo.Scope.SUBCLASSES)
    lateinit var bottomTabs: BottomTabs
    val animator: BottomTabsAnimator
        get() = bottomTabsPresenter.animator


    init {
        tabs.forEach {
            it.parentController = this
        }
    }

    override fun setDefaultOptions(defaultOptions: Options?) {
        super.setDefaultOptions(defaultOptions)
        bottomTabsPresenter.setDefaultOptions(defaultOptions!!)
        tabPresenter.setDefaultOptions(defaultOptions)
    }

    override fun createView(): BottomTabsLayout {
        val root = BottomTabsLayout(activity)
        bottomTabsContainer = createBottomTabsContainer()
        bottomTabs = bottomTabsContainer!!.bottomTabs
        val resolveCurrentOptions = resolveCurrentOptions()
        tabsAttacher.init(root, resolveCurrentOptions)
        bottomTabsPresenter.bindView(bottomTabsContainer!!, this)
        tabPresenter.bindView(bottomTabs)
        bottomTabs!!.setOnTabSelectedListener(this)
        root.addBottomTabsContainer(bottomTabsContainer)
        bottomTabs!!.addItems(createTabs())
        setInitialTab(resolveCurrentOptions)
        tabsAttacher.attach()
        return root
    }

    private fun setInitialTab(resolveCurrentOptions: Options) {
        var initialTabIndex = 0
        if (resolveCurrentOptions.bottomTabsOptions.currentTabId.hasValue()) initialTabIndex = bottomTabsPresenter.findTabIndexByTabId(resolveCurrentOptions.bottomTabsOptions.currentTabId.get()) else if (resolveCurrentOptions.bottomTabsOptions.currentTabIndex.hasValue()) {
            initialTabIndex = resolveCurrentOptions.bottomTabsOptions.currentTabIndex.get()
        }
        bottomTabs!!.setCurrentItem(initialTabIndex, false)
    }

    protected open fun createBottomTabsContainer(): BottomTabsContainer {
        return BottomTabsContainer(activity, createBottomTabs())
    }

    protected open fun createBottomTabs(): BottomTabs {
        return BottomTabs(activity)
    }

    override fun applyOptions(options: Options?) {
        super.applyOptions(options)
        bottomTabs!!.disableItemsCreation()
        bottomTabsPresenter.applyOptions(options!!)
        tabPresenter.applyOptions()
        bottomTabs!!.enableItemsCreation()
        this.options.bottomTabsOptions.clearOneTimeOptions()
        initialOptions.bottomTabsOptions.clearOneTimeOptions()
    }

    override fun mergeOptions(options: Options?) {
        bottomTabsPresenter.mergeOptions(options!!, this)
        tabPresenter.mergeOptions(options)
        super.mergeOptions(options)
        this.options.bottomTabsOptions.clearOneTimeOptions()
        initialOptions.bottomTabsOptions.clearOneTimeOptions()
    }

    override fun applyChildOptions(options: Options?, child: ViewController<*>?) {
        super.applyChildOptions(options, child)
        bottomTabsPresenter.applyChildOptions(resolveCurrentOptions(), child!!)
        performOnParentController(Func1 { parent: ParentController<*> ->
            parent.applyChildOptions(
                    this.options.copy()
                            .clearBottomTabsOptions()
                            .clearBottomTabOptions(),
                    child
            )
        }
        )
    }

    override fun mergeChildOptions(options: Options?, child: ViewController<*>?) {
        super.mergeChildOptions(options, child)
        bottomTabsPresenter.mergeChildOptions(options!!, child!!)
        tabPresenter.mergeChildOptions(options, child)
        performOnParentController(Func1 { parent: ParentController<*> -> parent.mergeChildOptions(options.copy().clearBottomTabsOptions(), child) })
    }

    override fun handleBack(listener: CommandListener?): Boolean {
        return !tabs.isEmpty() && tabs[bottomTabs!!.currentItem].handleBack(listener)
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        currentChild.sendOnNavigationButtonPressed(buttonId)
    }


    override fun onTabSelected(index: Int, wasSelected: Boolean): Boolean {
        val options = tabs[index].resolveCurrentOptions().bottomTabOptions
        eventEmitter.emitBottomTabPressed(index)
        if (options.selectTabOnPress[true]) {
            eventEmitter.emitBottomTabSelected(bottomTabs!!.currentItem, index)
            if (wasSelected) return false
            selectTab(index)
        }
        return false
    }

    private fun createTabs(): List<AHBottomNavigationItem>? {
        if (tabs.size > 5) throw RuntimeException("Too many tabs!")
        return CollectionUtils.map(tabs) { tab: ViewController<*> ->
            val options = tab.resolveCurrentOptions().bottomTabOptions
            AHBottomNavigationItem(
                    options.text[""],
                    imageLoader.loadIcon(activity, options.icon[null]),
                    imageLoader.loadIcon(activity, options.selectedIcon[null]),
                    options.testId[""]
            )
        }
    }

    val selectedIndex: Int
        get() = bottomTabs!!.currentItem

    override fun onMeasureChild(parent: CoordinatorLayout, child: ViewGroup, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        ObjectUtils.perform<ViewController<ViewGroup>>(findController(child), Func1 { obj: ViewController<ViewGroup> -> obj.applyBottomInset() })
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    override fun getBottomInset(child: ViewController<*>?): Int {
        return bottomTabsPresenter.getBottomInset(child?.let { resolveChildOptions(it) }
                ?: initialOptions) + ObjectUtils.perform(parentController, 0, { p: ParentController<ViewGroup> -> p.getBottomInset(this) })
    }

    override fun applyBottomInset() {
        bottomTabsPresenter.applyBottomInset(bottomInset)
        super.applyBottomInset()
    }

    override val childControllers: Collection<ViewController<*>>
        get() = tabs

    override fun destroy() {
        tabsAttacher.destroy()
        super.destroy()
    }

    override fun selectTab(newIndex: Int) {
        tabsAttacher.onTabSelected(tabs[newIndex])
        currentView.visibility = View.INVISIBLE
        bottomTabs.setCurrentItem(newIndex, false)
        currentView.visibility = View.VISIBLE
        currentChild.onViewDidAppear()
    }

    private val currentView: ViewGroup
        get() = tabs[bottomTabs.currentItem].view

    fun getPushAnimation(appearingOptions: Options?): Animator? {
        return bottomTabsPresenter.getPushAnimation(appearingOptions!!)
    }

    fun getSetStackRootAnimation(appearingOptions: Options?): Animator? {
        return bottomTabsPresenter.getSetStackRootAnimation(appearingOptions!!)
    }

    fun getPopAnimation(appearingOptions: Options?, disappearingOptions: Options?): Animator? {
        return bottomTabsPresenter.getPopAnimation(appearingOptions!!, disappearingOptions!!)
    }

}