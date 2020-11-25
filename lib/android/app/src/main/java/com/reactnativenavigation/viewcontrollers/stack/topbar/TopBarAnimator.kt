package com.reactnativenavigation.viewcontrollers.stack.topbar

import android.animation.*
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.RestrictTo
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.reactnativenavigation.options.AnimationOptions
import com.reactnativenavigation.options.Options
import com.reactnativenavigation.options.animations.ViewAnimationOptions
import com.reactnativenavigation.utils.ViewUtils
import com.reactnativenavigation.views.stack.StackLayout
import com.reactnativenavigation.views.stack.topbar.TopBar

class TopBarAnimator {
    companion object {
        private const val DEFAULT_COLLAPSE_DURATION = 100
        private const val DURATION = 300
        private val DECELERATE: TimeInterpolator = DecelerateInterpolator()
        private val LINEAR: TimeInterpolator = LinearInterpolator()
    }

    private enum class AnimationState { Idle, AnimatingEnter, AnimatingExit }

    private lateinit var topBar: TopBar
    private lateinit var stackId: String

    private var showAnimator: Animator = AnimatorSet()
        set(value) {
            field = value
            field.addListener(showAnimatorListener)
        }
    private var hideAnimator: Animator = AnimatorSet()
        set(value) {
            field = value
            field.addListener(hideAnimatorListener)
        }

    private val showAnimatorListener = AnimatorListener(AnimationState.AnimatingEnter, View.VISIBLE)
    private val hideAnimatorListener = AnimatorListener(AnimationState.AnimatingExit, View.GONE)

    private inner class AnimatorListener(private val startState: AnimationState, private val endVisibility: Int) : AnimatorListenerAdapter() {
        var isCancelled = false

        override fun onAnimationStart(animation: Animator?) {
            topBar.resetProperties()
            topBar.visibility = View.VISIBLE
            animationState = startState
        }

        override fun onAnimationCancel(animation: Animator?) {
            isCancelled = true
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (!isCancelled) {
                animationState = AnimationState.Idle
                topBar.visibility = endVisibility
            }
        }
    }


    private var animationState = AnimationState.Idle

    private val isOrWillBeVisible: Boolean
        get() = isFullyVisible || animationState == AnimationState.AnimatingEnter
    private val isFullyVisible: Boolean
        get() = topBar.visibility == View.VISIBLE && animationState == AnimationState.Idle

    private val isOrWillBeHidden: Boolean
        get() = isFullyHidden || animationState == AnimationState.AnimatingExit
    private val isFullyHidden: Boolean
        get() = topBar.visibility == View.GONE && animationState == AnimationState.Idle

    @RestrictTo(RestrictTo.Scope.TESTS)
    constructor(topBar: TopBar) {
        this.topBar = topBar
    }

    constructor()

    fun bindView(topBar: TopBar, stack: StackLayout) {
        this.topBar = topBar
        stackId = stack.stackId
    }

    fun isAnimatingHide() = hideAnimator.isRunning

    fun isAnimatingShow() = showAnimator.isRunning

    fun show(options: AnimationOptions, translationStartDy: Int) {
        if (isOrWillBeVisible) return
        showAnimator = if (options.hasValue()) {
            options.setValueDy(View.TRANSLATION_Y, -translationStartDy.toFloat(), 0f)
            options.getAnimation(topBar)
        } else {
            getDefaultShowAnimator(translationStartDy.toFloat(), DECELERATE, DURATION)
        }
        hideAnimator.cancel()
        showAnimator.start()
    }

    fun show(startTranslation: Float) {
        showAnimator = getDefaultShowAnimator(startTranslation, LINEAR, DEFAULT_COLLAPSE_DURATION)
        hideAnimator.cancel()
        showAnimator.start()
    }

    fun getPushAnimation(appearingOptions: Options, topBar: ViewAnimationOptions): Animator {
        if (isOrWillBeVisible && appearingOptions.topBar.visible.isFalse) {
            showAnimator.cancel()
            hideAnimator = topBar.exit.getAnimation(this.topBar)
            return hideAnimator
        }

        if (isOrWillBeHidden && appearingOptions.topBar.visible.isTrueOrUndefined) {
            hideAnimator.cancel()
            showAnimator = topBar.enter.getAnimation(this.topBar)
            return showAnimator
        }

        return AnimatorSet()
    }

    fun getPopAnimation(appearingOptions: Options, topBar: ViewAnimationOptions): Animator {
        if (isOrWillBeVisible && appearingOptions.topBar.visible.isFalse) {
            showAnimator.cancel()
            hideAnimator = topBar.exit.getAnimation(this.topBar)
            return hideAnimator
        }

        if (isOrWillBeHidden && appearingOptions.topBar.visible.isTrueOrUndefined) {
            hideAnimator.cancel()
            showAnimator = topBar.enter.getAnimation(this.topBar)
            return showAnimator
        }

        return AnimatorSet()
    }

    fun hide(
            options: AnimationOptions,
            translationStartDy: Float,
            translationEndDy: Float,
            onAnimationEnd: Runnable
    ) {
        if (isOrWillBeHidden) return
        hideAnimator = if (options.hasValue()) {
            options.setValueDy(View.TRANSLATION_Y, translationStartDy, -translationEndDy)
            options.getAnimation(topBar)
        } else {
            getDefaultHideAnimator(translationStartDy, translationEndDy, DECELERATE, DURATION)
        }
        hideInternal(onAnimationEnd)
    }

    fun hideOnScroll(translationStart: Float, translationEndDy: Float) {
        hideAnimator = getDefaultHideAnimator(translationStart, translationEndDy, LINEAR, DEFAULT_COLLAPSE_DURATION)
        hideInternal()
    }

    private fun hideInternal(onAnimationStart: Runnable? = null, onAnimationEnd: Runnable? = null) {
        showAnimator.cancel()
        hideAnimator.apply {
            doOnStart { onAnimationStart?.run() }
            doOnEnd { onAnimationEnd?.run() }
            start()
        }
    }

    private fun getDefaultShowAnimator(translationStart: Float, interpolator: TimeInterpolator, duration: Int): Animator {
        return ObjectAnimator.ofFloat(topBar, View.TRANSLATION_Y, -ViewUtils.getHeight(topBar) - translationStart, 0f).apply {
            setInterpolator(interpolator)
            setDuration(duration.toLong())
        }
    }

    private fun getDefaultHideAnimator(translationStart: Float, translationEndDy: Float, interpolator: TimeInterpolator, duration: Int): Animator {
        return ObjectAnimator.ofFloat(topBar, View.TRANSLATION_Y, translationStart, -topBar.measuredHeight - translationEndDy).apply {
            setInterpolator(interpolator)
            setDuration(duration.toLong())
        }
    }
}