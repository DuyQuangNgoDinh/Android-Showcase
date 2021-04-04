package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import android.graphics.Rect
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseTextWithinFrame
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

class MalaysiaICParser(
    context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {
    private val REQUIRED_IMAGE_FRONT = "Front"

    private val REGEX_ID_NUMBER = "[0-9]{6}-[0-9]{2}-[0-9]{4}"

    private val REGEX_DOB = "[0-9]{6}"
    private val REGEX_SEX = "LELAKI|PEREMPUAN"

    private val RECT_NAME_PERCENTAGE = Rect(0, 60, 67, 75)
    private val RECT_SEX_PERCENTAGE = Rect(63, 83, 100, 100)

    override fun initCardEntity() = CitizenData(type = CardType.MALAYSIA_IC)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        cardEntity.idNo = parseData(text, Regex(REGEX_ID_NUMBER))
        cardEntity.name = parseName(lines, imageSize)
        cardEntity.dob = parseDateOfBirth(cardEntity.idNo ?: "")
        cardEntity.gender = parseSex(lines, imageSize)
    }

    private fun parseName(lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val fullName = parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        return fullName?.replace("\n", " ")?.trim()
    }

    private fun parseSex(lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val sexString = parseTextWithinFrame(lines, RECT_SEX_PERCENTAGE, imageSize) ?: ""
        return when (parseData(sexString, Regex(REGEX_SEX))) {
            "PEREMPUAN" -> "F"
            "LELAKI" -> "M"
            else -> null
        }
    }

    private fun parseDateOfBirth(data: String): Long? {
        val dobString = parseData(data, Regex(REGEX_DOB))
        return if (dobString.isNullOrEmpty()) null
        else ParseUtils.parseDate(dobString, "yyMMdd")
    }

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_FRONT)
}
