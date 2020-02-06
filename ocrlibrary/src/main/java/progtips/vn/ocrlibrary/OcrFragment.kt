package progtips.vn.ocrlibrary


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.android.synthetic.main.fragment_ocr.*
import progtips.vn.ocrlibrary.model.CardInfo
import progtips.vn.ocrlibrary.parser.CardInfoParser
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class OcrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_takePicture.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private val REQUEST_TAKE_PHOTO = 2

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "progtips.vn.androidshowcase.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic()
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun setPic() {
        val bmOptions = BitmapFactory.Options()
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            iv_picture.setImageBitmap(bitmap)
            recognizeTextInImage(bitmap)
        }
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
