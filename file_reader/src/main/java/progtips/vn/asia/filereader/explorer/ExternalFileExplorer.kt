package progtips.vn.asia.filereader.explorer

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import progtips.vn.asia.filereader.reader.FileReader
import java.io.File
import java.io.FileOutputStream

class ExternalFileExplorer: FileExplorer {
    override fun readFile(context: Context, filename: String, reader: FileReader) {
        reader.read(File(context.getExternalFilesDir(null), filename))
    }

    // Checks if a volume containing external storage is available for read and write.
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun writeFile(context: Context, filename: String, onWrite: (FileOutputStream) -> Unit) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            onWrite.invoke(it)
        }
    }

    // Get array of external volumes (may be internal memory or sdcard)
    private fun getExternalVolumes(context: Context): Array<out File> {
        return ContextCompat.getExternalFilesDirs(context, null)
    }

    fun getFileList(context: Context): List<String> {
        return context.fileList().toList()
    }
}