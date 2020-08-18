package com.reactnativenavigation.viewcontrollers.splitview

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController

class SplitViewController(activity: Activity,
                          childRegistry: ChildControllersRegistry,
                          id: String,
                          private val master: ViewController<ViewGroup>,
                          private val details: ViewController<ViewGroup>,
                          presenter: Presenter,
                          initialOptions: Options) : ParentController<LinearLayout>(activity, childRegistry, id, presenter, initialOptions) {

    override fun getCurrentChild(): ViewController<*> {
        return master
    }

    override fun createView(): LinearLayout {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            addView(master.view)
            addView(details.view)
            val masterLayoutParams: LinearLayout.LayoutParams = master.view.layoutParams as LinearLayout.LayoutParams
            val detailsLayoutParams: LinearLayout.LayoutParams = details.view.layoutParams as LinearLayout.LayoutParams
            masterLayoutParams.weight = 3f
            detailsLayoutParams.weight = 1f
            master.view.layoutParams = masterLayoutParams
            details.view.layoutParams = detailsLayoutParams
        }
    }

    override fun getChildControllers(): MutableCollection<out ViewController<ViewGroup>> {
        return mutableListOf(master,details)
    }

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        Log.e("Daniel", "Clicked $buttonId")
    }
}