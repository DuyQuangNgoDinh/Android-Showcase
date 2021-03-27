package progtips.vn.asia.ocrlibrary.parser

import android.graphics.Rect
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

object ParseUtils {
    fun parseTextWithinFrame(lines: List<OCRResultLine>, rectPercentage: Rect, imageSize: ImageSize): String {
        val rect: Rect = calculateRectOnImage(rectPercentage, imageSize)
        return parseDataWithinFrame(rect, lines)
    }

    internal fun parseDataWithinFrame(frame: Rect, lines: List<OCRResultLine>): String {
        val data = mutableListOf<String>()
        for (line in lines) {
            if (frame.contains(line.boundingBox)) {
                data.add(line.text)
            }
        }
        return data.joinToString(" ")
            .removeMultipleSpaceAndNewLine()
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
}