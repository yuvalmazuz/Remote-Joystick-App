# Remote Joystick Android App
This application connects to flightGear using IP and PORT. After the connection is established, a message will pop on your screen. Then, you may control the flight using two seek bars for throttle and rudder, and a joystick for aileron and elevator. 

**Design Pattern:**
The app code is based on the MVVM architecture. Basically, we have three layers; View, Model and ViewModel. The view is essentially everything the users sees on his screen. The view communicates with the ViewModel, and alerts him when something changes (for example: you pressed a button). The ViewModel is responsible for connecting between the View and the Model. The model is responsible for the logic and the actual algorithm. So, when a change occurs, the View alerts the ViewModel which alerts the Model. The model executes the corresponding proccess and so on. 

**Requirements:**
* FlightGear ([Download](https://www.flightgear.org/))
* Android Studio ([Download](https://developer.android.com/studio))
* Android Studio virtual device.


**Instructions:**
**First of all** ,make sure you meet the requirements listed above.
**Next**, open your FlightGear. Before you press Fly, go to Settings and add this line to the Additional Settings box:
*--telnet=socket,in,10,127.0.0.1,6400,tcp*
Now, you can press FLY.
**Finally**, run the app using Android Studio (with the emulator).
Enter your IP, and in the Port box enter **6400**. Click connect, and if it all goes well you should see *Connected!* message on your screen.
You can control the Throttle and the Rudder of the plane using the seek bars. Afterwards, use the joystick to control the Aileron and Elevator. Happy flying!

**Authors:**
* Mika Tal.
* Yuval Mazuz.

Demo Video [here](https://www.youtube.com/watch?v=ICg_m23I2Bw)
