package progtips.vn.asia.quiz

import kotlinx.coroutines.flow.flow

class QuizRepository(
    private val source: QuizDataSource
) {
    suspend fun getQuestions() = flow {
        emit(source.getQuestions())
    }
}