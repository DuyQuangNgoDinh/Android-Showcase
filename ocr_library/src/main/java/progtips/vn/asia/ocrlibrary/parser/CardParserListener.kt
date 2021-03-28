package progtips.vn.asia.ocrlibrary.parser

import progtips.vn.asia.ocrlibrary.parser.model.CardEntity

interface CardParserListener<in T: CardEntity> {
    fun onSuccess(entity: T)
    fun onError(errorMessage: String?)
}
