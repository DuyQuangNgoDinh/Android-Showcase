package progtips.vn.asia.quiz

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class QuizRepositoryTest {
    private lateinit var quizRepository: QuizRepository

    @Before
    fun setup() {
        quizRepository = QuizRepository(FakeQuizDataSource())
    }

    @Test
    fun testGetQuestions_success_returnThreeQuestions() = TestCoroutineDispatcher().runBlockingTest {
        val expected = listOf(
            Question(
                "Question 1",
                listOf(
                    Answer("Answer 1", true),
                    Answer("Answer 2", false),
                    Answer("Answer 3", false),
                    Answer("Answer 4", false)
                )
            ),
            Question(
                "Question 2",
                listOf(
                    Answer("Answer 1", true),
                    Answer("Answer 2", false),
                    Answer("Answer 3", false),
                    Answer("Answer 4", false)
                )
            ),
            Question(
                "Question 3",
                listOf(
                    Answer("Answer 1", true),
                    Answer("Answer 2", false),
                    Answer("Answer 3", false),
                    Answer("Answer 4", true)
                )
            )
        )

        val actual = quizRepository.getQuestions().first()

        assertThat(actual, equalTo(expected))
    }
}