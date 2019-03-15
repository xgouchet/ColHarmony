package fr.xgouchet.colharmony

import java.awt.image.BufferedImage
import java.awt.image.RenderedImage

class HarmonyPainter {

    fun paint(
            image: BufferedImage,
            harmonyConfig: PatternConfig,
            resolution: ResolutionStrategy
    ): RenderedImage {

        val width = image.width
        val height = image.height
        val pixels = image.getRGB(0, 0, width, height, null, 0, width)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val color = Color(pixels[x + y * width])
                val harmonized = resolution.harmonizeColor(color, harmonyConfig)
                pixels[x + y * width] = harmonized.argb
            }
        }


        return BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).apply {
            setRGB(0, 0, width, height, pixels, 0, width)
        }
    }



    private fun shiftHue(distance: Float, hue: Int, color: Color): Color {
        return if (distance > 1) {

            Color.hsv(hue, 0, color.value, color.alpha)
        } else {
            color
        }
    }

}