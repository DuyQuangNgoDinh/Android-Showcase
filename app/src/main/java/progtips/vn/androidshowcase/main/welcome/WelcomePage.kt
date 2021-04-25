package progtips.vn.androidshowcase.main.welcome

data class WelcomePage(
    val title: String = "",
    val description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        return other != null && other is WelcomePage && title == other.title && description == other.title
    }
}