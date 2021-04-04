package progtips.vn.sharedresource.helper

import android.content.Context
import android.widget.Toast

fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}