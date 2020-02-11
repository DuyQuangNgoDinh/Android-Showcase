package progtips.vn.fbstyle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.fbstyle.R
import progtips.vn.fbstyle.model.Comment
import progtips.vn.fbstyle.ui.viewholder.CommentVH

class CommentAdapter(
    private val itemList: List<Comment>
): RecyclerView.Adapter<CommentVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH {
        return CommentVH(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item_view, parent, false)
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bind(itemList[position])
    }

}