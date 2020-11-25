package com.reactnativenavigation.viewcontrollers.bottomtabs

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import com.reactnativenavigation.options.AnimationsOptions
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.animations.ViewAnimationOptions
import com.reactnativenavigation.views.bottomtabs.BottomTabs

class BottomTabsAnimator(private val bottomTabs: BottomTabs) {
    fun hide(animationsOptions: AnimationsOptions) {
        if (animationsOptions.pop.bottomTabs.exit.hasValue()) {
            val set = animationsOptions.pop.bottomTabs.exit.getAnimation(bottomTabs)
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bottomTabs.hideBottomNavigation(false)
                }
            })
            set.start()
        } else {
            bottomTabs.hideBottomNavigation()
        }
    }

    fun show(animationsOptions: AnimationsOptions) {
        if (animationsOptions.push.bottomTabs.enter.hasValue()) {
            val set = animationsOptions.push.bottomTabs.enter.getAnimation(bottomTabs)
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    bottomTabs.restoreBottomNavigation(false)
                }
            })
            set.start()
        } else {
            bottomTabs.restoreBottomNavigation()
        }
    }

    fun getPushAnimation(appearingOptions: Options?, bottomTabs: ViewAnimationOptions?): Animator {
        return AnimatorSet()
    }
}