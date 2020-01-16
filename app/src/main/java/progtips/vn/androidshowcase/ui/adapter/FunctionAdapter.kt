package progtips.vn.androidshowcase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.ui.viewholder.EpisodeVH

class FunctionAdapter(
    private val itemList: Array<String>
): RecyclerView.Adapter<EpisodeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeVH {
        return EpisodeVH(
            LayoutInflater.from(parent.context).inflate(R.layout.function_item_view, parent, false)
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: EpisodeVH, position: Int) {
        holder.bind(itemList[position])
    }

}