package progtips.vn.asia.quiz

interface QuizDataSource {
    suspend fun getQuestions(): List<Question>
}