package progtips.vn.asia.wifiManager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import androidx.core.app.ActivityCompat

/**
 * Use this class only on the API 28 or lower
 */
@Suppress("DEPRECATION")
internal class NetworkManagerOldVersion(
    private val context: Context
): NetworkManager() {
    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun connectToWifi(networkInfo: NetworkInfo): Boolean {
        // Enable wifi if wifi disabled
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }

        val conf = WifiConfiguration()
        conf.SSID = "\"${networkInfo.networkSSID}\""
        conf.preSharedKey = "\"${networkInfo.password}\""
        val netId = wifiManager.addNetwork(conf)

        return if (netId == -1)
            false
        else {
            wifiManager.disconnect()
            wifiManager.enableNetwork(netId, true)
            wifiManager.reconnect()
            true
        }
    }

    override fun getSavedNetworkInfo(): List<NetworkInfo> {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return emptyList()
        } else {
            wifiManager.configuredNetworks.map {
                NetworkInfo(
                    it.SSID,
                    it.preSharedKey
                )
            }
        }
    }
}