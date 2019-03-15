@file:JvmName("Main")

package fr.xgouchet.colharmony

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.DefaultHelpFormatter
import com.xenomachina.argparser.mainBody
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main(args: Array<String>) = mainBody("colharmony") {

    val config = ArgParser(args, ArgParser.Mode.GNU, DefaultHelpFormatter())
            .parseInto(::Config)
    val colorAnalyzer = ColorAnalyzer()
    val harmonyPainter = HarmonyPainter()


    config.log { "Loading image from ${config.input}" }
    val image = try {
        ImageIO.read(File(config.input))
    } catch (e: IOException) {
        System.err.println("Unable to read image file from: ${config.input} (${e.message})")
        return@mainBody
    }

    config.log { "Analyzing image hue histogram (step = ${config.step})" }
    colorAnalyzer.analyzeColor(image, config.step)

    if (config.allPatterns) {
        Pattern.values().forEach {
            harmonizeWithPattern(it, config, colorAnalyzer, harmonyPainter, image)
        }
    } else {
        harmonizeBestPattern(config, colorAnalyzer, harmonyPainter, image)
    }

}

private fun harmonizeBestPattern(
        config: Config,
        colorAnalyzer: ColorAnalyzer,
        harmonyPainter: HarmonyPainter,
        image: BufferedImage
) {
    config.log { "Finding best harmony" }
    val harmonyConfig = colorAnalyzer.findBestPatternConfig(config.keepHues)
    if (harmonyConfig == null) {
        System.err.println("Couldn't find a pattern matching the desired constraints")
        return
    }

    config.log { "Harmonizing using config : $harmonyConfig" }
    val output = harmonyPainter.paint(image, harmonyConfig, config.resolution)
    val outputName = "${config.input}_${harmonyConfig.pattern.key}_${config.resolution.name()}.png"
    ImageIO.write(output, "png", File(outputName))
}

private fun harmonizeWithPattern(
        pattern: Pattern,
        config: Config,
        colorAnalyzer: ColorAnalyzer,
        harmonyPainter: HarmonyPainter,
        image: BufferedImage
) {
    config.log { "Finding best harmony [${pattern.name}]" }
    val harmonyConfig = colorAnalyzer.findBestConfigForPattern(pattern, config.keepHues)
    if (harmonyConfig == null) {
        System.err.println("Couldn't find a pattern matching the desired constraints")
        return
        }

    config.log { "Harmonizing using config : $harmonyConfig" }
    val output = harmonyPainter.paint(image, harmonyConfig, config.resolution)
    val outputName = "${config.input}_${pattern.key}_${config.resolution.name()}.png"
    ImageIO.write(output, "png", File(outputName))
}