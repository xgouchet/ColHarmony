package fr.xgouchet.colharmony

import kotlin.math.abs
import kotlin.math.min

@Suppress("EnumEntryName")
enum class Pattern(
        val key: String,
        private val regions: List<Region>
) {
    PATTERN_i("i", listOf(
            Region(0, Region.ANGLE_SMALL)
    )),
    PATTERN_V("V", listOf(
            Region(0, Region.ANGLE_LARGE)
    )),
    PATTERN_L("L", listOf(
            Region(0, Region.ANGLE_SMALL),
            Region(90, Region.ANGLE_LARGE)
    )),
    PATTERN_J("J", listOf(
            Region(0, Region.ANGLE_SMALL),
            Region(270, Region.ANGLE_LARGE)
    )),
    PATTERN_I("I", listOf(
            Region(0, Region.ANGLE_SMALL),
            Region(180, Region.ANGLE_SMALL)
    )),
    PATTERN_T("T", listOf(
            Region(90, Region.ANGLE_FLAT)
    )),
    PATTERN_Y("Y", listOf(
            Region(0, Region.ANGLE_LARGE),
            Region(180, Region.ANGLE_SMALL)
    )),
    PATTERN_X("X", listOf(
            Region(0, Region.ANGLE_LARGE),
            Region(180, Region.ANGLE_LARGE)
    )),
    PATTERN_O("o", emptyList());

    val maxDistance: Float by lazy {
        var max = 0f
        for (a in 0 until Angle.MAX_ANGLE) {
            val angle = Angle(a)
            val distance = getDistance(angle, 0)
            if (distance > max) {
                max = distance
            }
        }
        max
    }

    fun getDistance(patternAngle: Angle, hue: Int): Float {
        var bestDistance = Region.MAX_DISTANCE
        regions.forEach {
            val regionDistance = it.getDistance(patternAngle, hue)
            if (regionDistance < bestDistance) {
                bestDistance = regionDistance
            }
        }
        return bestDistance
    }

    fun getNearestHue(patternAngle: Angle, hue: Int, distance: Float): Int {
        var nearestHue = Angle(hue)
        var nearestDistance = Angle.MAX_ANGLE

        regions.forEach {
            val regionHue = it.getNearestHue(patternAngle, hue, distance)
            val regionDistance = angleDistance(regionHue, Angle(hue))
            if (regionDistance < nearestDistance) {
                nearestDistance = regionDistance
                nearestHue = regionHue
            }
        }
        return nearestHue.value
    }

    class Region(
            private val center: Angle,
            size: Int
    ) {

        constructor(center: Int, size: Int) : this(Angle(center), size)

        private val halfSize = size / 2

        fun getDistance(patternAngle: Angle, hue: Int): Float {
            val rotatedCenter = center + patternAngle
            val rawDistance = angleDistance(Angle(hue), rotatedCenter)
            return rawDistance.toFloat() / halfSize.toFloat()
        }

        fun getNearestHue(patternAngle: Angle, hue: Int, distance: Float): Angle {
            val distanceSize = halfSize.toFloat() * distance
            val start = center + patternAngle - Angle(distanceSize.toInt())
            val end = center + patternAngle + Angle(distanceSize.toInt())

            val distStart = angleDistance(start, Angle(hue))
            val distEnd = angleDistance(end, Angle(hue))

            return if (distStart <= distEnd) {
                start
            } else {
                end
            }
        }

        companion object {
            const val ANGLE_SMALL = 18
            const val ANGLE_LARGE = 94
            const val ANGLE_FLAT = 180

            const val MAX_DISTANCE = 20f
        }
    }
}

fun angleDistance(a: Angle, b: Angle): Int {
    val na = a.normalized().value
    val nb = b.normalized().value

    val directDistance = abs(nb - na)
    val wrappedDistanceUp = abs(na + 360 - nb)
    val wrappedDistanceDown = abs(nb + 360 - na)
    return min(directDistance, min(wrappedDistanceDown, wrappedDistanceUp))
}
