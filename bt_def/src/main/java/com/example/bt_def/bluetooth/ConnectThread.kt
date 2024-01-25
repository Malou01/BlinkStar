package com.example.bt_def.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

class ConnectThread(device: BluetoothDevice, val listener: BluetoothController.Listener) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket? = null
    init {
        try {
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (e: IOException){

        } catch (se: SecurityException){

        }
    }

    override fun run() {
        try {
            mSocket?.connect()
            listener.onReceive(BluetoothController.BLUETOOTH_CONNECTED)
            BluetoothController.BLUETOOTH_STATE = true
            readMessage();
        } catch (e: IOException){
            listener.onReceive(BluetoothController.BLUETOOTH_NO_CONNECTED)
            BluetoothController.BLUETOOTH_STATE = false;
        } catch (se: SecurityException){

        }
    }

    private fun readMessage() {
        val buffer = StringBuilder()
        while (true) {
            try {
                buffer.append(mSocket?.inputStream?.read()?.toChar())
                if (buffer.indexOf("\r\n") > 0) {
                    val message = buffer.toString()
                    listener.onReceive(message)
                    buffer.delete(0, buffer.length)
                }
            } catch (e: IOException) {
                if (BluetoothController.BLUETOOTH_STATE) {
                    listener.onReceive(BluetoothController.BLUETOOTH_NO_CONNECTED)
                    BluetoothController.BLUETOOTH_STATE = false
                }
                break
            }
        }
    }

    fun sendMessage(message: String?){
        try {
            mSocket?.outputStream?.write(message!!.toByteArray())
        } catch (e: IOException){

        }
    }


    fun closeConnection(){
        try {
            mSocket?.close()
        } catch (e: IOException){

        }
    }
}

