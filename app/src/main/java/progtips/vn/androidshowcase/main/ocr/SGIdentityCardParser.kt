package progtips.vn.androidshowcase.main.ocr

import android.content.Context
import android.graphics.Rect
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_IMAGE_FRONT
import progtips.vn.androidshowcase.main.ocr.utils.ParseUtils
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseTextWithinFrame
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

class SGIdentityCardParser(
    context: Context,
    cardParserListener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, cardParserListener) {

    private val REGEX_NAME_RACE = "(Name)(.*)(Race)"
    private val REGEX_NAME = """(Name\n)([A-Z ]+)"""

    private val REGEX_DOB = """(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)(\d{2})"""
    private val REGEX_RACE = """(Race)\n([A-Z]+)"""
    private val REGEX_SEX = "(?m)^[MF]$"
    private val REGEX_BIRTH_COUNTRY = """(birth|of birth)\n([A-Z ]+)"""

    private val RECT_NAME_PERCENTAGE = Rect(21, 33, 97, 58)
    private val RECT_RACE_PERCENTAGE = Rect(21, 65, 97, 72)
    private val RECT_BIRTH_COUNTRY_PERCENTAGE = Rect(21, 87, 97, 95)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        cardEntity.idNo = ParseUtils.parseNRICNumber(text)
        cardEntity.name = parseName(text, lines, imageSize)
        cardEntity.nationality = parseRace(text, lines, imageSize)
        cardEntity.gender = parseData(text, Regex(REGEX_SEX, RegexOption.MULTILINE), 0)
        cardEntity.birthCountry = parseBirthCountry(text, lines, imageSize)
        cardEntity.dob = parseDateOfBirth(text)
    }

    override fun initCardEntity() = CitizenData(type = CardType.IDENTITY_CARD)

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_FRONT)

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val nameRaceRegex = Regex(REGEX_NAME_RACE, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
        val nameOnlyRegex = Regex(REGEX_NAME)
        var fullName: String? = null

        if (nameRaceRegex.containsMatchIn(data)) {
            fullName = nameRaceRegex.find(data)!!.groupValues[2]
        } else if (nameOnlyRegex.containsMatchIn(data)) {
            fullName = nameOnlyRegex.find(data)!!.groupValues[2]
        }

        // Get name based on position if regex not match
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

    private fun parseDateOfBirth(data: String): Long? {
        val dobString = parseData(data, Regex(REGEX_DOB)) ?: ""
        return parseDate(dobString, "dd-MM-yyyy")
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
        } else birthCountry
    }
}