package com.reactnativenavigation.viewcontrollers.splitview

import android.app.Activity
import android.view.View
import android.widget.LinearLayout
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter

class SplitViewPresenter(val activity: Activity, val options: Options): Presenter(activity, options) {
    private lateinit var splitView: LinearLayout
    private lateinit var masterView: View
    private lateinit var detailsView: View

    fun bindView(splitView: LinearLayout, master: View, details: View) {
        this.splitView = splitView
        masterView = master
        detailsView = details
    }

    fun applyOptions(options: Options) {
        with(splitView) {
            addView(masterView)
        }
        applyLayoutDirection(leftToRight = options.splitViewOptions.primaryEdge ?: true)
    }

    protected fun applyLayoutDirection(leftToRight: Boolean) {
        with(splitView) {
            if (leftToRight) {
                addView(detailsView)
            } else {
                addView(detailsView, 0)
            }
        }
    }
}