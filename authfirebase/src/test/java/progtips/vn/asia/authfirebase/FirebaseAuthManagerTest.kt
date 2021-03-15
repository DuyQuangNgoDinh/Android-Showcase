package progtips.vn.asia.authfirebase

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@InternalCoroutinesApi
class FirebaseAuthManagerTest {
    private val coroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var authManager: FirebaseAuthManager

    @Before
    fun setup() {
        authManager = FirebaseAuthManager()
    }

    @Test
    fun testUnInitAuthManager_login_returnUnInitState() = coroutineDispatcher.runBlockingTest {
        authManager.login("", "")
        val status = authManager.authStateFlow.first()

        assertThat(status, equalTo(AuthStatus.Uninitialized))
    }
}