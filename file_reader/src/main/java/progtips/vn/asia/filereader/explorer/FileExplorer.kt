package progtips.vn.asia.filereader.explorer

import android.content.Context
import progtips.vn.asia.filereader.reader.FileReader
import java.io.FileInputStream

interface FileExplorer {
    fun readFile(context: Context, filename: String, reader: FileReader)
}
