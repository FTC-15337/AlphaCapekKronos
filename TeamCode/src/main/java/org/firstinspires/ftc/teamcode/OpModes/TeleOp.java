package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Sorter1;
import org.firstinspires.ftc.teamcode.Mechanisms.Sorter3;
import org.firstinspires.ftc.teamcode.Mechanisms.Sorter2;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.KickStand;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.Mechanisms.Turret;
import org.firstinspires.ftc.teamcode.Mechanisms.Hood;
import org.firstinspires.ftc.teamcode.Mechanisms.KickConfig;
//import org.firstinspires.ftc.teamcode.Mechanisms.WebcamConfig;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode{
    KickStand kickStand = new KickStand();
    Shooter shooter = new Shooter();
    //Turret turret = new Turret();
    Hood hood = new Hood();
    KickConfig kick = new KickConfig();
    MecDrivebase drive = new MecDrivebase();
    IntakeConfig intake = new IntakeConfig();
    ElapsedTime kickTimer = new ElapsedTime();
    LimelightConfig limelight = new LimelightConfig();
    Turret turret = new Turret();
   // WebcamConfig webcam = new WebcamConfig();
    double forward, strafe, rotate;
    Sorter1 c1 = new Sorter1();
    Sorter2 c2 = new Sorter2();
    Sorter3 c3 = new Sorter3();
    public void SetOperator(){

    }

    //boolean manualOverride = Math.abs(manualInput) > 0.1;


    int step = -1;
    public void autoKickMid(){
        if(step == -1) return;
        switch(step){
            case 0:
                hood.hoodHigh();
                //shooter.setVelocity(shooter.getVelocity() + 600);
                kick.kickOne();
                kickTimer.reset();
                step = 1;
                break;
            case 1:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractOne();
                    hood.hoodLow();
                    step = 2;
                }
                break;
            case 2:
                //shooter.setVelocity(shooter.getVelocity() + 600);
                hood.hoodMed();
                kick.kickTwo();
                kickTimer.reset();
                step = 3;
                break;
            case 3:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractTwo();
                    step = 4;
                }
                break;
            case 4:
                //shooter.setVelocity(shooter.getVelocity() + 600);
                hood.hoodLow();
                kick.kickThree();
                kickTimer.reset();
                step = 5;
                break;
            case 5:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractThree();
                    step = -1;
                }
                break;


        }
    }

    public int stepN = -1;
    public void autoKickNear(){
        if(stepN == -1) return;
        switch(stepN){
            case 0:
                hood.hoodLow();
                //shooter.setVelocity(shooter.getVelocity() + 600);
                kick.kickOne();
                kickTimer.reset();
                stepN = 1;
                break;
            case 1:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractOne();
                    stepN = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                kickTimer.reset();
                stepN = 3;
                break;
            case 3:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractTwo();
                    stepN = 4;
                }
                break;
            case 4:
                //shooter.setVelocity(shooter.getVelocity() + 600);
                kick.kickThree();
                kickTimer.reset();
                stepN = 5;
                break;
            case 5:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractThree();
                    stepN = -1;
                }
                break;


        }
    }

    int green = -1;
    int purple = -1;

    public void sortPurple(){
        if(purple == -1) return;

        switch(purple){
            case 0:
                if(c1.getDetectedColor(telemetry).equals(Sorter1.DetectedColor.PURPLE)) {
                    kick.kickOne();
                    kickTimer.reset();
                    purple = 1;
                }else{
                    purple = 2;
                }
                break;
            case 1:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractOne();
                    purple = -1;
                }
                break;
            case 2:
                if(c2.getDetectedColor(telemetry).equals(Sorter2.DetectedColor.PURPLE)) {
                    kick.kickTwo();
                    kickTimer.reset();
                    purple = 3;
                }else{
                    purple = 4;
                }
                break;
            case 3:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractTwo();
                    purple = -1;
                }
                break;
            case 4:
                if(c3.getDetectedColor(telemetry).equals(Sorter3.DetectedColor.PURPLE)) {
                    kick.kickThree();
                    kickTimer.reset();
                    purple = 5;
                }else{
                    purple = -1;
                    telemetry.addLine("ERROR");
                }
                break;
            case 5:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractThree();
                    purple = -1;
                }
                break;
        }
    }

    public void sortGreen(){
        if(green == -1) return;

        switch(green){
            case 0:
                if(c1.getDetectedColor(telemetry).equals(Sorter1.DetectedColor.GREEN)) {
                    kick.kickOne();
                    kickTimer.reset();
                    green = 1;
                }else{
                    green = 2;
                }
                break;
            case 1:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractOne();
                    green = -1;
                }
                break;
            case 2:
                if(c2.getDetectedColor(telemetry).equals(Sorter2.DetectedColor.GREEN)) {
                    kick.kickTwo();
                    kickTimer.reset();
                    green = 3;
                }else{
                    green = 4;
                }
                break;
            case 3:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractTwo();
                    green = -1;
                }
                break;
            case 4:
                if(c3.getDetectedColor(telemetry).equals(Sorter3.DetectedColor.GREEN)) {
                    kick.kickThree();
                    kickTimer.reset();
                    green = 5;
                }else{
                    green = -1;
                    telemetry.addLine("ERROR");
                }
                break;
            case 5:
                if(kickTimer.milliseconds() >= 600){
                    kick.retractThree();
                    green = -1;
                }
                break;
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {
        intake.init(hardwareMap);
        kick.init(hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        hood.init(hardwareMap);
        kickStand.init(hardwareMap);
        drive.init(hardwareMap);
        limelight.init(hardwareMap);
        c1.init(hardwareMap);
        c2.init(hardwareMap);
        c3.init(hardwareMap);
        //webcam.init(hardwareMap, telemetry);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.driveFieldRelative(forward, strafe, rotate);

            if(gamepad2.left_trigger >= 0.7){
                intake.IntakeMotorMax();
            } else if (gamepad2.dpad_up){
                intake.OutIntake();
            } else {
                intake.IntakeMotorStop();
            }
boolean x = false;boolean y = false;

            if (gamepad1.right_bumper) {
                limelight.alignTurretR(true);
            } else if (gamepad1.left_bumper) {
                limelight.alignTurretL(true);
            } else {
                limelight.alignTurretR(false);
            limelight.alignTurretL(false);
            }



            if(gamepad1.right_trigger >= 0.7){
                shooter.shooterMid();
            } else if(gamepad1.a){
                shooter.shooterNear();
            } else {
                shooter.shooterStop();
            }

//            if(gamepad2.right_stick_x > 0.0) {
//                turret.Right();
//            } else if(gamepad2.right_stick_x < 0.0){
//                turret.Left();
//            } else {
//                turret.Stop();
//            }

            if(-gamepad2.left_stick_y == 1.0) {
                hood.hoodHigh();
            } else if(-gamepad2.left_stick_y == -1.0){
                hood.hoodLow();
            } else{
                //hood.hoodMed();
            }

            if(gamepad1.y){
                kickStand.kickStandMax();
            } else {
                kickStand.kickStandStop();
            }

            if(gamepad1.dpad_up) {
                drive.imu.resetYaw();
            }

            if(gamepad2.left_bumper && green == -1){
                green = 0;
            }

            if(gamepad2.right_bumper && purple == -1){
                purple = 0;
            }

            if(gamepad2.y && step == -1 && shooter.getVelocity() >= 1200) {
                step = 0;
            }else if(gamepad2.y && stepN == -1 && shooter.getVelocity() <= 1200){
                stepN = 0;
            }

            autoKickMid();
            autoKickNear();
            sortGreen();
            sortPurple();

  //          webcam.update();
//            webcam.getId();

           // telemetry.addData("Tag ID is", webcam.getId());

            telemetry.addData("SHOOTER VELOCITY IS ", shooter.getVelocity());

            telemetry.update();
        }
    }
}
