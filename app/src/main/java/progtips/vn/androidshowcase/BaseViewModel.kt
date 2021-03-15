package progtips.vn.androidshowcase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import progtips.vn.androidshowcase.utils.vmevent.Event

abstract class BaseViewModel: ViewModel() {
    protected var isInProgress = MutableLiveData<Boolean>()
    fun isInProgress(): LiveData<Boolean> = isInProgress

    protected var error = MutableLiveData<Event<Throwable>>()
    fun getError(): LiveData<Event<Throwable>> = error

    protected fun launch(
        isInProgressLiveData: MutableLiveData<Boolean>? = isInProgress,
        errorLiveData: MutableLiveData<Event<Throwable>>? = error,
        task: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                isInProgressLiveData?.value = true
                task()
            } catch (throwable: Throwable) {
                errorLiveData?.value = Event(throwable)
            } finally {
                isInProgressLiveData?.value = false
            }
        }
    }
}