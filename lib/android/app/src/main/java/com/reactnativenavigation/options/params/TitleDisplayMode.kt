package com.reactnativenavigation.options.params

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState

private const val MODE_ALWAYS_SHOW = "alwaysShow"
private const val MODE_SHOW_WHEN_ACTIVE = "showWhenActive"
private const val MODE_SHOW_WHEN_ACTIVE_FORCE = "showWhenActiveForce"
private const val MODE_ALWAYS_HIDE = "alwaysHide"

enum class TitleDisplayMode(private val state: TitleState?) {
    ALWAYS_SHOW(TitleState.ALWAYS_SHOW), SHOW_WHEN_ACTIVE(TitleState.SHOW_WHEN_ACTIVE), ALWAYS_HIDE(TitleState.ALWAYS_HIDE), SHOW_WHEN_ACTIVE_FORCE(TitleState.SHOW_WHEN_ACTIVE_FORCE), UNDEFINED(null);

    fun hasValue(): Boolean {
        return state != null
    }

    operator fun get(defaultValue: TitleState): TitleState = state ?: defaultValue


    fun toState(): TitleState {
        if (state == null) throw RuntimeException("TitleDisplayMode is undefined")
        return state
    }

    companion object {
        @JvmStatic
        fun fromString(mode: String?): TitleDisplayMode {
            return when (mode) {
                MODE_ALWAYS_SHOW -> ALWAYS_SHOW
                MODE_SHOW_WHEN_ACTIVE -> SHOW_WHEN_ACTIVE
                MODE_ALWAYS_HIDE -> ALWAYS_HIDE
                MODE_SHOW_WHEN_ACTIVE_FORCE -> SHOW_WHEN_ACTIVE_FORCE
                else -> UNDEFINED
            }
        }
    }

}