package progtips.vn.asia.ocrlibrary.parser.model

data class OCRResult(
    val text: String,
    val lines: List<OCRResultLine>,
    val imageSize: ImageSize
)