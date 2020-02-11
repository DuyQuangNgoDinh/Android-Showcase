package progtips.vn.fbstyle.ui.viewholder

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.fbstyle.R
import progtips.vn.fbstyle.model.Comment
import progtips.vn.sharedresource.helper.loadRoundCornerCenterCropImage
import progtips.vn.sharedresource.widget.ExpandableTextView

class CommentVH(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val ivAvatar by lazy { itemView.findViewById<ImageView>(R.id.iv_avatar) }
    private val tvUsername by lazy { itemView.findViewById<TextView>(R.id.tv_username) }
    private val tvPostTime by lazy { itemView.findViewById<TextView>(R.id.tv_post_time) }
    private val tvPostContent by lazy { itemView.findViewById<ExpandableTextView>(R.id.tv_post_content) }
    private val tvReplies by lazy { itemView.findViewById<TextView>(R.id.tv_replies) }

    fun bind(item: Comment) {
        ivAvatar.loadRoundCornerCenterCropImage(item.avatar, 100)
        tvUsername.text = item.name
        tvPostTime.text = DateUtils.getRelativeTimeSpanString(item.upload)
        tvPostContent.setOriginalText(item.content)
        tvReplies.text = itemView.resources.getQuantityString(R.plurals.replies, item.replies?.size ?: 0, item.replies?.size ?: 0)
    }

}