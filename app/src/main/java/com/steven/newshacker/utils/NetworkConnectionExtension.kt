package com.steven.newshacker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.steven.newshacker.utils.Constants


fun Context.isNetWorkAvailable(): Boolean {
    val cm = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun Context.networkNotAvailableToast() {
    Toast.makeText(this, Constants.NETWORK_ERROR, Toast.LENGTH_SHORT).show()
}