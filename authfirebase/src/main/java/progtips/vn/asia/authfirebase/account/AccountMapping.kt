package progtips.vn.asia.authfirebase.account

import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toAccount() = Account(
    uid,
    displayName,
    email,
    photoUrl.toString()
)