package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class KickConfig {
    private Servo one;
    private Servo two;
    private Servo three;

    public void init(HardwareMap hwMap){
        one = hwMap.get(Servo.class, "kickOne");
        two = hwMap.get(Servo.class, "kickTwo");
        three = hwMap.get(Servo.class, "kickThree");
    }
    public void kickOne(){
        one.setPosition(0.35);
    }
    public void retractOne(){
        one.setPosition(0.0);
    }
    public void kickTwo(){
        two.setPosition(0.0);
    }
    public void retractTwo(){
        two.setPosition(0.35);
    }
    public void kickThree(){
        three.setPosition(0.65);
    }
    public void retractThree(){
        three.setPosition(1.0);
    }
}
