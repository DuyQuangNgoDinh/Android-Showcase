package progtips.vn.asia.quiz

data class Question(
    val question: String = "",
    val answers: List<Answer> = emptyList()
)

data class Answer(
    val answer: String = "",
    val correct: Boolean = false
)