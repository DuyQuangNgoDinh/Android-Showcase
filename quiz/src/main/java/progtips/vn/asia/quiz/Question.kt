package progtips.vn.asia.quiz

data class Question(
    val question: String = "",
    val answers: List<Answer> = emptyList()
)

data class Answer(
    val id: String = "",
    val answer: String = "",
    val selected: Boolean = false,
    val correct: Boolean = false
)