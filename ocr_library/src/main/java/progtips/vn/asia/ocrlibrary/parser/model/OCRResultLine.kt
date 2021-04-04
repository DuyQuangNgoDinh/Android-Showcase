package progtips.vn.asia.ocrlibrary.parser.model

import android.graphics.Rect

data class OCRResultLine(
    val text: String,
    val boundingBox: Rect
)