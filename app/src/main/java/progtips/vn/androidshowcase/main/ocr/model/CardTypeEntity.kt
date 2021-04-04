package progtips.vn.androidshowcase.main.ocr.model

data class CardTypeEntity (
    val title: String,
    val type: CardType
) {
    override fun toString() = title
}