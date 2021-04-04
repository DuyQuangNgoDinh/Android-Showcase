package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import android.graphics.Rect
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_IMAGE_BACK
import progtips.vn.androidshowcase.main.ocr.utils.ParseUtils.parseNRICNumber
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseTextWithinFrame
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

class WorkPassParser(
    context: Context,
    cardParserListener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, cardParserListener) {

    private val REGEX_NAME = """(Name\n)([A-Z ]+)"""

    private val REGEX_DOB_LABEL =
        "(birth|date of birth)\\n((0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)(\\d{2}))"
    private val REGEX_DOB = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)(\\d{2})"
    private val REGEX_RACE = """(Nationality)\n([A-Z]+)"""
    private val REGEX_DOB_SEX = """^([\d-\s]*)([MF])$"""

    private val RECT_NAME_PERCENTAGE = Rect(4, 17, 97, 25)
    private val RECT_DOB_PERCENTAGE = Rect(30, 43, 72, 52)
    private val RECT_RACE_PERCENTAGE = Rect(30, 54, 72, 63)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        cardEntity.idNo = parseNRICNumber(text)
        cardEntity.name = parseName(text, lines, imageSize)
        cardEntity.nationality = parseRace(text, lines, imageSize)
        cardEntity.dob = parseDateOfBirth(text, lines, imageSize)
        cardEntity.gender = parseSex(text)
    }

    override fun initCardEntity() = CitizenData(type = CardType.WORK_PERMIT)

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_BACK)

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        // Get name based on label
        val fullName = parseData(data, Regex(REGEX_NAME), 2)

        // Get name based on position
        return if (fullName.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        } else fullName
    }

    private fun parseRace(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val race = parseData(data, Regex(REGEX_RACE, RegexOption.IGNORE_CASE), 2)

        return if (race.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_RACE_PERCENTAGE, imageSize)
        } else race
    }

    private fun parseSex(data: String): String? {
        return parseData(data, Regex(REGEX_DOB_SEX, RegexOption.MULTILINE), 2)
    }

    private fun parseDateOfBirth(
        data: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize
    ): Long? {
        var dobString = parseData(data, Regex(REGEX_DOB_LABEL, RegexOption.IGNORE_CASE), 2)
        if (dobString.isNullOrEmpty()) {
            dobString = parseTextWithinFrame(lines, RECT_DOB_PERCENTAGE, imageSize)?.let { dobText ->
                parseData(dobText, Regex(REGEX_DOB))
            }
        }
        return if (dobString.isNullOrEmpty()) return null
        else parseDate(dobString, "dd-MM-yyyy")
    }
}