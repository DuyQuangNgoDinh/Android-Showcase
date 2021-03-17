package progtips.vn.asia.authfirebase.account

data class Account(
    val userId: String = "",
    val username: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val providerId: String? = null,
    val loginMethod: LoginMethod? = null
)