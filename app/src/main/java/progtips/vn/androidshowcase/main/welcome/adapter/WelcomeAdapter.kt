package progtips.vn.androidshowcase.main.welcome.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import progtips.vn.androidshowcase.main.welcome.WelcomePage
import progtips.vn.androidshowcase.main.welcome.adapter.WelcomeAdapter.WelcomeVH
import progtips.vn.androidshowcase.main.welcome.ui.WelcomeItemUI

class WelcomeAdapter(private val listener: OnClickWelcomeListener): ListAdapter<WelcomePage, WelcomeVH>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<WelcomePage>() {
            override fun areItemsTheSame(oldItem: WelcomePage, newItem: WelcomePage): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WelcomePage, newItem: WelcomePage): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeVH {
        return WelcomeVH(
            ComposeView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        )
    }

    override fun onBindViewHolder(holder: WelcomeVH, position: Int) {
        holder.bind(getItem(position), position, itemCount)
    }

    inner class WelcomeVH(view: View): RecyclerView.ViewHolder(view) {

        fun bind(item: WelcomePage, position: Int, itemCount: Int) {
            (itemView as? ComposeView)?.apply {
                setContent {
                    WelcomeItemUI(item, position, itemCount) {
                        listener.onClickSkip()
                    }
                }
            }
        }
    }

    interface OnClickWelcomeListener {
        fun onClickSkip()
    }
}
