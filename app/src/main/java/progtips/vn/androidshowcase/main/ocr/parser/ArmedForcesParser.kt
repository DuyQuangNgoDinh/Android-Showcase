package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import android.graphics.Rect
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_IMAGE_BACK
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_IMAGE_FRONT
import progtips.vn.androidshowcase.main.ocr.utils.ParseUtils.parseNRICNumber
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseTextWithinFrame
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

class ArmedForcesParser(
    context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {
    private val REGEX_NAME_RACE = "(Name)(.*)(NRIC)"

    private val REGEX_DOB = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)(\\d{2})"
    private val REGEX_RACE = "(Race)\\n([A-Z]+)"
    private val REGEX_BIRTH_COUNTRY = "(birth|of birth)\\n([A-Z ]+)"
    private val REGEX_SEX = "^[MF]$"

    private val RECT_NAME_PERCENTAGE = Rect(5, 33, 71, 61)
    private val RECT_RACE_PERCENTAGE = Rect(2, 28, 39, 37)
    private val RECT_BIRTH_COUNTRY_PERCENTAGE = Rect(35, 39, 97, 47)

    override fun initCardEntity() = CitizenData(type = CardType.ARMED_FORCES)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        when (type) {
            REQUIRED_IMAGE_FRONT -> {
                parseFrontSide(cardEntity, text, lines, imageSize)
            }
            REQUIRED_IMAGE_BACK -> {
                parseBackSide(cardEntity, text, lines, imageSize)
            }
        }
    }

    private fun parseFrontSide(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize
    ) {
        cardEntity.idNo = parseNRICNumber(text)
        cardEntity.name = parseName(text, lines, imageSize)
        cardEntity.type = CardType.ARMED_FORCES
    }

    private fun parseBackSide(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize
    ) {
        cardEntity.nationality = parseRace(text, lines, imageSize)
        cardEntity.dob = parseDateOfBirth(text)
        cardEntity.gender = parseSex(text)
        cardEntity.birthCountry = parseBirthCountry(text, lines, imageSize)
        cardEntity.type = CardType.ARMED_FORCES
    }

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        // Get name based on label
        val fullName = parseData(data, Regex(REGEX_NAME_RACE, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)), 2)

        // Get name based on position
        return if (fullName.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        } else fullName.replace("\n", " ").trim()
    }

    private fun parseRace(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val race = parseData(data, Regex(REGEX_RACE, RegexOption.IGNORE_CASE), 2)
        return if (race.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_RACE_PERCENTAGE, imageSize)
        } else race
    }

    private fun parseSex(data: String): String? {
        return parseData(data, Regex(REGEX_SEX, RegexOption.MULTILINE), 0)
    }

    private fun parseDateOfBirth(data: String): Long? {
        val dobString = parseData(data, Regex(REGEX_DOB))
        return if (dobString.isNullOrEmpty()) null
        else parseDate(dobString, "dd/MM/yyyy")
    }

    private fun parseBirthCountry(
        data: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize
    ): String? {
        val birthCountry = parseData(data, Regex(REGEX_BIRTH_COUNTRY, RegexOption.IGNORE_CASE), 2)

        // Get name based on position
        return if (birthCountry.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_BIRTH_COUNTRY_PERCENTAGE, imageSize)
        } else birthCountry.replace("\n", " ").trim()
    }

    override fun getRequiredImageList() = listOf(
        REQUIRED_IMAGE_FRONT,
        REQUIRED_IMAGE_BACK
    )
}
