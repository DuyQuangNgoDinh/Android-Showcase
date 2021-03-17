package progtips.vn.asia.quiz

import kotlinx.coroutines.flow.flow

class FakeQuizDataSource: QuizDataSource {
    override suspend fun getQuestions() = flow {
        emit(listOf(
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
        ))
    }
}