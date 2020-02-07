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
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_ocr.*
import progtips.vn.ocrlibrary.helper.CardInfoParser
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

    private val RC_PERMISSION_TAKE_PHOTO = 2
    private val RC_PERMISSION_PICK_PHOTO = 3
    private val RC_ACTION_TAKE_PHOTO = 4
    private val RC_ACTION_PICK_PHOTO = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoSelectionDelegate = PhotoSelectionDelegate(requireActivity(), this,
            PhotoSelectionDelegate.RequestCodes(
                RC_PERMISSION_TAKE_PHOTO,
                RC_PERMISSION_PICK_PHOTO,
                RC_ACTION_TAKE_PHOTO,
                RC_ACTION_PICK_PHOTO
            ), object: PhotoSelectionDelegate.EventListener {
                override fun onPhotoReady(imageUri: Uri) {
                    val bitmap = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
                        decodeBitmapFromUri(imageUri)
                    else
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)

                    iv_picture.setImageBitmap(bitmap)
                    recognizeTextInImage(bitmap)
                }
            })

        btn_takePicture.setOnClickListener {
            photoSelectionDelegate.showPhotoSelectionDialog("Choose Photo")
        }
    }

    @RequiresApi(28)
    private fun decodeBitmapFromUri(imageUri: Uri): Bitmap {
        val source = ImageDecoder.createSource(requireActivity().contentResolver, imageUri)
        return ImageDecoder.decodeBitmap(source)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        photoSelectionDelegate.onActivityResult(requestCode, resultCode, data)
    }

    private fun recognizeTextInImage(bitmap: Bitmap) {
        // 1. Run the text recognizer
        // 1.1. Create FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        // 1.2. Get recognizer instance
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        // 1.3. pass the image to the processImage method
        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                val info = CardInfoParser.parse(firebaseVisionText.text)

                tv_name.setText(info?.name)
                tv_dob.setText(info?.dob)
                tv_number.setText(info?.nricNo)
                when(info?.sex) {
                    "M" -> rad_male.isChecked = true
                    "F" -> rad_female.isChecked = true
                }
            }
            .addOnFailureListener { e ->

            }
    }

}
