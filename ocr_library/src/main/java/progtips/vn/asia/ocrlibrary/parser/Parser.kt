package progtips.vn.asia.ocrlibrary.parser

import android.content.Context
import android.util.Log
import com.media2359.ocr.sgcardparser.ocr.TextRecognitionProcessor
import progtips.vn.asia.ocrlibrary.parser.model.*
import progtips.vn.asia.ocrlibrary.parser.ocr.OCRProcessorListener

abstract class Parser<T: CardEntity>(
    context: Context,
    private val cardParserListener: CardParserListener
) {
    private var totalTask = 0
    private var finishedTask = 0
    private val processor = TextRecognitionProcessor(context)

    /**
     * List of scan data for attracting information
     * Some documents need both side (front/back) so we put all images in a list
     */
    fun parse(data: List<ScanData>) {
        totalTask = data.size
        finishedTask = 0
        val cardEntity = initCardEntity()

        for (dataItem in data) {
            processor.recognizeImage(dataItem, object : OCRProcessorListener {
                override fun onSuccess(result: OCRResult) {
                    try {
                        Log.d("Parser", result.text)
                        parse(
                            cardEntity,
                            result.text,
                            result.lines,
                            result.imageSize,
                            dataItem.type
                        )
                        finishedTask += 1
                        if (finishedTask >= totalTask) cardParserListener.onSuccess(cardEntity)
                    } catch (e: Exception) {
                        cardParserListener.onError(e.message)
                    }
                }

                override fun onError(exception: Exception) {
                    cardParserListener.onError(exception.message)
                }
            })
        }
    }

    abstract fun initCardEntity(): T

    /**
     * abstract function for extracting meaning data from text
     * @param cardEntity: Card Info object to store user's data
     * @param text: raw text recognized from OCR Library
     * @param lines: list of lines of text recognized from OCR Library
     * @param imageSize: size (width and height) of image used for text recognition library
     * @param type: type of image (e.g. Front side/Back side of card)
     */
    @Throws(Exception::class)
    protected abstract fun parse(
        cardEntity: T,
        text: String,
        lines: List<OCRResultLine>,
        imageSize: ImageSize,
        type: String
    )

    /**
     * List of images required for each type of card (e.g. Front card for IC, Back card for Work Permit, Front and Back for Armed Forces)
     */
    abstract fun getRequiredImageList(): List<String>
}