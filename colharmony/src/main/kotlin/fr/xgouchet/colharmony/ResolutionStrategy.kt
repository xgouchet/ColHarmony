package fr.xgouchet.colharmony

import kotlin.math.max
import kotlin.math.pow

sealed class ResolutionStrategy {
    abstract fun name(): String
    abstract fun harmonizeColor(color: Color, harmonyConfig: PatternConfig): Color

    object ClampSaturationStrategy : ResolutionStrategy() {
        override fun name(): String = "clamped"

        override fun harmonizeColor(color: Color, harmonyConfig: PatternConfig): Color {
            val hue = color.hue
            val distance = harmonyConfig.getDistance(hue)
            return if (distance > 1) {
                Color.hsv(hue, 0, color.value, color.alpha)
            } else {
                color
            }
        }

    }

    object FadeSaturationStrategy : ResolutionStrategy() {
        override fun name(): String = "faded"

        override fun harmonizeColor(color: Color, harmonyConfig: PatternConfig): Color {
            val hue = color.hue
            val distance = harmonyConfig.getDistance(hue)
            return when {
                distance > 1.25f -> Color.hsv(hue, 0, color.value, color.alpha)
                distance > 0.75f -> {
                    val saturation = ((2.5f - (2f * distance)) * color.saturation).toInt()
                    Color.hsv(hue, saturation, color.value, color.alpha)
                }
                else -> color
            }
        }
    }

    object ShiftHueStrategy : ResolutionStrategy() {
        override fun name(): String = "shifted"

        override fun harmonizeColor(color: Color, harmonyConfig: PatternConfig): Color {
            val hue = color.hue
            val distance = harmonyConfig.getDistance(hue)
            return if (distance > 1) {
                val nearestHue = harmonyConfig.getNearestHue(hue)
                Color.hsv(nearestHue, color.saturation, color.value, color.alpha)
            } else {
                color
            }
        }
    }

    object ScaleHueStrategy : ResolutionStrategy() {
        override fun name(): String = "scaled"

        override fun harmonizeColor(color: Color, harmonyConfig: PatternConfig): Color {
            val hue = color.hue
            val distance = harmonyConfig.getDistance(hue)
            val maxDistance = harmonyConfig.pattern.maxDistance
            val scaledDistance = distance / maxDistance
            val wantedDistance = scaledDistance.pow(0.5f)
            val nearestHue = harmonyConfig.getNearestHue(hue, wantedDistance)
            val overScaledDistance = max(0f, (distance - 1f) / (maxDistance / 1f))
            val saturation = ((1 - overScaledDistance) * color.saturation).toInt()
            return Color.hsv(nearestHue, saturation, color.value, color.alpha)
        }
    }

}
