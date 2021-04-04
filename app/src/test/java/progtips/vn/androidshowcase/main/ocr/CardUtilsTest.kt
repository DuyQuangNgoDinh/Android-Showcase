package progtips.vn.androidshowcase.main.ocr

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import progtips.vn.androidshowcase.main.ocr.utils.ParseUtils

class CardUtilsTest {
    @Test
    fun testParseNRICNumber_success_returnNRIC() {
        val data = "askdjfksafS9917627Zaslkfjas"
        val actual = ParseUtils.parseNRICNumber(data)

        assertThat(actual, equalTo("S9917627Z"))
    }

    @Test
    fun testParseNRICNumber_fail_returnEmptyString() {
        val data = "askdjfksaf991r627aslkfjas"
        val actual = ParseUtils.parseNRICNumber(data)

        assertThat(actual, equalTo(""))
    }
}