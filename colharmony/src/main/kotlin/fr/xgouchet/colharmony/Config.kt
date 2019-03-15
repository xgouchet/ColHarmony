package fr.xgouchet.colharmony

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

@Suppress("UseDataClass")
class Config(parser: ArgParser) {

    val verbose by parser.flagging("-v", "--verbose",
            help = "Prints verbose logs during process")

    val input by parser.storing("-i", "--input",
            help = "Input file path")

    val step by parser.storing("-p", "--precision-step",
            help = "The analysis precision step (1 means every pixel is looked at, higher values means faster but less accurate).") { (toIntOrNull() ?: 3) }
            .default(3)

    val keepHues by parser.adding("-k", "--keep-hue",
            help = "Ensure that the given hue [0…360] is kept as is") { toInt() }

    val allPatterns by parser.flagging("-a", "--all",
            help = "Generates an harmonized image for all patterns")

    val resolution by parser.mapping(
            "--desaturate" to ResolutionStrategy.ClampSaturationStrategy,
            "--fade" to ResolutionStrategy.FadeSaturationStrategy,
            "--shift" to ResolutionStrategy.ShiftHueStrategy,
            "--scale" to ResolutionStrategy.ScaleHueStrategy,
            help = "Resolution strategy : ")
            .default(ResolutionStrategy.FadeSaturationStrategy)

    fun log(message: () -> String) {
        if (verbose) {
            print("· ")
            println(message())
        }
    }
}