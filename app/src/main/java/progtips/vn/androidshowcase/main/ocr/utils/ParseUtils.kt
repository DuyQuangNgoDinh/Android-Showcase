package progtips.vn.androidshowcase.main.ocr.utils

import java.util.*

const val REGEX_NRIC = """([STFG])([\dO]{7})([A-Z1])"""

object ParseUtils {
    fun parseNRICNumber(data: String): String {
        val nricRegex = Regex(REGEX_NRIC, RegexOption.IGNORE_CASE)
        return nricRegex.find(data)?.groupValues?.let {
            String.format(
                "%s%s%s",
                it[1].toUpperCase(Locale.ROOT),
                it[2].toUpperCase(Locale.ROOT).replace("O", "0"),
                it[3].toUpperCase(Locale.ROOT).replace("1", "I")
            )
        } ?: ""
    }
}