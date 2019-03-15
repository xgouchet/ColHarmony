package fr.xgouchet.colharmony

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

data class Color(
        val argb: Int
) {

    val red: Int by lazy {
        argb shr 16 and 255
    }

    val green: Int by lazy {
        argb shr 8 and 255
    }

    val blue: Int by lazy {
        argb shr 0 and 255
    }

    val alpha: Int by lazy {
        argb shr 24 and 255
    }

    private val hsv: Triple<Int, Int, Int> by lazy {
        val r = red.toFloat() / 255f
        val g = green.toFloat() / 255f
        val b = blue.toFloat() / 255f

        val mx = max(r, max(g, b))
        val mn = min(r, min(g, b))
        val df = mx - mn

        val h = if (mx == mn) {
            0f
        } else if (mx == r) {
            (60 * ((g - b) / df) + 360) % 360
        } else if (mx == g) {
            (60 * ((b - r) / df) + 120) % 360
        } else if (mx == b) {
            (60 * ((r - g) / df) + 240) % 360
        } else {
            0f
        }

        val s = if (mx == 0f) {
            0f
        } else {
            (df / mx) * 100
        }
        val v = mx * 100

        Triple(h.toInt(), s.toInt(), v.toInt())
    }

    val hue: Int by lazy { hsv.first }
    val saturation: Int by lazy { hsv.second }
    val value: Int by lazy { hsv.third }

    companion object {

        fun argb(red: Int, green: Int, blue: Int, alpha: Int = 255): Color {
            val value = (
                    ((alpha and 255) shl 24)
                            or ((red and 255) shl 16)
                            or ((green and 255) shl 8)
                            or ((blue and 255) shl 0)
                    )
            return Color(value)
        }

        @Suppress("ComplexMethod")
        fun hsv(hue: Int, saturation: Int, value: Int, alpha: Int = 255): Color {
            val h = hue.toFloat()
            val s = saturation.toFloat() / 100f
            val v = value.toFloat() / 100f

            val h60 = h / 60.0f
            val h60f = floor(h60)
            val hi = h60f.toInt() % 6
            val f = h60 - h60f
            val p = v * (1 - s)
            val q = v * (1 - f * s)
            val t = v * (1 - (1 - f) * s)
            val (r, g, b) = if (hi == 0) {
                Triple(v, t, p)
            } else if (hi == 1) {
                Triple(q, v, p)
            } else if (hi == 2) {
                Triple(p, v, t)
            } else if (hi == 3) {
                Triple(p, q, v)
            } else if (hi == 4) {
                Triple(t, p, v)
            } else if (hi == 5) {
                Triple(v, p, q)
            } else {
                Triple(0f, 0f, 0f)
            }
            return argb((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt(), alpha)
        }
    }
}