package progtips.vn.asia.quiz

class QuizRepository(
    private val source: QuizDataSource
) {
    suspend fun getQuestions() = source.getQuestions()
}