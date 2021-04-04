package progtips.vn.sharedresource.helper

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

/**
 * Set error message for TextInputLayout
 */
fun TextInputLayout.setErrorMessage(@StringRes errorMessage: Int? = null) {
    if (errorMessage != null) {
        isErrorEnabled = true
        error = resources.getString(errorMessage)
    } else {
        error = null
        isErrorEnabled = false
    }
}

fun TextInputLayout.setErrorMessage(errorMessage: String? = null) {
    if (errorMessage != null) {
        isErrorEnabled = true
        error = errorMessage
    } else {
        error = null
        isErrorEnabled = false
    }
}