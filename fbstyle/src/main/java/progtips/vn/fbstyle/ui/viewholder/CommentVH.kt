package progtips.vn.fbstyle.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.fbstyle.model.Comment

class CommentVH(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
//    private val tvFuncName by lazy { itemView.findViewById<TextView>(R.id.tvFuncName) }
//    private val cvFunction by lazy { itemView.findViewById<CardView>(R.id.cv_function) }

    fun bind(item: Comment) {
        itemView.setOnClickListener {
            when(item) {

            }
        }
//        tvFuncName.text = item.title
//        cvFunction.setCardBackgroundColor(Color.parseColor(item.color))
    }

}