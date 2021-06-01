package progtips.vn.androidshowcase.main.file.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class InternalReaderViewModel @Inject constructor(): ViewModel() {
    val addPriceChannel = ConflatedBroadcastChannel<Boolean>(false)
    val addVolumeChannel = ConflatedBroadcastChannel<Boolean>(false)

    val readFile = combine(
        addPriceChannel.asFlow().distinctUntilChanged(),
        addVolumeChannel.asFlow().distinctUntilChanged()
    ) { priceAdded, volumeAdded ->
        priceAdded && volumeAdded
    }.asLiveData()
}