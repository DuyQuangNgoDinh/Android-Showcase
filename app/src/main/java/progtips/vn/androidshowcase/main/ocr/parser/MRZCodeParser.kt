package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.androidshowcase.main.ocr.model.REQUIRED_PASSPOST_CODE
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.Parser
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine
import java.util.*

class MRZCodeParser(
    private val context: Context,
    listener: CardParserListener<CitizenData>
) : Parser<CitizenData>(context, listener) {

    private val REGEX_MRZ =
        "([A-Z])([A-Z0-9<])([A-Z<]{3})([A-Z<]{39})\\n([A-Z0-9<]{9})([0-9])([A-Z<]{3})([0-9]{6})([0-9])([MF<])([0-9]{6})([0-9])([A-Z0-9<]{14})([0-9<])([0-9])"

    override fun parse(
        cardEntity: CitizenData,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    ) {
        val normalizedData = text.replace(" ", "").toUpperCase(Locale.ROOT)
        val matcher = Regex(REGEX_MRZ)
        if (matcher.containsMatchIn(normalizedData)) {
            val matchGroup = matcher.find(normalizedData)!!.groupValues
            cardEntity.idNo = matchGroup[5].replace("<", "")
            cardEntity.name = parseFullNameFromMRZ(matchGroup[4])
            cardEntity.dob = parseDate(matchGroup[8], "yyMMdd")
            cardEntity.gender = matchGroup[10]
        } else throw Exception(context.getString(R.string.cannot_parse_information))
    }

    private fun parseFullNameFromMRZ(data: String?): String? {
        return if (data.isNullOrEmpty()) null
        else {
            val fullName = data.split("<<")
            val surname = fullName[0].split("<").joinToString(" ")
            val firstName = fullName[1].split("<").joinToString(" ")

            "$firstName $surname".trim()
        }
    }

    override fun getRequiredImageList() = listOf(REQUIRED_PASSPOST_CODE)

    override fun initCardEntity() = CitizenData(type = CardType.PASSPORT)
}