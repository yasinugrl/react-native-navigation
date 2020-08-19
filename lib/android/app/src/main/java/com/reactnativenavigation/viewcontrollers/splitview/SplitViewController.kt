package com.reactnativenavigation.viewcontrollers.splitview

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController

class SplitViewController(activity: Activity,
                          childRegistry: ChildControllersRegistry,
                          id: String,
                          private val master: ViewController<ViewGroup>,
                          private val details: ViewController<ViewGroup>,
                          private val presenter: SplitViewPresenter,
                          initialOptions: Options) : ParentController<LinearLayout>(activity, childRegistry, id, presenter, initialOptions) {

    override fun getCurrentChild(): ViewController<*> {
        return master
    }

    override fun createView(): LinearLayout {
        val splitView = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        presenter.bindView(
                splitView = splitView,
                master = master.view,
                details = details.view)
        return splitView
    }

    override fun applyOptions(options: Options) {
        super.applyOptions(options)
        presenter.applyOptions(options)
    }

    override fun getChildControllers(): MutableCollection<out ViewController<ViewGroup>> {
        return mutableListOf(master,details)
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        Log.e("Daniel", "Clicked $buttonId")
    }
}