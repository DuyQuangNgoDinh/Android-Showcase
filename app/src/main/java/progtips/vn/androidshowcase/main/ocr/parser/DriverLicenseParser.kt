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

class DriverLicenseParser(
    context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {
    private val REGEX_NAME_BIRTH = "(?is)(Name:|Name)(.*)(Birth)"
    private val REGEX_NAME = "(?i)(Name:|Name)\\n([A-Z ]+)"

    private val REGEX_DOB_DDMMMYYYY_LABEL =
        "(birth|birth date:|birth date)\\s((0[1-9]|[12][0-9]|3[01])\\s(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\\s(19|20)(\\d{2}))"
    private val REGEX_DOB_DDMMMYYYY =
        "(0[1-9]|[12][0-9]|3[01])\\s(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\\s(19|20)(\\d{2})"

    private val RECT_NAME_PERCENTAGE = Rect(35, 27, 97, 52)
    private val RECT_DOB_PERCENTAGE = Rect(35, 53, 97, 62)

    override fun initCardEntity() = CitizenData(type = CardType.DRIVER_LICENSE)

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
    }

    private fun parseName(data: String, lines: List<OCRResultLine>, imageSize: ImageSize): String? {
        val nameBirth = parseData(data, Regex(REGEX_NAME_BIRTH, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)), 2)
        val nameOnly = parseData(data, Regex(REGEX_NAME_BIRTH, RegexOption.IGNORE_CASE), 2)

        val fullName = if (nameBirth.isNullOrEmpty()) nameOnly else nameBirth

        // Get name based on position
        return if (fullName.isNullOrEmpty()) {
            parseTextWithinFrame(lines, RECT_NAME_PERCENTAGE, imageSize)
        } else fullName.trim().replace("\n", " ")
    }

    private fun parseDateOfBirth(
        data: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize
    ): Long? {
        var dobString = parseData(data, Regex(REGEX_DOB_DDMMMYYYY_LABEL, RegexOption.IGNORE_CASE), 2)
        if (dobString.isNullOrEmpty()) {
            dobString = parseTextWithinFrame(lines, RECT_DOB_PERCENTAGE, imageSize)?.let { dobString ->
                parseData(dobString, Regex(REGEX_DOB_DDMMMYYYY, RegexOption.IGNORE_CASE))
            }
        }

        return if (dobString.isNullOrEmpty()) null
        else parseDate(dobString, "dd MMM yyyy")
    }

    override fun getRequiredImageList() = listOf(REQUIRED_IMAGE_FRONT)
}
