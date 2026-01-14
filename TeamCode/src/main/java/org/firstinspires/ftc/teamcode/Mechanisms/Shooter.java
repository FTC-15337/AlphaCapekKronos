package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
public class Shooter {
    public DcMotorEx shooter;

    double F = 15.2071;
    double P = 300.0000;


    public void init(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }

    public void shooterMid(){
        shooter.setVelocity(1200);
    }

    public void shooterNear(){
        shooter.setVelocity(940);
    }
    public void shooterFar(){
        shooter.setVelocity(1400);
    }
    public void shooterStop(){
        shooter.setVelocity(0);
    }

    public double getVelocity() {
        return shooter.getVelocity();
    }

    public void setVelocity(double velocity){
    shooter.setVelocity(velocity);
    }
}
