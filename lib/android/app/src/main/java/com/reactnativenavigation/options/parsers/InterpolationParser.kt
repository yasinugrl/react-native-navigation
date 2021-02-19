package com.reactnativenavigation.options.parsers

import android.animation.TimeInterpolator
import android.view.animation.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.reactnativenavigation.options.interpolators.DecelerateAccelerateInterpolator
import com.reactnativenavigation.options.interpolators.SpringInterpolator
import org.json.JSONObject

object InterpolationParser {
    @JvmStatic
    fun parse(json: JSONObject): TimeInterpolator {
        val interpolation = json.optJSONObject("interpolation")
        return when (if (interpolation == null) "linear" else interpolation.optString("type", "linear")) {
            "decelerate" -> {
                val factor = interpolation!!.optDouble("factor", 1.0).toFloat()
                DecelerateInterpolator(factor)
            }
            "accelerateDecelerate" -> {
                AccelerateDecelerateInterpolator()
            }
            "accelerate" -> {
                val factor = interpolation!!.optDouble("factor", 1.0).toFloat()
                AccelerateInterpolator(factor)
            }
            "decelerateAccelerate" -> {
                DecelerateAccelerateInterpolator()
            }
            "fastOutSlowIn" -> {
                FastOutSlowInInterpolator()
            }
            "overshoot" -> {
                val tension = interpolation!!.optDouble("tension", 1.0)
                OvershootInterpolator(tension.toFloat())
            }
            "spring" -> {
                val mass = interpolation!!.optDouble("mass", 3.0).toFloat()
                val damping = interpolation.optDouble("damping", 500.0).toFloat()
                val stiffness = interpolation.optDouble("stiffness", 200.0).toFloat()
                val allowsOverdamping = interpolation.optBoolean("allowsOverdamping", false)
                val initialVelocity = interpolation.optDouble("initialVelocity", 0.0).toFloat()
                SpringInterpolator(mass, damping, stiffness, allowsOverdamping, initialVelocity)
            }
            "linear" -> {
                LinearInterpolator()
            }
            else -> {
                LinearInterpolator()
            }
        }
    }
}