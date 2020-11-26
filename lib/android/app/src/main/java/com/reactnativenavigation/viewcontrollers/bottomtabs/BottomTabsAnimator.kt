package com.reactnativenavigation.viewcontrollers.bottomtabs

import com.reactnativenavigation.views.animations.BaseViewAnimator
import com.reactnativenavigation.views.bottomtabs.BottomTabs

class BottomTabsAnimator(private val bottomTabs: BottomTabs) : BaseViewAnimator<BottomTabs>(HideDirection.Down, bottomTabs) {
    override fun onShowAnimationEnd() {
        bottomTabs.restoreBottomNavigation(false)
    }

    override fun onHideAnimationEnd() {
        bottomTabs.hideBottomNavigation(false)
    }
}