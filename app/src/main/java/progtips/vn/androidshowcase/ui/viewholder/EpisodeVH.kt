package progtips.vn.androidshowcase.ui.viewholder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.model.Function
import progtips.vn.androidshowcase.ui.helper.FunctionList.Companion.FUNC_AUTHENTICATION
import progtips.vn.androidshowcase.ui.helper.FunctionList.Companion.FUNC_FBSTYLE
import progtips.vn.androidshowcase.ui.helper.FunctionList.Companion.FUNC_OCR

class EpisodeVH(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val tvFuncName by lazy { itemView.findViewById<TextView>(R.id.tvFuncName) }
    private val cvFunction by lazy { itemView.findViewById<CardView>(R.id.cv_function) }

    fun bind(item: Function) {
        itemView.setOnClickListener {
            when(item.id) {
                FUNC_AUTHENTICATION -> it.findNavController().navigate(R.id.action_catalogueFragment_to_loginFragment)
                FUNC_OCR -> it.findNavController().navigate(R.id.action_catalogueFragment_to_ocr)
                FUNC_FBSTYLE -> it.findNavController().navigate(R.id.action_catalogueFragment_to_fbstyle)
            }
        }
        tvFuncName.text = item.title
        cvFunction.setCardBackgroundColor(Color.parseColor(item.color))
    }

}