package com.reactnativenavigation.viewcontrollers.toptabs

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.utils.Functions.Func1
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewVisibilityListenerAdapter
import com.reactnativenavigation.views.toptabs.TopTabsLayoutCreator
import com.reactnativenavigation.views.toptabs.TopTabsViewPager

class TopTabsController(
        activity: Activity,
        childRegistry: ChildControllersRegistry,
        id: String,
        private val tabs: List<ViewController<ViewGroup>>,
        private val viewCreator: TopTabsLayoutCreator,
        options: Options,
        presenter: Presenter
) : ParentController<TopTabsViewPager>(activity!!, childRegistry!!, id!!, presenter!!, options!!) {
    override val currentChild: ViewController<*>
        get() = tabs[view!!.currentItem]

    override fun createView(): TopTabsViewPager {
        return viewCreator.create()
    }

    override val childControllers: Collection<ViewController<*>>
        get() = tabs

    init {
        tabs.forEach {
            it.setViewVisibilityListener(object : ViewVisibilityListenerAdapter() {
                override fun onViewAppeared(view: View?): Boolean {
                    it.parentController = this@TopTabsController
                    return this@TopTabsController.view.isCurrentView(view) ?: false
                }
            })
        }
    }

    override fun onViewWillAppear() {
        super.onViewWillAppear()
        performOnParentController(Func1 { parentController: ParentController<*> -> parentController.setupTopTabsWithViewPager(view) })
        performOnCurrentTab(Func1 { obj: ViewController<*> -> obj.onViewWillAppear() })
    }

    override fun onViewWillDisappear() {
        super.onViewWillDisappear()
    }

    override fun onViewDisappear() {
        super.onViewDisappear()
        performOnCurrentTab(Func1 { obj: ViewController<*> -> obj.onViewDisappear() })
        performOnParentController(Func1 { obj: ParentController<*> -> obj.clearTopTabs() })
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        performOnCurrentTab(Func1 { tab: ViewController<*> -> tab.sendOnNavigationButtonPressed(buttonId) })
    }

    override fun applyOptions(options: Options?) {
        super.applyOptions(options)
        view!!.applyOptions(options)
    }

    override fun applyChildOptions(options: Options?, child: ViewController<*>?) {
        super.applyChildOptions(options, child)
        performOnParentController(Func1 { parentController: ParentController<*> -> parentController.applyChildOptions(this.options.copy(), child) })
    }

    @CallSuper
    override fun mergeChildOptions(options: Options?, child: ViewController<*>?) {
        super.mergeChildOptions(options, child)
        performOnParentController(Func1 { parentController: ParentController<*> -> parentController.applyChildOptions(options!!.copy(), child) })
    }

    fun switchToTab(index: Int) {
        view.switchToTab(index)
        currentChild.onViewDidAppear()
    }

    private fun performOnCurrentTab(task: Func1<ViewController<*>>) {
        task.run(tabs[view.currentItem])
    }


}