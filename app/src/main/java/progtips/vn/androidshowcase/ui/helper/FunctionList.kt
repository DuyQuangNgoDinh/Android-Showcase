package progtips.vn.androidshowcase.ui.helper

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.model.Function
import java.io.BufferedReader

class FunctionList {
    companion object {
        const val FUNC_AUTHENTICATION = 1
        const val FUNC_OCR = 2
        const val FUNC_FBSTYLE = 3

        fun initFunctionEntryList(resources: Resources): List<Function> {
            val inputStream = resources.openRawResource(R.raw.functions)
            val jsonProductsString = inputStream.bufferedReader().use(BufferedReader::readText)
            val productListType = object : TypeToken<ArrayList<Function>>() {}.type
            return Gson().fromJson(jsonProductsString, productListType)
        }
    }
}