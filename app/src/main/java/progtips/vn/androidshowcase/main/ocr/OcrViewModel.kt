package progtips.vn.androidshowcase.main.ocr

import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import progtips.vn.androidshowcase.BaseViewModel
import progtips.vn.androidshowcase.content.repository.AuthRepository
import progtips.vn.androidshowcase.main.auth.model.AuthState
import javax.inject.Inject

@HiltViewModel
class OcrViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel() {

}