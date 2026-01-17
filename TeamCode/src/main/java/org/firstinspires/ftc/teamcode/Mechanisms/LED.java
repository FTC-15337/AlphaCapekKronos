package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LED {
    Servo LED;

    public void init(HardwareMap hwMap){
        LED = hwMap.get(Servo.class , "led");
    }

    public void setLED(){
        LED.setPosition(0.5);
    }

    public void off() {
        LED.setPosition(0.0);
    }
}
