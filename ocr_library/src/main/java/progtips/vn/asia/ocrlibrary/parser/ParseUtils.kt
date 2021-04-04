package progtips.vn.asia.ocrlibrary.parser

import android.graphics.Rect
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ParseUtils {
    fun parseTextWithinFrame(lines: List<OCRResultLine>, rectPercentage: Rect, imageSize: ImageSize): String? {
        val rect: Rect = calculateRectOnImage(rectPercentage, imageSize)
        return parseDataWithinFrame(rect, lines)
    }

    internal fun parseDataWithinFrame(frame: Rect, lines: List<OCRResultLine>): String? {
        return if (lines.isEmpty()) null
        else {
            val data = mutableListOf<String>()
            for (line in lines) {
                if (frame.contains(line.boundingBox)) {
                    data.add(line.text)
                }
            }

            data.joinToString(" ")
                .removeMultipleSpaceAndNewLine()
        }
    }

    internal fun String.removeMultipleSpaceAndNewLine() = this.replace(Regex("\\s{2,}|\\n+"), " ").trim()

    internal fun calculateRectOnImage(percentageRect: Rect, imageSize: ImageSize): Rect {
        percentageRect.left = Math.min(100, percentageRect.left)
        percentageRect.top = Math.min(100, percentageRect.top)
        percentageRect.right = Math.min(100, percentageRect.right)
        percentageRect.bottom = Math.min(100, percentageRect.bottom)

        return Rect(
            percentageRect.left * imageSize.width / 100,
            percentageRect.top * imageSize.height / 100,
            percentageRect.right * imageSize.width / 100,
            percentageRect.bottom * imageSize.height / 100
        )
    }

    /**
     * Find string that matches regex and return string
     * @param data the string to find pattern
     * @param regex Regex object for matching
     * @param groupPos If regex has group, return string of this group, default is 0 to get all text matched by regex
     * @return null if not match
     */
    fun parseData(data: String, regex: Regex, groupPos: Int = 0): String? {
        return if (regex.containsMatchIn(data))
            regex.find(data)!!.groupValues.getOrNull(groupPos)
        else null
    }

    fun parseDate(date: String, pattern: String): Long? {
        val formatter = SimpleDateFormat(pattern, Locale.ROOT)
        return try {
            formatter.parse(date)?.time
        } catch (e: ParseException) {
            null
        }
    }
}