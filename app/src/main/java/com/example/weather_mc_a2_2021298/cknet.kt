package com.example.weather_mc_a2_2021298

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

interface Cknet {
    fun observe(): Flow<Cknet.Status>
    enum class Status{
        Available,Unavailable,Losing,Lost
    }
}

