package com.example.remotejoystick.view_model

import android.widget.SeekBar
import com.example.remotejoystick.model.FGModel

class ViewModel {
//    Connects between model and view, each function calls
//    a function from model.
    private val model: FGModel = FGModel()

    fun connect(ip: String, port: Int) {
        this.model.connect(ip, port)
    }

    fun isConnected(): Boolean{
        return this.model.isConnected()
    }

    fun setThrottleBar(bar: SeekBar){
        this.model.setThrottleBar(bar)
    }

    fun setRudderBar(bar: SeekBar){
        this.model.setRudderBar(bar)
    }

    fun setAileron(data: Float){
        this.model.setAileron(data)
    }

    fun setElevator(data: Float){
        this.model.setElevator(data)
    }
}