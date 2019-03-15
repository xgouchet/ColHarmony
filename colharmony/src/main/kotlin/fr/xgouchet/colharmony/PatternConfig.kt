package fr.xgouchet.colharmony

data class PatternConfig(
        val pattern: Pattern,
        val angle: Angle,
        val score: Float
) {
    fun getDistance(hue: Int): Float {
        return pattern.getDistance(angle, hue)
    }

    fun getNearestHue(hue: Int, distance: Float = 1f): Int {
        return pattern.getNearestHue(angle, hue, distance)
    }

}