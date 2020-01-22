package progtips.vn.androidshowcase.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R

class EpisodeVH(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val tvFuncName by lazy { itemView.findViewById<TextView>(R.id.tvFuncName) }

    fun bind(item: String) {
        itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_catalogueFragment_to_loginFragment)
        }
        tvFuncName.text = item
    }

}