package progtips.vn.androidshowcase.utils.ui

import android.app.Dialog
import android.content.Context
import progtips.vn.androidshowcase.R

fun Context.showProgressDialog(): Dialog {
    return Dialog(this, R.style.TransparentDialog).apply {
        setContentView(R.layout.view_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}