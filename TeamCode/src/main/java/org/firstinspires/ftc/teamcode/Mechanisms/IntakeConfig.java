package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class IntakeConfig {
    private DcMotor intake;

    public void init(HardwareMap hwMap){
        intake = hwMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void IntakeMotorStop(){
        intake.setPower(0.0);
    }
    public void IntakeMotorMax(){
        intake.setPower(1.0);
    }

    public void OutIntake(){
        intake.setPower(-1.0);
    }
}
