package fr.xgouchet.colharmony

import java.awt.image.BufferedImage

class ColorAnalyzer {

    private val hueFineHistogram = IntArray(MAX_HUE) { 0 }

    fun analyzeColor(image: BufferedImage, step: Int) {

        val width = image.width
        val height = image.height
        val pixels = image.getRGB(0, 0, width, height, null, 0, width)

        for (y in 0 until height step step) {
            for (x in 0 until width step step) {
                val color = Color(pixels[x + y * width])
                val hue = color.hue
                val sat = color.saturation
                hueFineHistogram[hue] += sat
            }
        }
    }

    fun findBestPatternConfig(keepHues: List<Int>): PatternConfig? {
        var bestConfig: PatternConfig? = null

        for (pattern in Pattern.values()) {
            val config = findBestConfigForPattern(pattern, keepHues)
            if (config != null && (bestConfig == null || config.score < bestConfig.score)) {
                bestConfig = config
            }
        }

        return bestConfig
    }

    fun findBestConfigForPattern(pattern: Pattern, keepHues: List<Int>): PatternConfig? {
        var bestConfig: PatternConfig? = null
        for (a in 0 until MAX_HUE) {
            val angle = Angle(a)
            val score = computeFineScore(pattern, angle)
            if (bestConfig == null || score < bestConfig.score) {
                val patternConfig = PatternConfig(pattern, angle, score)
                if (keepHues.all { patternConfig.getDistance(it) <= KEEP_THRESHOLD }) {
                    bestConfig = patternConfig
                }
            }
        }
        return bestConfig
    }

    private fun computeFineScore(pattern: Pattern, angle: Angle): Float {
        var totalDistance = 0f
        for (hue in 0 until MAX_HUE) {
            val scale = hueFineHistogram[hue]
            val distance = pattern.getDistance(angle, hue)
            totalDistance += scale * distance
        }
        return totalDistance
    }

    companion object {
        private const val MAX_HUE = 360

        private const val KEEP_THRESHOLD = 0.75f
    }
}