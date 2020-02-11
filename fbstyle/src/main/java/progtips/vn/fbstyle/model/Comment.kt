package progtips.vn.fbstyle.model

data class Comment (
    val avatar: String? = null,
    val name: String? = null,
    val upload: Long = 0,
    val content: String?= null,
    val replies: List<Comment>? = null
)