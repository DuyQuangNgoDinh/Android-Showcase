package progtips.vn.asia.ocrlibrary.parser.ocr

import progtips.vn.asia.ocrlibrary.parser.model.OCRResult

interface OCRProcessorListener {
    fun onSuccess(result: OCRResult)
    fun onError(exception: Exception)
}