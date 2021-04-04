package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import android.graphics.Rect
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_IMAGE_FRONT
import progtips.vn.androidshowcase.main.ocr.utils.ParseUtils.parseNRICNumber
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseTextWithinFrame
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

class BirthCertParser(
    context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {
    private val REGEX_NAME = "(Name\\n)([A-Z ()]+)"

    private val REGEX_DOB = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)(\\d{2})"
    private val REGEX_SEX = "^(FEMALE|MALE)$"

    private val RECT_NAME_PERCENTAGE = Rect(4, 17, 97, 25)

    override fun initCardEntity() = CitizenData(type = CardType.BIRTH_CERTIFICATE)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        cardEntity.idNo = parseNRICNumber(text)
        cardEntity.name = parseName(text, lines, imageSize)
        cardEntity.dob = parseDateOfBirth(text)
        cardEntity.gender = parseSex(text)
    }

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        // Get name based on label
        val fullName = parseData(data, Regex(REGEX_NAME), 2)

        // Get name based on position
        return if (fullName.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        } else fullName.replace("\n", " ").trim()
    }

    private fun parseSex(data: String): String? {
        return when (parseData(data, Regex(REGEX_SEX, RegexOption.MULTILINE))) {
            "FEMALE" -> "F"
            "MALE" -> "M"
            else -> null
        }
    }

    private fun parseDateOfBirth(data: String): Long? {
        val dobString = parseData(data, Regex(REGEX_DOB))
        return if (dobString.isNullOrEmpty()) null
        else parseDate(dobString, "dd/MM/yyyy")
    }

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_FRONT)
}
