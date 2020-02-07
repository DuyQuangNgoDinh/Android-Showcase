package progtips.vn.ocrlibrary.helper

import progtips.vn.ocrlibrary.model.CardInfo
import java.text.SimpleDateFormat

class CardInfoParser {
    companion object {
        private const val demoText = "P<NLDDE<BRUIJN<<WILLEKE<LISELOTTE<<<<<<<<<<<\n" +
                "SPECI20142NLD6503101F2403096999999990<<<<<84"
        private const val MRZ_REG = "([A-Z])([A-Z0-9<])([A-Z]{3})([A-Z<]{39})\\n([A-Z0-9<]{9})([0-9])([A-Z]{3})([0-9]{6})([0-9])([MF<])([0-9]{6})([0-9])([A-Z0-9<]{14})([0-9])([0-9])"
        private const val NRIC_REG = "([STFGstfg])(\\d{7})([A-Z|a-z])"
        private const val NAME_REG = "Name\\n[A-Z ]+"
        private const val DOB_REG = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d"
        private const val SEX_REG = "(?m)^[MF]$"


        fun parse(data: String): CardInfo? {

            val nricNo: String?
            var name: String?
            var dob: String?
            val sex: String?

            val mrzRex = MRZ_REG.toRegex().find(data.replace(" ",""))

            if (mrzRex != null) {
                val groupValues = mrzRex.destructured.toList()
                nricNo = groupValues[4].replace("<", "")
                name = groupValues[3]

                val surname = name.split("<<")[0].split("<").joinToString(" ")
                val firstnames = name.split("<<")[1].split("<").joinToString(" ")
                name = String.format("%s %s", firstnames, surname)

                dob = groupValues[7]

                val formatter = SimpleDateFormat("yyMMdd")
                val date = formatter.parse(dob)
                dob = SimpleDateFormat("dd-MM-yyyy").format(date)
                sex = groupValues[9]
            } else {
                nricNo = NRIC_REG.toRegex().find(data)?.value
                name = NAME_REG.toRegex().find(data)?.value
                dob = DOB_REG.toRegex().find(data)?.value
                sex = SEX_REG.toRegex().find(data)?.value
            }

            return CardInfo(nricNo, name, dob, sex)
        }
    }
}