package progtips.vn.asia.filereader.reader

import com.opencsv.CSVReader
import java.io.File

class CSVFileReader(
    private val onReadLine : (Array<String>) -> Boolean
): FileReader {
    override fun read(file: File) {
        val reader = CSVReader(java.io.FileReader(file))
        var nextLine: Array<String>
        var stop = false
        while (reader.readNext().also { nextLine = it } != null && !stop) {
            stop = onReadLine.invoke(nextLine)
        }
    }
}