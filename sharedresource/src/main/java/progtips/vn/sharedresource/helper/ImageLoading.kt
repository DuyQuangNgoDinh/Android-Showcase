package progtips.vn.sharedresource.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.graphics.PorterDuff
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadCenterCropImage(url: String?, @DrawableRes placeholder: Int? = null) {
    loadCenterCropImage(if (url == null) null else Uri.parse(url), placeholder)
}

fun ImageView.loadImage(url: String?, @DrawableRes placeholder: Int? = null) {
    loadImage(if (url == null) null else Uri.parse(url), placeholder)
}

fun ImageView.loadImage(uri: Uri?, @DrawableRes placeholder: Int?) {
    Glide.with(this.context)
        .load(uri)
        .apply { if (placeholder != null) placeholder(placeholder) }
        .into(this)
}

fun ImageView.loadCenterCropImage(uri: Uri?, @DrawableRes placeholder: Int?) {
    Glide.with(this.context)
        .load(uri)
        .apply { if (placeholder != null) placeholder(placeholder) }
        .transform(CenterCrop())
        .into(this)
}

fun ImageView.loadRoundCornerCenterCropImage(uri: Uri?, radius: Int, @DrawableRes placeholder: Int?) {
    Glide.with(this.context)
        .load(uri)
        .apply { if (placeholder != null) placeholder(placeholder) }
        .transform(
            MultiTransformation<Bitmap>(
                CenterCrop(),
                RoundedCorners(radius))
        )
        .into(this)
}

fun ImageView.loadRoundCornerCenterCropImage(url: String?, radius: Int, @DrawableRes placeholder: Int? = null) {
    loadRoundCornerCenterCropImage(if (url == null) null else Uri.parse(url), radius, placeholder)
}

/**
 * Set white background to be transparent
 * Then set tint color for image
 */
fun ImageView.filterWhiteBackground(@ColorRes tint: Int) {
    val color = context.getColorCompat(tint)
    val red = Color.red(color).toFloat()
    val green = Color.green(color).toFloat()
    val blue = Color.blue(color).toFloat()

    val colorMatrix = floatArrayOf(
        0f, 0.0f, 0.0f, 0.0f, red,
        0.0f, 0f, 0.0f, 0.0f, green,
        0.0f, 0.0f, 0f, 0.0f, blue,
        -0.333f, -0.333f, -0.333f, 1f, 0f
    )

    this.colorFilter = ColorMatrixColorFilter(colorMatrix)
}

fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}
