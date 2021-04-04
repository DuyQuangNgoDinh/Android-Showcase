package progtips.vn.androidshowcase.main.ocr.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R
import progtips.vn.asia.ocrlibrary.parser.model.ScanData

class HomeCarouselItemVH(
    containerView: View,
    listener: EventListener
): RecyclerView.ViewHolder(containerView) {

    private val ivPicture = itemView.findViewById<ImageView>(R.id.iv_picture)
    private val tvPlaceholder = itemView.findViewById<TextView>(R.id.tv_picture_description)

    init {
        itemView.setOnClickListener {
            listener.onClickCarouselItem(layoutPosition)
        }
    }

    fun bind(data: ScanData) {
        tvPlaceholder.text = data.type

        if (data.imageUri != null) {
            ivPicture.setImageURI(data.imageUri)
            tvPlaceholder.visibility = View.GONE
        } else {
            ivPicture.setImageResource(R.drawable.default_image)
            tvPlaceholder.visibility = View.VISIBLE
        }
    }

    interface EventListener {
        fun onClickCarouselItem(position: Int)
    }
}
