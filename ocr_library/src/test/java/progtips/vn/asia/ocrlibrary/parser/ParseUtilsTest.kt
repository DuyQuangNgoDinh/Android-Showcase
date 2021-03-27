package progtips.vn.asia.ocrlibrary.parser

import android.graphics.Rect
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.calculateRectOnImage
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseData
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDataWithinFrame
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.parseDate
import progtips.vn.asia.ocrlibrary.parser.ParseUtils.removeMultipleSpaceAndNewLine
import progtips.vn.asia.ocrlibrary.parser.model.ImageSize
import progtips.vn.asia.ocrlibrary.parser.model.OCRResultLine

@RunWith(AndroidJUnit4::class)
class ParseUtilsTest {
    @Test
    fun testCalculateRectOnImage_success_returnRectWithCorrectPixel() {
        val imageSize = ImageSize(1024, 768)
        val percentageRect = Rect(0, 0, 50, 50)

        val actual = calculateRectOnImage(percentageRect, imageSize)

        assertThat(actual.left, equalTo(0))
        assertThat(actual.top, equalTo(0))
        assertThat(actual.right, equalTo(512))
        assertThat(actual.bottom, equalTo(384))
    }

    @Test
    fun testParseDataWithinFrame_success_returnData() {
        val percentageRect = Rect(0, 0, 512, 384)
        val dataLines = listOf(
            OCRResultLine("line 1\n", Rect(0, 0, 100, 100)),
            OCRResultLine("line 2\n", Rect(0, 0, 300, 200)),
            OCRResultLine("line 3", Rect(0, 0, 550, 100)),
            OCRResultLine("line 4", Rect(0, 0, 512, 400))
        )

        val actual = parseDataWithinFrame(percentageRect, dataLines)

        assertThat(actual, equalTo("line 1 line 2"))
    }

    @Test
    fun testRemoveMultipleSpaceAndNewLine() {
        assertThat("example content\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
        assertThat("example \ncontent\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
        assertThat("example  content\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
        assertThat("example   content\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
        assertThat("example\ncontent\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
        assertThat("example\n content\n".removeMultipleSpaceAndNewLine(), equalTo("example content"))
    }

    @Test
    fun testParseData() {
        assertThat(parseData("xxxabc123xxx", Regex("""(\w{3})(\d{3})""")), equalTo("abc123"))
        assertThat(parseData("xxxabc123xxx", Regex("""(\w{3})(\d{3})"""), 1), equalTo("abc"))
        assertThat(parseData("xxxabc123xxx", Regex("""(\w{3})(\d{3})"""), 2), equalTo("123"))
        assertThat(parseData("xxxabc123xxx", Regex("""(\w{3})(\d{3})"""), 12), equalTo(null))
    }

    @Test
    fun testParseDate() {
        assertThat(parseDate("27 Mar 2021", "dd MMM yyyy"), equalTo(1616778000000L))
        assertThat(parseDate("27 Mar 2021", "MM dd yyyy"), equalTo(null))
    }
}