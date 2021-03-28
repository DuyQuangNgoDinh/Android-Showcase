package progtips.vn.androidshowcase.main.ocr.parser

import android.content.Context
import androidx.annotation.NonNull
import progtips.vn.androidshowcase.main.ocr.model.CardType
import progtips.vn.androidshowcase.main.ocr.model.CitizenData
import progtips.vn.asia.ocrlibrary.parser.CardParserListener
import progtips.vn.asia.ocrlibrary.parser.Parser

object ParserManager {
    private val parserMap = mutableMapOf<CardType, Parser<CitizenData>?>()

    fun getParser(
        type: CardType,
        context: Context,
        @NonNull listener: CardParserListener<CitizenData>
    ): Parser<CitizenData>? {
        return parserMap.getOrPut(type) {
            buildParser(type, context, listener)
        }
    }

    private fun buildParser(
        type: CardType,
        context: Context,
        @NonNull listener: CardParserListener<CitizenData>
    ): Parser<CitizenData>? {
        return when (type) {
            CardType.IDENTITY_CARD -> SGIdentityCardParser(context, listener)
//            CardType.WORK_PERMIT -> WorkPassParser(context, listener)
//            CardType.PASSPORT -> MRZCodeParser(context, listener)
//            CardType.DRIVER_LICENSE -> DriverLicenseParser(context, listener)
//            CardType.STUDENT_PASS -> StudentPassParser(context, listener)
//            CardType.LONG_TERM_PASS -> LongTermPassParser(context, listener)
//            CardType.ARMED_FORCES -> ArmedForcesParser(context, listener)
//            CardType.BIRTH_CERTIFICATE -> BirthCertParser(context, listener)
//            CardType.MALAYSIA_IC -> MalaysiaICParser(context, listener)
            else -> null
        }
    }
}