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

class StudentPassParser(
    context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {
    private val REGEX_NAME_NRIC = "(POLYTECHNIC)(.*)([STFG][\\dO]{7}[A-Z])"

    private val REGEX_DOB_LABEL =
        "(birth|date of birth|of birth)\\s((0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)(\\d{2}))"
    private val REGEX_DOB = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)(\\d{2})"
    private val REGEX_SEX = "(sex) ([MF])"

    private val RECT_NAME_PERCENTAGE = Rect(2, 19, 64, 30)
    private val RECT_DOB_PERCENTAGE = Rect(2, 34, 64, 45)

    override fun initCardEntity() = CitizenData(type = CardType.STUDENT_PASS)

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_FRONT)

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        cardEntity.idNo = parseNRICNumber(text)
        cardEntity.name = parseName(text, lines, imageSize)
        cardEntity.dob = parseDateOfBirth(text, lines, imageSize)
        cardEntity.gender = parseData(text, Regex(REGEX_SEX, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)), 2)
    }

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        // Get name based on label
        val fullName = parseData(data, Regex(REGEX_NAME_NRIC, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)), 2)

        // Get name based on position
        return if (fullName.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        } else fullName.replace("\n", " ").trim()
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
        return if (dobString.isNullOrEmpty()) null
        else parseDate(dobString, "dd/MM/yyyy")
    }
}
