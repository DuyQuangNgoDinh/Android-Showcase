package progtips.vn.asia.ocrlibrary.parser.model

import android.graphics.Rect

data class OCRResultLine(
    private val text: String,
    private val boundingBox: Rect
)