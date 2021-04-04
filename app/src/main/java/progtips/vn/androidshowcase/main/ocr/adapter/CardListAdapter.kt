package progtips.vn.androidshowcase.main.ocr.adapter

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import progtips.vn.androidshowcase.main.ocr.model.CardTypeEntity

class CardListAdapter(
    @NonNull context: Context,
    @LayoutRes textViewResId: Int,
    val data: List<CardTypeEntity>
): ArrayAdapter<CardTypeEntity>(context, textViewResId, data) {

}