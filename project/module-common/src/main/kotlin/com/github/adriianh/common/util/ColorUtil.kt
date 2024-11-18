package com.github.adriianh.common.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.md_5.bungee.api.ChatColor
import java.awt.Color
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.math.floor
import kotlin.math.roundToInt

/*
    @author: Esophose
    converted from: https://github.com/Rosewood-Development/RoseGarden
*/
object HexUtil {
    private const val CHARS_UNTIL_LOOP = 30
    private val RAINBOW_PATTERN =
        Pattern.compile("<(?<type>rainbow|r)(#(?<speed>\\d+))?(:(?<saturation>\\d*\\.?\\d+))?(:(?<brightness>\\d*\\.?\\d+))?(:(?<loop>l|L|loop))?>")
    private val GRADIENT_PATTERN =
        Pattern.compile("<(?<type>gradient|g)(#(?<speed>\\d+))?(?<hex>(:#([A-Fa-f\\d]{6}|[A-Fa-f\\d]{3})){2,})(:(?<loop>l|L|loop))?>")
    private val HEX_PATTERNS = listOf(
        Pattern.compile("<#([A-Fa-f\\d]){6}>"),  // <#FFFFFF>
        Pattern.compile("\\{#([A-Fa-f\\d]){6}}"),  // {#FFFFFF}
        Pattern.compile("&#([A-Fa-f\\d]){6}"),  // &#FFFFFF
        Pattern.compile("&\\{#([A-Fa-f\\d]){6}}") // &{#FFFFFF}
    )
    private val STOP = Pattern.compile(
        "<(rainbow|r)(#(\\d+))?(:(\\d*\\.?\\d+))?(:(\\d*\\.?\\d+))?(:(l|L|loop))?>|" +
                "<(gradient|g)(#(\\d+))?((:#([A-Fa-f\\d]{6}|[A-Fa-f\\d]{3})){2,})(:(l|L|loop))?>|" +
                "(&[a-f\\dr])|" +
                "<#([A-Fa-f\\d]){6}>|" +
                "\\{#([A-Fa-f\\d]){6}}|" +
                "&#([A-Fa-f\\d]){6}|" +
                "#([A-Fa-f\\d]){6}|" +
                "&\\{#([A-Fa-f\\d]){6}}|" +
                org.bukkit.ChatColor.COLOR_CHAR
    )

    private fun getCaptureGroup(matcher: Matcher, group: String): String? {
        return try {
            matcher.group(group)
        } catch (e: IllegalStateException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun colorify(message: String): String {
        var parsed = message
        parsed = parseRainbow(parsed)
        parsed = parseGradients(parsed)
        parsed = parseHex(parsed)
        parsed = parseLegacy(parsed)
        parsed = parseMiniMessage(parsed)
        return parsed
    }

    private fun parseRainbow(message: String): String {
        var parsed = message
        var matcher = RAINBOW_PATTERN.matcher(parsed)
        while (matcher.find()) {
            val parsedRainbow = StringBuilder()

            // Possible parameters and their defaults
            var speed = -1
            var saturation = 1.0f
            var brightness = 1.0f
            val looping = getCaptureGroup(matcher, "looping") != null
            val speedGroup = getCaptureGroup(matcher, "speed")
            if (speedGroup != null) {
                try {
                    speed = speedGroup.toInt()
                } catch (ignored: NumberFormatException) {
                }
            }
            val saturationGroup = getCaptureGroup(matcher, "saturation")
            if (saturationGroup != null) {
                try {
                    saturation = saturationGroup.toFloat()
                } catch (ignored: NumberFormatException) {
                }
            }
            val brightnessGroup = getCaptureGroup(matcher, "brightness")
            if (brightnessGroup != null) {
                try {
                    brightness = brightnessGroup.toFloat()
                } catch (ignored: NumberFormatException) {
                }
            }
            val stop = findStop(parsed, matcher.end())
            val content = parsed.substring(matcher.end(), stop)
            var contentLength = content.length
            val chars = content.toCharArray()
            for (i in 0 until chars.size - 1) if (chars[i] == '&' && "KkLlMmNnOoRr".indexOf(chars[i + 1]) > -1) contentLength -= 2
            val length = if (looping) contentLength.coerceAtMost(CHARS_UNTIL_LOOP) else contentLength
            val rainbow: ColorGenerator = if (speed == -1) {
                Rainbow(length, saturation, brightness)
            } else {
                AnimatedRainbow(length, saturation, brightness, speed)
            }
            var compoundedFormat = "" // Carry the format codes through the rainbow gradient
            var i = 0
            while (i < chars.size) {
                val c = chars[i]
                if (c == '&' && i + 1 < chars.size) {
                    val next = chars[i + 1]
                    val color = org.bukkit.ChatColor.getByChar(next)
                    if (color != null && color.isFormat) {
                        compoundedFormat += ChatColor.COLOR_CHAR.toString() + next
                        i++ // Skip next character
                        i++
                        continue
                    }
                }
                parsedRainbow.append(translateHex(rainbow.next())).append(compoundedFormat).append(c)
                i++
            }
            val before = parsed.substring(0, matcher.start())
            val after = parsed.substring(stop)
            parsed = before + parsedRainbow + after
            matcher = RAINBOW_PATTERN.matcher(parsed)
        }

        return parsed
    }

    private fun parseGradients(message: String): String {
        var parsed = message
        var matcher = GRADIENT_PATTERN.matcher(parsed)
        while (matcher.find()) {
            val parsedGradient = StringBuilder()
            var speed = -1
            val looping = getCaptureGroup(matcher, "loop") != null
            val hexSteps = Arrays.stream(
                getCaptureGroup(matcher, "hex")!!.substring(1).split(":").toTypedArray()
            )
                .map { x: String ->
                    if (x.length != 4) x else String.format(
                        "#%s%s%s%s%s%s",
                        x[1],
                        x[1],
                        x[2],
                        x[2],
                        x[3],
                        x[3]
                    )
                }
                .map { nm: String? -> Color.decode(nm) }
                .collect(Collectors.toList())
            val speedGroup = getCaptureGroup(matcher, "speed")
            if (speedGroup != null) {
                try {
                    speed = speedGroup.toInt()
                } catch (ignored: NumberFormatException) {
                }
            }
            val stop = findStop(parsed, matcher.end())
            val content = parsed.substring(matcher.end(), stop)
            var contentLength = content.length
            val chars = content.toCharArray()
            for (i in 0 until chars.size - 1) if (chars[i] == '&' && "KkLlMmNnOoRr".indexOf(chars[i + 1]) > -1) contentLength -= 2
            val length = if (looping) contentLength.coerceAtMost(CHARS_UNTIL_LOOP) else contentLength
            val gradient: ColorGenerator = if (speed == -1) {
                Gradient(hexSteps, length)
            } else {
                AnimatedGradient(hexSteps, length, speed)
            }
            var compoundedFormat = "" // Carry the format codes through the gradient
            var i = 0
            while (i < chars.size) {
                val c = chars[i]
                if (c == '&' && i + 1 < chars.size) {
                    val next = chars[i + 1]
                    val color = org.bukkit.ChatColor.getByChar(next)
                    if (color != null && color.isFormat) {
                        compoundedFormat += ChatColor.COLOR_CHAR.toString() + next
                        i++ // Skip next character
                        i++
                        continue
                    }
                }
                parsedGradient.append(translateHex(gradient.next())).append(compoundedFormat).append(c)
                i++
            }
            val before = parsed.substring(0, matcher.start())
            val after = parsed.substring(stop)
            parsed = before + parsedGradient + after
            matcher = GRADIENT_PATTERN.matcher(parsed)
        }

        return parsed
    }

    private fun parseHex(message: String): String {
        var parsed = message
        for (pattern: Pattern in HEX_PATTERNS) {
            var matcher = pattern.matcher(parsed)
            while (matcher.find()) {
                val color = translateHex(cleanHex(matcher.group()))
                val before = parsed.substring(0, matcher.start())
                val after = parsed.substring(matcher.end())
                parsed = before + color + after
                matcher = pattern.matcher(parsed)
            }
        }

        return parsed
    }

    private fun parseLegacy(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    private fun parseMiniMessage(message: String): String {
        val regex = "<mm>(.*?)</mm>".toRegex()
        val matches = regex.findAll(message)

        if (matches.count() == 0) return message

        val miniMessage = MiniMessage.miniMessage()
        val parsed: Component = miniMessage.deserialize(message)

        return parsed.toString()
    }

    private fun findStop(content: String, searchAfter: Int): Int {
        val matcher = STOP.matcher(content)
        while (matcher.find()) {
            if (matcher.start() > searchAfter) return matcher.start()
        }
        return content.length
    }

    private fun cleanHex(hex: String): String {
        return if (hex.startsWith("<") || hex.startsWith("{")) {
            hex.substring(1, hex.length - 1)
        } else if (hex.startsWith("&{")) {
            hex.substring(2, hex.length - 1)
        } else if (hex.startsWith("&")) {
            hex.substring(1)
        } else {
            hex
        }
    }

    private fun translateHex(hex: String): String {
        return ChatColor.of(hex).toString()
    }

    private fun translateHex(color: Color): String {
        return ChatColor.of(color).toString()
    }

    private interface ColorGenerator {
        operator fun next(): Color
    }

    open class Gradient(colors: List<Color>, steps: Int) :
        ColorGenerator {
        private val gradients: MutableList<TwoStopGradient>
        private val steps: Int
        protected var step: Long
        override fun next(): Color {
            // Gradients will use the first color if the entire spectrum won't be available to preserve prettiness
            return gradients[0].colorAt(0)
        }

        private class TwoStopGradient(
            private val startColor: Color,
            private val endColor: Color,
            private val lowerRange: Float,
            private val upperRange: Float
        ) {
            fun colorAt(step: Int): Color {
                return Color(
                    calculateHexPiece(step, startColor.red, endColor.red),
                    calculateHexPiece(step, startColor.green, endColor.green),
                    calculateHexPiece(step, startColor.blue, endColor.blue)
                )
            }

            private fun calculateHexPiece(step: Int, channelStart: Int, channelEnd: Int): Int {
                val range = upperRange - lowerRange
                val interval = (channelEnd - channelStart) / range
                return (interval * (step - lowerRange) + channelStart).let { if (it.isNaN()) 0 else it.roundToInt() }
            }
        }

        init {
            if (colors.size < 2) throw IllegalArgumentException("Must provide at least 2 colors")
            gradients = ArrayList()
            this.steps = steps - 1
            step = 0
            val increment = this.steps.toFloat() / (colors.size - 1)
            for (i in 0 until colors.size - 1) gradients.add(
                TwoStopGradient(
                    colors[i],
                    colors[i + 1], increment * i, increment * (i + 1)
                )
            )
        }
    }

    class AnimatedGradient(colors: List<Color>, steps: Int, speed: Int) :
        Gradient(colors, steps) {
        init {
            step = System.currentTimeMillis() / speed
        }
    }

    /**
     * Allows generation of a rainbow gradient with a fixed number of steps
     */
    open class Rainbow(totalColors: Int, saturation: Float, brightness: Float) :
        ColorGenerator {
        private val hueStep: Float = 1.0f / if (totalColors < 1) 1 else totalColors
        private val saturation: Float = 0f.coerceAtLeast(1f.coerceAtMost(saturation))
        private val brightness: Float = 0f.coerceAtLeast(1f.coerceAtMost(brightness))
        protected var hue: Float = 0f
        override fun next(): Color {
            val color = Color.getHSBColor(hue, saturation, brightness)
            hue += hueStep
            return color
        }
    }

    class AnimatedRainbow(totalColors: Int, saturation: Float, brightness: Float, speed: Int) :
        Rainbow(totalColors, saturation, brightness) {
        init {
            hue = ((((floor(System.currentTimeMillis() / 50.0)) / 360) * speed) % 1).toFloat()
        }
    }
}

fun String.colorify() = HexUtil.colorify(this)
fun List<String>.colorify() = this.map { it.colorify() }