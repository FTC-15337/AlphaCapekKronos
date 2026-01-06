package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class KickStand {
    private DcMotor kickStand;

    public void init(HardwareMap hwMap) {
        kickStand = hwMap.get(DcMotor.class, "stand");
        kickStand.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void kickStandMax() {
        kickStand.setPower(0.5);
    }
    public void kickStandStop() {
        kickStand.setPower(0.0);
    }
}
