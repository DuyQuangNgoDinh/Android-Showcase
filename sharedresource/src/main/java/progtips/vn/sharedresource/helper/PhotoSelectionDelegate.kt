package progtips.vn.sharedresource.helper

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import progtips.vn.sharedresource.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_IMAGE_OPEN = 1
const val REQUEST_TAKE_PHOTO = 2

class PhotoSelectionDelegate(
    private val activity: Activity,
    private val fragment: Fragment?,
    private val eventListener: EventListener
) {

    fun showPhotoSelectionDialog(title: String) {
        val clickListener = DialogInterface.OnClickListener { _, which ->
            when(which) {
                0 -> launchCamera()
                1 -> openGallery()
            }
        }
        val options = activity.resources.getStringArray(R.array.photo_selection_options)
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setItems(options, clickListener)
            .create()
            .also { it.show() }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_IMAGE_OPEN)
    }

    private fun launchCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    capturedPhotoUri = FileProvider.getUriForFile(
                        activity,
                        "progtips.vn.androidshowcase.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedPhotoUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    lateinit var capturedPhotoPath: String
    lateinit var capturedPhotoUri: Uri

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun startActivityForResult(intent: Intent, requestCode: Int) {
        fragment?.startActivityForResult(intent, requestCode) ?: activity.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_TAKE_PHOTO -> handleCapturedPhoto()
                REQUEST_IMAGE_OPEN -> handlePickedPhoto(data)
            }
        }
    }

    private fun handlePickedPhoto(data: Intent?) {
        if (data != null){
            data.data?.let { imageUri ->
                eventListener.onPhotoReady(imageUri)
            }
        }
    }

    private fun handleCapturedPhoto() {
        eventListener.onPhotoReady(capturedPhotoUri)
    }

    interface EventListener {
        fun onPhotoReady(imageUri: Uri)
    }
}