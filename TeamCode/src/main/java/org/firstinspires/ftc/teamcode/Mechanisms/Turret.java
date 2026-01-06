package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turret {
    private CRServo turret;

    public void init(HardwareMap hwMap) {
        turret = hwMap.get(CRServo.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void Left(){
        turret.setPower(-1.0);
    }
    public void Right(){
        turret.setPower(1.0);
    }
    public void Stop(){
        turret.setPower(0.0);
    }
    public void setPower(double power){turret.setPower(power);}
}
