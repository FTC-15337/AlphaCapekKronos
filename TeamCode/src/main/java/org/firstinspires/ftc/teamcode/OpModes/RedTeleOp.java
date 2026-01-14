package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
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
import org.firstinspires.ftc.teamcode.Mechanisms.WebcamConfig;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "RedTeleOp")

public class RedTeleOp extends LinearOpMode{
    KickStand kickStand = new KickStand();
    Shooter shooter = new Shooter();
    Hood hood = new Hood();
    KickConfig kick = new KickConfig();
    MecDrivebase drive = new MecDrivebase();
    IntakeConfig intake = new IntakeConfig();
    ElapsedTime kickTimer = new ElapsedTime();
    LimelightConfig limelight = new LimelightConfig();
    Turret turret = new Turret();
    //WebcamConfig webcam = new WebcamConfig();
    Sorter1 c1 = new Sorter1();
    Sorter2 c2 = new Sorter2();
    Sorter3 c3 = new Sorter3();

    GoBildaPinpointDriver pinpoint;

    double forward, strafe, rotate;

    public void setDriver() {
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);

        if(gamepad1.dpad_up) {
            drive.imu.resetYaw();
        }

        if (gamepad1.left_bumper) {
            limelight.alignL();
        } else if (gamepad1.right_bumper) {
            limelight.alignR();
        } else {
            turret.setPower(0.0);
        }

        if(gamepad1.y){
            kickStand.kickStandMax();
        } else {
            kickStand.kickStandStop();
        }

//        webcam.update();
//
//        AprilTagDetection id24 = webcam.getTagBySpecificId(24);
//
//        telemetry.addData("Distance is: ", webcam.getTagRange(id24));
    }

    public void setOperator() {
        if(gamepad2.left_trigger >= 0.7){
            intake.IntakeMotorMax();
        } else if (gamepad2.dpad_up){
            intake.OutIntake();
        } else {
            intake.IntakeMotorStop();
        }

        if(gamepad2.left_bumper && green == -1){
            green = 0;
        }

        if(gamepad2.right_bumper && purple == -1){
            purple = 0;
        }

        if(gamepad2.yWasPressed() && step == -1) {
            step = 0;
        }

        autoKick();
        sortGreen();
        sortPurple();
    }

    int step = -1;
    public void autoKick(){
        if(step == -1) return;
        switch(step){
            case 0:
                kick.kickOne();
                kickTimer.reset();
                step = 1;
                break;
            case 1:
                if(kickTimer.milliseconds() >= 300){
                    kick.retractOne();
                    step = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                kickTimer.reset();
                step = 3;
                break;
            case 3:
                if(kickTimer.milliseconds() >= 300){
                    kick.retractTwo();
                    step = 4;
                }
                break;
            case 4:
                kick.kickThree();
                kickTimer.reset();
                step = 5;
                break;
            case 5:
                if(kickTimer.milliseconds() >= 300){
                    kick.retractThree();
                    step = -1;
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
                if(kickTimer.milliseconds() >= 200){
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
                if(kickTimer.milliseconds() >= 200){
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
                if(kickTimer.milliseconds() >= 200){
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
                if(kickTimer.milliseconds() >= 200){
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
                if(kickTimer.milliseconds() >= 200){
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
                if(kickTimer.milliseconds() >= 200){
                    kick.retractThree();
                    green = -1;
                }
                break;
        }
    }



    @Override
    public void runOpMode() throws InterruptedException {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setOffsets(-4, -6.5, DistanceUnit.INCH);

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        pinpoint.resetPosAndIMU();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));

        intake.init(hardwareMap);
        kick.init   (hardwareMap);
        shooter.init(hardwareMap);
        turret.init(hardwareMap);
        hood.init(hardwareMap);
        kickStand.init(hardwareMap);
        drive.init(hardwareMap);
        limelight.redInit(hardwareMap);
        c1.init(hardwareMap);
        c2.init(hardwareMap);
        c3.init(hardwareMap);
        //webcam.init(hardwareMap, telemetry);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            pinpoint.update();

            Pose2D pose2D = pinpoint.getPosition();

            if (gamepad1.dpad_down) {
                pinpoint.resetPosAndIMU();
            }

            //double distance = Math.sqrt((pose2D.getX(DistanceUnit.INCH) - 1) * (pose2D.getX(DistanceUnit.INCH) - 1) + (pose2D.getY(DistanceUnit.INCH) - 59) * (pose2D.getY(DistanceUnit.INCH) - 59));

            if (gamepad1.right_trigger >= 0.7) {
                shooter.setVelocity(875.25982 * Math.pow(1.00377, 123));
                hood.setHood(0.00396959 * 123 + 0.166253);
            } else if(gamepad1.a) {
                shooter.shooterMid();
                hood.hoodMed();
            } else if(gamepad1.x) {
                shooter.shooterNear();
                hood.hoodLow();
            } else if(gamepad1.b) {
                shooter.shooterFar();
                hood.hoodHigh();
            } else {
                shooter.shooterStop();
            }

            setDriver();
            setOperator();

//            if(isStopRequested()){
//                webcam.stop();
//            }

//            telemetry.addData("Distance is (INCH)", distance);

            telemetry.addData("SHOOTER VELOCITY IS ", shooter.getVelocity());
            telemetry.addData("HOOD POS IS", hood.getHood());
            telemetry.update();
        }
    }
}
