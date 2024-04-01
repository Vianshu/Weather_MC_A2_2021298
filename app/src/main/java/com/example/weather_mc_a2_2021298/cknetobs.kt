package com.example.weather_mc_a2_2021298

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


class cknetobs(private val context: Context):Cknet{
    private val conn_man=
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<Cknet.Status> {
        return callbackFlow {
            val callback=object: ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(Cknet.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(Cknet.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(Cknet.Status.Lost) }

                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(Cknet.Status.Unavailable) }

                }
            }
            conn_man.registerDefaultNetworkCallback(callback)
            awaitClose{
                conn_man.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}