package progtips.vn.androidshowcase.main.file.internal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import progtips.vn.androidshowcase.R
import progtips.vn.asia.filereader.explorer.InternalFileExplorer
import progtips.vn.asia.filereader.reader.CSVFileReader
import timber.log.Timber

class InternalReaderFragment: Fragment(R.layout.fm_tmp) {
    private val fileReader = InternalFileExplorer()

    private var count = 0
    private val onReadLine : (Array<String>) -> Boolean = {
        Timber.d(it.joinToString(separator = "-"))
        count++ > 10
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fileReader.readFile(requireContext(), "hsx_internal.csv", CSVFileReader(onReadLine))
    }
}