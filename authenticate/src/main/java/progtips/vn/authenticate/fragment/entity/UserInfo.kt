package progtips.vn.authenticate.fragment.entity

import progtips.vn.authenticate.fragment.manager.LoginMethod

data class UserInfo(
    val email: String,
    val username: String,
    val token: String? = null,
    val loginMethod: LoginMethod? = null
)