package progtips.vn.asia.wifiManager

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Use this class on from API 29 or higher
 */
@RequiresApi(Build.VERSION_CODES.Q)
internal class NetworkManagerQ(
    private val context: Context
): NetworkManager() {
    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun connectToWifi(networkInfo: NetworkInfo): Boolean {
        return true
    }

    override fun getSavedNetworkInfo(): List<NetworkInfo> {
        return emptyList()
    }
}