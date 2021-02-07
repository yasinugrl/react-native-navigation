package com.reactnativenavigation.viewcontrollers.stack.topbar.button

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.ButtonOptions
import com.reactnativenavigation.options.params.Colour
import com.reactnativenavigation.react.events.ComponentType
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.viewcontrollers.viewcontroller.YellowBoxDelegate
import com.reactnativenavigation.viewcontrollers.viewcontroller.overlay.ViewControllerOverlay
import com.reactnativenavigation.views.stack.topbar.titlebar.ButtonsToolbar
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBarButtonCreator
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBarReactButtonView

open class ButtonController(activity: Activity,
                            private val presenter: ButtonPresenter,
                            val button: ButtonOptions,
                            private val viewCreator: TitleBarButtonCreator,
                            private val onPressListener: OnClickListener) : ViewController<TitleBarReactButtonView>(activity, button.id, YellowBoxDelegate(activity), Options(), ViewControllerOverlay(activity)), MenuItem.OnMenuItemClickListener {

    private var menuItem: MenuItem? = null

    interface OnClickListener {
        fun onPress(buttonId: String?)
    }

    val buttonInstanceId: String
        get() = button.instanceId

    val buttonIntId: Int
        get() = button.intId

    @SuppressLint("MissingSuperCall")
    override fun onViewWillAppear() {
        view?.sendComponentStart(ComponentType.Button)
    }

    @SuppressLint("MissingSuperCall")
    override fun onViewDisappear() {
        view?.sendComponentStop(ComponentType.Button)
    }

    override val isRendered: Boolean get() = !button.component.componentId.hasValue() || super.isRendered

    override fun sendOnNavigationButtonPressed(buttonId: String?) {
        buttonId?.let {
            view.sendOnNavigationButtonPressed(buttonId)
        }
    }

    override val currentComponentName: String? get() = button.component.name.get()

    override fun createView(): TitleBarReactButtonView {
        return viewCreator.create(super.activity, button.component)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        onPressListener.onPress(button.id)
        return true
    }

    fun areButtonsEqual(other: ButtonController): Boolean {
        if (other === this) return true
        return if (other.id != id) false else button.equals(other.button)
    }

    fun applyNavigationIcon(toolbar: Toolbar) {
        presenter.applyNavigationIcon(toolbar) {
            onPressListener.onPress(it)
        }
    }

    open fun applyColor(toolbar: Toolbar, color: Colour) = this.menuItem?.let { presenter.applyColor(toolbar, it, color) }

    open fun applyDisabledColor(toolbar: Toolbar, disabledColour: Colour) = this.menuItem?.let { presenter.applyDisabledColor(toolbar, it, disabledColour) }

    fun addToMenu(buttonsBar: ButtonsToolbar, order: Int) {
        if (button.component.hasValue() && buttonsBar.containsButton(menuItem, order)) return
        buttonsBar.menu.removeItem(button.intId)
        menuItem = buttonsBar.addButton(Menu.NONE,
                button.intId,
                order,
                presenter.styledText)?.also { menuItem ->
            menuItem.setOnMenuItemClickListener(this@ButtonController)
            presenter.applyOptions(buttonsBar, menuItem) {
                this@ButtonController.view
            }
        }
    }

}