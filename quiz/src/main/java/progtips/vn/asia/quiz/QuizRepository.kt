package progtips.vn.asia.quiz

class QuizRepository(
    private val source: QuizDataSource
) {
    suspend fun getQuestions() = source.getQuestions()

    fun checkAnswer(questions: List<Question>, check: (List<Answer>) -> Int): Int {
        var point = 0
        questions.forEach {
            point += check(it.answers)
        }
        return point
    }
}