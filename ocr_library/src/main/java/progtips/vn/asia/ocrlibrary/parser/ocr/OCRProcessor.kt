package progtips.vn.asia.ocrlibrary.parser.ocr

import progtips.vn.asia.ocrlibrary.parser.model.ScanData

interface OCRProcessor {
    fun recognizeImage(scanData: ScanData, listener: OCRProcessorListener)
}