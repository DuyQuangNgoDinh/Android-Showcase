package progtips.vn.asia.filereader.explorer

import android.content.Context
import progtips.vn.asia.filereader.reader.FileReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class InternalFileExplorer: FileExplorer {
    override fun readFile(context: Context, filename: String, reader: FileReader) {
        reader.read(File(context.filesDir, filename))
    }

    fun writeFile(context: Context, filename: String, onWrite: (FileOutputStream) -> Unit) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            onWrite.invoke(it)
        }
    }

    fun getFileList(context: Context): List<String> {
        return context.fileList().toList()
    }
}