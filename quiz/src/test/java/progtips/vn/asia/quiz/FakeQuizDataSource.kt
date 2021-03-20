package progtips.vn.asia.quiz

import kotlinx.coroutines.flow.flow

class FakeQuizDataSource: QuizDataSource {
    override suspend fun getQuestions() = flow {
        emit(listOf(
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
        ))
    }
}