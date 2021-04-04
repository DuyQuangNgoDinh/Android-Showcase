package progtips.vn.androidshowcase.main.ocr.model

import progtips.vn.asia.ocrlibrary.parser.model.CardEntity

data class CitizenData(
    var idNo: String? = null,
    var name: String? = null,
    var dob: Long? = null,
    var race: String? = null,
    var nationality: String? = null,
    var gender: String? = null,
    var birthCountry: String? = null,
    var type: CardType? = null
): CardEntity()