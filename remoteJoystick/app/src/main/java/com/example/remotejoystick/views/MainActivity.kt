package com.example.remotejoystick.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.remotejoystick.R
import com.example.remotejoystick.view_model.ViewModel

class MainActivity : AppCompatActivity() {
    private val vm = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tb : SeekBar = findViewById(R.id.throttle_bar)
        val rb : SeekBar = findViewById(R.id.rudderBar)
        //sets seekBars functions (on progress, etc)
        this.vm.setThrottleBar(tb)
        this.vm.setRudderBar(rb)
        //sets onChange function for joystick to set Elevator/Aileron
        val joystick : Joystick = findViewById(R.id.joystick)
        joystick.onChange = {ail : Float, elev : Float ->
            vm.setElevator(-elev)
            vm.setAileron(ail)
        }
    }

    fun connectAttempt(view: View) {
        val ip = findViewById<EditText>(R.id.ip_text).text.toString()
        val portString = findViewById<EditText>(R.id.port_text).text.toString()
        if(ip.isEmpty() && portString.isEmpty()){
            Toast.makeText(applicationContext,"Please fill both fields", Toast.LENGTH_LONG).show()
            return
        }
        else if(ip.isEmpty()){
            Toast.makeText(applicationContext,"Please enter IP Address", Toast.LENGTH_LONG).show()
            return
        }
        else if(portString.isEmpty()){
            Toast.makeText(applicationContext,"Please enter Port number", Toast.LENGTH_LONG).show()
            return
        }
        try {
            val port = portString.toInt()
            vm.connect(ip, port)
            Toast.makeText(applicationContext,"Connected!", Toast.LENGTH_LONG).show()
        }
        catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext,"Connection failed, try again", Toast.LENGTH_LONG).show()
        }
    }
}