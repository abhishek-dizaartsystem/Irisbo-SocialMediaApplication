package com.example.sociamediaapplication.data.preferences

import com.example.sociamediaapplication.data.remote.RetrofitClient
import io.socket.client.IO
import io.socket.client.Socket

object SocketManager {

    private var socket: Socket? = null

    fun connect(token: String){
        if(socket?.connected() == true)return

        val options = IO.Options()

        options.auth = mapOf("token" to token)

        socket = IO.socket(RetrofitClient.BASE_URL.removeSuffix("/"), options)
        socket?.connect()
    }

    fun getSocket(): Socket? = socket

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }
}