package progtips.vn.asia.wifiManager

import android.content.Context
import android.os.Build

abstract class NetworkManager {
    companion object {
        fun create(context: Context): NetworkManager {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                NetworkManagerOldVersion(context)
            else NetworkManagerQ(context)
        }
    }

    /**
     * Connect to wifi network
     * @param networkInfo The information about network (SSID, Password)
     * @return true if connect successfully
     */
    protected abstract fun connectToWifi(networkInfo: NetworkInfo): Boolean

    protected abstract fun getSavedNetworkInfo(): List<NetworkInfo>
}