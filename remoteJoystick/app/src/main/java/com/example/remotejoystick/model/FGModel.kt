package com.example.remotejoystick.model

import android.widget.SeekBar
import java.io.IOException
import java.io.OutputStream
import java.net.Socket

class FGModel {
    private lateinit var socket: Socket
    private lateinit var out: OutputStream
    private var isConnected: Boolean = false

    fun connect(ip: String, port: Int) {
        val t = Thread(Runnable {
            this.socket = Socket(ip, port)
            this.out = this.socket.getOutputStream()
            this.isConnected = true
        })
        t.start()
        t.join()
    }

    fun isConnected(): Boolean{
        return this.isConnected
    }

    fun setRudderBar(bar: SeekBar){
        val name = "rudder"
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (isConnected) {
                    val prog = progress.toFloat()
                    send(prog, name)
                }
            }
        })
    }

    fun setThrottleBar(bar: SeekBar){
        val name = "throttle"
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (isConnected) {
                    val prog = progress.toFloat()
                    send(prog, name)
                }
            }
        })
    }

    fun setAileron(data: Float){
        if(isConnected) {
            send(data, "aileron")
        }
    }

    fun setElevator(data: Float){
        if(isConnected) {
            send(data, "elevator")
        }
    }

    fun send(data: Float, name: String){
        val t = Thread(Runnable {
            var msg = ""
            //checks which attribute is being sent, using name
            if (name == "rudder") {
                //adjusts values to better scale
                val realData = (data - 500) / 500
                msg = "set /controls/flight/rudder $realData \r\n"
            }
            if (name == "throttle") {
                val realData = data / 1000
                msg = "set /controls/engines/current-engine/throttle $realData \r\n"
            }
            if (name == "aileron") {
                msg = "set /controls/flight/aileron $data \r\n"
            }
            if (name == "elevator") {
                msg = "set /controls/flight/elevator $data \r\n"
            }
            try {
                //message needs to be send in ByteArrays
                out.write(msg.toByteArray(Charsets.UTF_8))
                out.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
        t.start()
        t.join()
    }

}