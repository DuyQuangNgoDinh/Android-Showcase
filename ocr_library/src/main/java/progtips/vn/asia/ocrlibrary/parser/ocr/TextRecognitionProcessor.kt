package com.media2359.ocr.sgcardparser.ocr

import android.content.Context
import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResult
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine
import progtips.vn.asia.ocrlibrary.parser.model.ScanData
import progtips.vn.asia.ocrlibrary.parser.ocr.OCRProcessor
import progtips.vn.asia.ocrlibrary.parser.ocr.OCRProcessorListener

class TextRecognitionProcessor(private val context: Context) : OCRProcessor {
    override fun recognizeImage(scanData: ScanData, listener: OCRProcessorListener) {
        try {
            // 1.1. Create InputImage instance
            val image = InputImage.fromFilePath(context, scanData.imageUri)

            // 1.2. Get recognizer instance
            val recognizer = TextRecognition.getClient()

            // 1.3. pass the image to the processImage method
            recognizer.process(image)
                .addOnSuccessListener { visionText: Text ->
                    val ocrResult = convertResult(visionText, image.bitmapInternal)
                    listener.onSuccess(ocrResult)
                }
                .addOnFailureListener { error: Exception? ->
                    listener.onError(
                        error!!
                    )
                }
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    private fun convertResult(visionText: Text, bitmap: Bitmap): OCRResult {
        return OCRResult(
            visionText.text,
            getTextLines(visionText),
            ImageSize(bitmap.width, bitmap.height)
        )
    }

    private fun getTextLines(visionText: Text): List<OCRResultLine> {
        val lines: MutableList<OCRResultLine> = ArrayList()
        for (block in visionText.textBlocks) {
            for (line in block.lines) {
                lines.add(
                    OCRResultLine(line.text, line.boundingBox!!)
                )
            }
        }
        return lines
    }
}