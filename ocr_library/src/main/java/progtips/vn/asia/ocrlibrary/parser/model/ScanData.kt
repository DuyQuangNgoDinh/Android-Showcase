package progtips.vn.asia.ocrlibrary.parser.model

import android.net.Uri

/**
 * Data used for scanning for information
 * @property imageUri: the URI of image for scanning
 * @property type: type of image (e.g. Front side/Back side of card)
 */
data class ScanData(
    var imageUri: Uri? = null,
    val type: String
)
