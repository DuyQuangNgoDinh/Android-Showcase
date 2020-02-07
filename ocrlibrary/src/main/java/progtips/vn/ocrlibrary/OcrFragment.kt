package progtips.vn.ocrlibrary


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.fragment_ocr.*
import progtips.vn.ocrlibrary.helper.CardInfoParser
import progtips.vn.ocrlibrary.model.CardInfo
import progtips.vn.sharedresource.helper.PhotoSelectionDelegate
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class OcrFragment : Fragment() {
    private lateinit var photoSelectionDelegate: PhotoSelectionDelegate
    private val photoReadyListener = object: PhotoSelectionDelegate.EventListener {
        override fun onPhotoReady(imageUri: Uri) {
            iv_picture.setImageURI(imageUri)
            recognizeTextInImage(imageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoSelectionDelegate = PhotoSelectionDelegate(requireActivity(), this, photoReadyListener)

        btn_takePicture.setOnClickListener {
            photoSelectionDelegate.showPhotoSelectionDialog("Choose Photo")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        photoSelectionDelegate.onActivityResult(requestCode, resultCode, data)
    }

    private fun recognizeTextInImage(imageUri: Uri) {
        val onSuccessListener: (CardInfo) -> Unit = { cardInfo ->
            tv_name.setText(cardInfo.name)
            tv_dob.setText(cardInfo.dob)
            tv_number.setText(cardInfo.nricNo)
            when(cardInfo.sex) {
                "M" -> rad_male.isChecked = true
                "F" -> rad_female.isChecked = true
            }
        }

        CardInfoParser(requireActivity(), onSuccessListener).parse(imageUri)
    }

}
