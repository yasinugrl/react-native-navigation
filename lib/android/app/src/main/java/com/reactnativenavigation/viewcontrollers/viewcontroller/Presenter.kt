package com.reactnativenavigation.viewcontrollers.viewcontroller

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.reactnativenavigation.options.NavigationBarOptions
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.OrientationOptions
import com.reactnativenavigation.options.StatusBarOptions
import com.reactnativenavigation.options.StatusBarOptions.TextColorScheme
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.utils.StatusBarUtils
import com.reactnativenavigation.utils.logd
import com.reactnativenavigation.viewcontrollers.navigator.Navigator
import com.reactnativenavigation.viewcontrollers.parent.ParentController

class Presenter(private val activity: Activity, private var defaultOptions: Options) {
    fun setDefaultOptions(defaultOptions: Options) {
        this.defaultOptions = defaultOptions
    }

    fun mergeOptions(view: View, options: Options) {
        logd("options:${options}","mergeOptions")
        logd("statusBar:${options.statusBar}","mergeOptions")
        logd("navigationBar:${options.navigationBar}","mergeOptions")
        mergeStatusBarOptions(view, options.statusBar)
        mergeNavigationBarOptions(options.navigationBar)
    }

    fun applyOptions(view: ViewController<*>, options: Options) {
        logd("options:${options}","applyOptions")
        logd("statusBar:${options.statusBar}","applyOptions")
        logd("navigationBar:${options.navigationBar}","applyOptions")
        val withDefaultOptions = options.copy().withDefaultOptions(defaultOptions)
        applyOrientation(withDefaultOptions.layout.orientation)
        applyViewOptions(view, withDefaultOptions)
        applyStatusBarOptions(withDefaultOptions)
        applyNavigationBarOptions(withDefaultOptions.navigationBar)
    }

    fun onViewBroughtToFront(options: Options) {
        val withDefaultOptions = options.copy().withDefaultOptions(defaultOptions)
        applyStatusBarOptions(withDefaultOptions)
    }

    private fun applyOrientation(options: OrientationOptions) {
        activity.requestedOrientation = options.value
    }

    private fun applyViewOptions(view: ViewController<*>, options: Options) {
        applyBackgroundColor(view, options)
        applyTopMargin(view.view!!, options)
    }

    private fun applyTopMargin(view: View, options: Options) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams && options.layout.topMargin.hasValue()) {
            (view.layoutParams as ViewGroup.MarginLayoutParams).topMargin = options.layout.topMargin[0]
        }
    }

    private fun applyBackgroundColor(view: ViewController<*>, options: Options) {
        if (options.layout.backgroundColor.hasValue()) {
            if (view is Navigator) return
            val ld = LayerDrawable(arrayOf<Drawable>(ColorDrawable(options.layout.backgroundColor.get())))
            var top = if (view.resolveCurrentOptions()!!.statusBar.drawBehind.isTrue) 0 else StatusBarUtils.getStatusBarHeight(view.activity)
            if (view !is ParentController<*>) {
                val lp = view.view!!.layoutParams as ViewGroup.MarginLayoutParams
                if (lp.topMargin != 0) top = 0
            }
            ld.setLayerInset(0, 0, top, 0, 0)
            view.view!!.background = ld
        }
    }

    private fun applyStatusBarOptions(options: Options) {
        val statusBar = options.copy().withDefaultOptions(defaultOptions).statusBar
        setStatusBarBackgroundColor(statusBar)
        setTextColorScheme(statusBar.textColorScheme)
        setTranslucent(statusBar)
        setStatusBarVisible(statusBar.visible)
    }

    private fun setTranslucent(options: StatusBarOptions) {
        val window = activity.window
        if (options.translucent.isTrue) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (StatusBarUtils.isTranslucent(window)) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun setStatusBarVisible(visible: Bool) {
        val decorView = activity.window.decorView
        var flags = decorView.systemUiVisibility
        flags = if (visible.isFalse) {
            flags or View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            flags and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
        }
        decorView.systemUiVisibility = flags
    }

    private fun setStatusBarBackgroundColor(statusBar: StatusBarOptions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && statusBar.backgroundColor.canApplyValue()) {
            val defaultColor = if (statusBar.visible.isTrueOrUndefined) Color.BLACK else Color.TRANSPARENT
            activity.window.statusBarColor = statusBar.backgroundColor[defaultColor]
        }
    }

    private fun setTextColorScheme(scheme: TextColorScheme) {
        val view = activity.window.decorView
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        if (scheme == TextColorScheme.Dark) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        } else {
            clearDarkTextColorScheme(view)
        }
    }

    private fun clearDarkTextColorScheme(view: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        var flags = view.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        view.systemUiVisibility = flags
    }

    private fun mergeStatusBarOptions(view: View, statusBar: StatusBarOptions) {
        mergeStatusBarBackgroundColor(statusBar)
        mergeTextColorScheme(statusBar.textColorScheme)
        mergeTranslucent(statusBar)
        mergeStatusBarVisible(view, statusBar.visible, statusBar.drawBehind)
    }

    private fun mergeStatusBarBackgroundColor(statusBar: StatusBarOptions) {
        if (statusBar.backgroundColor.hasValue() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = statusBar.backgroundColor[Color.BLACK]
        }
    }

    private fun mergeTextColorScheme(scheme: TextColorScheme) {
        if (!scheme.hasValue() || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val view = activity.window.decorView
        if (scheme == TextColorScheme.Dark) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        } else {
            clearDarkTextColorScheme(view)
        }
    }

    private fun mergeTranslucent(options: StatusBarOptions) {
        val window = activity.window
        if (options.translucent.isTrue) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (options.translucent.isFalse && StatusBarUtils.isTranslucent(window)) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun mergeStatusBarVisible(view: View, visible: Bool, drawBehind: Bool) {
        if (visible.hasValue()) {
            var flags = view.systemUiVisibility
            flags = if (visible.isTrue) {
                flags and (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv() and View.SYSTEM_UI_FLAG_FULLSCREEN.inv())
            } else {
                flags or (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
            if (flags != view.systemUiVisibility) view.requestLayout()
            view.systemUiVisibility = flags
        } else if (drawBehind.hasValue()) {
            if (drawBehind.isTrue) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
            }
        }
    }

    private fun applyNavigationBarOptions(options: NavigationBarOptions) {
        applyNavigationBarVisibility(options)
        setNavigationBarBackgroundColor(options)
    }

    private fun mergeNavigationBarOptions(options: NavigationBarOptions) {
        mergeNavigationBarVisibility(options)
        setNavigationBarBackgroundColor(options)
    }

    private fun mergeNavigationBarVisibility(options: NavigationBarOptions) {
        if (options.isVisible.hasValue()) applyNavigationBarOptions(options)
    }

    private fun applyNavigationBarVisibility(options: NavigationBarOptions) {
        val decorView = activity.window.decorView
        var flags = decorView.systemUiVisibility
        val defaultVisibility = flags and View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION == 0
        val hideNavigationBarFlags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        flags = if (options.isVisible[defaultVisibility]) {
            flags and hideNavigationBarFlags.inv()
        } else {
            flags or hideNavigationBarFlags
        }
        decorView.systemUiVisibility = flags
    }

    private fun setNavigationBarBackgroundColor(navigationBar: NavigationBarOptions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && navigationBar.backgroundColor.canApplyValue()) {
            val defaultColor = activity.window.navigationBarColor
            val color = navigationBar.backgroundColor[defaultColor]
            activity.window.navigationBarColor = color
            setNavigationBarButtonsColor(color)
        }
    }

    private fun setNavigationBarButtonsColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = activity.window.decorView
            var flags = decorView.systemUiVisibility
            flags = if (isColorLight(color)) {
                flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
            decorView.systemUiVisibility = flags
        }
    }

    private fun isColorLight(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.5
    }

}