package progtips.vn.androidshowcase.main.ocr.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.R
import progtips.vn.asia.ocrlibrary.parser.model.ScanData

class HomeCarouselAdapter(
    private val listener: HomeCarouselItemVH.EventListener
) : RecyclerView.Adapter<HomeCarouselItemVH>() {

    var carouselDataList = mutableListOf<ScanData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselItemVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_carousel_listing_item, parent, false)
        return HomeCarouselItemVH(view, listener)
    }

    override fun getItemCount() = carouselDataList.size

    override fun onBindViewHolder(holder: HomeCarouselItemVH, position: Int) {
        holder.bind(carouselDataList[position])
    }

    fun updateCarouselDataList(data: List<ScanData>) {
        carouselDataList.clear()
        carouselDataList.addAll(data)
        notifyDataSetChanged()
    }

    fun updateImageOnCarousel(uri: Uri, position: Int) {
        if (position in 0 until itemCount) {
            carouselDataList[position].imageUri = uri
            notifyItemChanged(position)
        }
    }
}
