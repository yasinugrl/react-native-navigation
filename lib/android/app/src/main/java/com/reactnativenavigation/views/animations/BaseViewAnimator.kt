package com.reactnativenavigation.views.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.reactnativenavigation.options.AnimationOptions
import com.reactnativenavigation.options.animations.ViewAnimationOptions
import com.reactnativenavigation.options.params.Bool
import com.reactnativenavigation.utils.ViewUtils
import com.reactnativenavigation.utils.resetViewProperties

open class BaseViewAnimator<T : View>(private val hideDirection: HideDirection, view: T? = null) {
    companion object {
        private const val DURATION = 300L
        private val fastOutSlowInInterpolator = FastOutSlowInInterpolator()
    }

    enum class HideDirection { Up, Down }
    private enum class AnimationState { Idle, AnimatingEnter, AnimatingExit }

    private lateinit var view: T

    private var showAnimator: Animator = AnimatorSet()
        set(value) {
            field = value
            field.addListener(showAnimatorListener)
            field.doOnEnd { onShowAnimationEnd() }
        }
    private var hideAnimator: Animator = AnimatorSet()
        set(value) {
            field = value
            field.addListener(hideAnimatorListener)
            field.doOnEnd { onHideAnimationEnd() }
        }

    private val showAnimatorListener = AnimatorListener(AnimationState.AnimatingEnter, View.VISIBLE)
    private val hideAnimatorListener = AnimatorListener(AnimationState.AnimatingExit, View.GONE)

    private inner class AnimatorListener(private val startState: AnimationState, private val endVisibility: Int) : AnimatorListenerAdapter() {
        var isCancelled = false

        override fun onAnimationStart(animation: Animator?) {
            view.resetViewProperties()
            view.visibility = View.VISIBLE
            animationState = startState
        }

        override fun onAnimationCancel(animation: Animator?) {
            isCancelled = true
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (!isCancelled) {
                animationState = AnimationState.Idle
                view.visibility = endVisibility
            }
        }
    }

    private var animationState = AnimationState.Idle

    private val isOrWillBeVisible: Boolean
        get() = isFullyVisible || animationState == AnimationState.AnimatingEnter
    private val isFullyVisible: Boolean
        get() = view.visibility == View.VISIBLE && animationState == AnimationState.Idle

    private val isOrWillBeHidden: Boolean
        get() = isFullyHidden || animationState == AnimationState.AnimatingExit
    private val isFullyHidden: Boolean
        get() = view.visibility == View.GONE && animationState == AnimationState.Idle

    init {
        view?.let { this.view = it }
    }

    @CallSuper
    open fun bindView(view: T) {
        this.view = view
    }

    open fun onShowAnimationEnd() = Unit

    open fun onHideAnimationEnd() = Unit

    fun isAnimatingHide() = hideAnimator.isRunning

    fun isAnimatingShow() = showAnimator.isRunning

    fun getPushAnimation(
            animation: ViewAnimationOptions,
            visible: Bool,
            translationStart: Float = 0f,
            translationEndDy: Float = 0f
    ): Animator {
        if (isOrWillBeVisible && visible.isFalse) {
            showAnimator.cancel()
            hideAnimator = animation.exit.getAnimation(view, getDefaultHideAnimator(translationStart, translationEndDy))
            return hideAnimator
        }

        if (isOrWillBeHidden && visible.isTrueOrUndefined) {
            hideAnimator.cancel()
            showAnimator = animation.enter.getAnimation(view, getDefaultShowAnimator(translationStart))
            return showAnimator
        }

        return AnimatorSet()
    }

    fun getPopAnimation(
            animation: ViewAnimationOptions,
            visible: Bool,
            translationStart: Float = 0f,
            translationEndDy: Float = 0f
    ): Animator {
        if (isOrWillBeVisible && visible.isFalse) {
            showAnimator.cancel()
            hideAnimator = animation.exit.getAnimation(view, getDefaultHideAnimator(translationStart, translationEndDy))
            return hideAnimator
        }

        if (isOrWillBeHidden && visible.isTrueOrUndefined) {
            hideAnimator.cancel()
            showAnimator = animation.enter.getAnimation(view, getDefaultShowAnimator(translationStart))
            return showAnimator
        }

        return AnimatorSet()
    }

    fun show(options: AnimationOptions, translationStartDy: Int) {
        if (isOrWillBeVisible) return
        showAnimator = if (options.hasValue()) {
            options.setValueDy(
                    View.TRANSLATION_Y,
                    -translationStartDy.toFloat(),
                    0f
            )
            options.getAnimation(view)
        } else {
            getDefaultShowAnimator(translationStartDy.toFloat())
        }
        hideAnimator.cancel()
        showAnimator.start()
    }

    fun show(startTranslation: Float) {
        showAnimator = getDefaultShowAnimator(startTranslation)
        hideAnimator.cancel()
        showAnimator.start()
    }

    open fun hide(
            options: AnimationOptions,
            translationStartDy: Float = 0f,
            translationEndDy: Float = 0f,
            onAnimationEnd: Runnable? = null
    ) {
        if (isOrWillBeHidden) return
        hideAnimator = if (options.hasValue()) {
            options.setValueDy(View.TRANSLATION_Y, translationStartDy, -translationEndDy)
            options.getAnimation(view)
        } else {
            getDefaultHideAnimator(translationStartDy, translationEndDy)
        }
        hideInternal(onAnimationEnd = onAnimationEnd)
    }

    private fun hideInternal(onAnimationEnd: Runnable?) {
        showAnimator.cancel()
        hideAnimator.apply {
            doOnEnd { onAnimationEnd?.run() }
            start()
        }
    }

    private fun getDefaultShowAnimator(translationStart: Float): Animator {
        val direction = if (hideDirection == HideDirection.Up) 1 else -1
        return ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                direction * (-ViewUtils.getHeight(view) - translationStart),
                0f
        ).apply {
            interpolator = fastOutSlowInInterpolator
            duration = DURATION
        }
    }

    private fun getDefaultHideAnimator(translationStart: Float, translationEndDy: Float): Animator {
        val direction = if (hideDirection == HideDirection.Up) 1 else -1
        android.R.integer.config_mediumAnimTime
        return ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                translationStart,
                direction * (-view.measuredHeight - translationEndDy)
        ).apply {
            interpolator = fastOutSlowInInterpolator
            duration = DURATION
        }
    }
}