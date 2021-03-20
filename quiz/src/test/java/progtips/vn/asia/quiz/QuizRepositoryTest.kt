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
                    Answer("1", "Answer 1", true),
                    Answer("2", "Answer 2", false),
                    Answer("3", "Answer 3", false),
                    Answer("4", "Answer 4", false)
                )
            ),
            Question(
                "Question 2",
                listOf(
                    Answer("1", "Answer 1", true),
                    Answer("2", "Answer 2", false),
                    Answer("3", "Answer 3", false),
                    Answer("4", "Answer 4", false)
                )
            ),
            Question(
                "Question 3",
                listOf(
                    Answer("1", "Answer 1", true),
                    Answer("2", "Answer 2", false),
                    Answer("3", "Answer 3", false),
                    Answer("4", "Answer 4", true)
                )
            )
        )

        val actual = quizRepository.getQuestions().first()

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun testCheckAnswer_success_returnThreePoint() {
        val questions = listOf(
            Question(
                "Question 1",
                listOf(
                    Answer("1", "Answer 1", true, true),
                    Answer("2", "Answer 2", false, false),
                    Answer("3", "Answer 3", false, false),
                    Answer("4", "Answer 4", false, false)
                )
            ),
            Question(
                "Question 2",
                listOf(
                    Answer("1", "Answer 1", false, true),
                    Answer("2", "Answer 2", false, false),
                    Answer("3", "Answer 3", true, false),
                    Answer("4", "Answer 4", false, false)
                )
            ),
            Question(
                "Question 3",
                listOf(
                    Answer("1", "Answer 1", true, true),
                    Answer("2", "Answer 2", false, false),
                    Answer("3", "Answer 3", false, false),
                    Answer("4", "Answer 4", true, true)
                )
            )
        )

        val actual = quizRepository.checkAnswer(questions) { answers ->
            answers.filter { it.selected and it.correct }.size
        }

        assertThat(actual, equalTo(3))
    }
}