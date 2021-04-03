package progtips.vn.androidshowcase.main.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import progtips.vn.androidshowcase.content.repository.AuthRepository
import progtips.vn.androidshowcase.main.auth.model.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    val authStateLiveData = authRepository.authStateFlow.asLiveData()

    fun isInProgress() = authRepository.loadingFlow.asLiveData()

    fun getError() = authRepository.errorFlow.asLiveData()

    val isLoggedIn = authStateLiveData.map {
        it is AuthState.Authenticated
    }

    val username = authStateLiveData.map {
        if (it is AuthState.Authenticated) it.account.username
        else ""
    }

    fun logout() = authRepository.logout()
}