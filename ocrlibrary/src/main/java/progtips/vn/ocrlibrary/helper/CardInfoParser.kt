package progtips.vn.ocrlibrary.helper

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.fragment_ocr.*
import progtips.vn.ocrlibrary.model.CardInfo
import java.text.SimpleDateFormat

class CardInfoParser(
    private val activity: Activity,
    private val successListener: (CardInfo) -> Unit
) {
    companion object {
        private const val demoText = "P<NLDDE<BRUIJN<<WILLEKE<LISELOTTE<<<<<<<<<<<\n" +
                "SPECI20142NLD6503101F2403096999999990<<<<<84"
        private const val MRZ_REG = "([A-Z])([A-Z0-9<])([A-Z]{3})([A-Z<]{39})\\n([A-Z0-9<]{9})([0-9])([A-Z]{3})([0-9]{6})([0-9])([MF<])([0-9]{6})([0-9])([A-Z0-9<]{14})([0-9])([0-9])"
        private const val NRIC_REG = "([STFGstfg])(\\d{7})([A-Z|a-z])"
        private const val NAME_REG = "Name\\n[A-Z ]+"
        private const val DOB_REG = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d"
        private const val SEX_REG = "(?m)^[MF]$"
    }

    fun parse(imageUri: Uri) {

        // 1. Run the text recognizer
        // 1.1. Create FirebaseVisionImage
        val image = FirebaseVisionImage.fromFilePath(activity, imageUri)

        // 1.2. Get recognizer instance
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        // 1.3. pass the image to the processImage method
        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                val info = parseInfoFromVisionText(firebaseVisionText.text)
                successListener.invoke(info)
            }
            .addOnFailureListener { e ->

            }
    }

    private fun parseInfoFromVisionText(data: String): CardInfo {
        val nricNo: String?
        val name: String?
        val dob: String?
        val sex: String?

        val mrzRex = MRZ_REG.toRegex().find(data.replace(" ",""))

        if (mrzRex != null) {
            val groupValues = mrzRex.destructured.toList()
            nricNo = groupValues[4].replace("<", "")
            name = parseFullname(groupValues[3])
            dob = formatMRZDate(groupValues[7])
            sex = groupValues[9]
        } else {
            nricNo = NRIC_REG.toRegex().find(data)?.value
            name = NAME_REG.toRegex().find(data)?.value
            dob = DOB_REG.toRegex().find(data)?.value
            sex = SEX_REG.toRegex().find(data)?.value
        }

        return CardInfo(nricNo, name, dob, sex)
    }

    private fun parseFullname(data: String): String {
        val surname = data.split("<<")[0].split("<").joinToString(" ")
        val firstname = data.split("<<")[1].split("<").joinToString(" ")
        return String.format("%s %s", firstname, surname)
    }

    /**
     * In MRZ format, the date displayed in yyMMdd, convert it to dd-MM-yyyy
     */
    private fun formatMRZDate(date: String): String {
        val formatter = SimpleDateFormat("yyMMdd")
        val date = formatter.parse(date)
        return SimpleDateFormat("dd-MM-yyyy").format(date!!)
    }
}