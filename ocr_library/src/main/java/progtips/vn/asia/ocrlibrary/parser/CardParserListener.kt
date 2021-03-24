package progtips.vn.asia.ocrlibrary.parser

import progtips.vn.asia.ocrlibrary.parser.model.CardEntity

interface CardParserListener {
    fun onSuccess(entity: CardEntity)
    fun onError(errorMessage: String?)
}
