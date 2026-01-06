package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hood {
    private Servo hood;
    private CRServo turret;

    public void init(HardwareMap hwMap){
        hood = hwMap.get(Servo.class, "hood");
        turret = hwMap.get(CRServo.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        hoodLow();
    }

    public void hoodHigh() {
        hood.setPosition(0.7);
    }
    public void hoodMed() {
        hood.setPosition(0.5);
    }
    public void hoodLow() {
        hood.setPosition(0.0);
    }
}
