package progtips.vn.asia.quiz

import kotlinx.coroutines.flow.Flow

interface QuizDataSource {
    suspend fun getQuestions(): Flow<List<Question>>
}