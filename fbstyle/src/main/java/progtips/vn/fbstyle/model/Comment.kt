package progtips.vn.fbstyle.model

data class Comment (
    val avatar: String? = null,
    val name: String,
    val upload: Long,
    val content: String
)