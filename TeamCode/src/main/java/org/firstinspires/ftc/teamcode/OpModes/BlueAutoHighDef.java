package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.Mechanisms.Hood;
import org.firstinspires.ftc.teamcode.Mechanisms.KickConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Blue Auto")
public class BlueAutoHighDef extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    private Timer kickTimer;

    private KickConfig kick = new KickConfig();
    private Hood hood = new Hood();
    private Shooter shooter = new Shooter();
    private LimelightConfig limelightConfig = new LimelightConfig();
    private MecDrivebase drive = new MecDrivebase();

    public enum PathState {
        // START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT TO SCORE THE ARTIFACT
        DRIVE_START_POS_SHOOT_POS,
        SHOOT_PRELOAD,
        INTAKE_ONE,
        STOP
    }

    PathState pathState;

    private final Pose startPose = new Pose(23.024390243902435, 120.1951219512195, Math.toRadians(143));
    private final Pose shootPos = new Pose(58.146341463414636, 84.97560975609755, Math.toRadians(143));
    private final Pose intakePos = new Pose(15.804878048780488, 84.97560975609755, Math.toRadians(180));

    private PathChain driveStartPosShootPos, driveShootPosIntakePos;

    public void buildPaths() {
        // put in coordinates for starting pose > ending pose
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPos))
                .setConstantHeadingInterpolation(Math.toRadians(143))
                .build();

//        driveShootPosIntakePos = follower.pathBuilder()
//                .addPath(new BezierLine(shootPos, intakePos))
//                .setConstantHeadingInterpolation(Math.toRadians(180))
//                .build();
    }


    public int stepN = -1;
    public void autoKickMid(){
        if(stepN == -1) return;
        switch(stepN){
            case 0:
                hood.hoodLow();
                kick.kickOne();
                stepN = 1;
                break;
            case 1:
                if(kickTimer.getElapsedTimeSeconds() > 2.6){
                    kick.retractOne();
                    stepN = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                stepN = 3;
                break;
            case 3:
                if(kickTimer.getElapsedTimeSeconds() > 2.85){
                    kick.retractTwo();
                    stepN = 4;
                }
                break;
            case 4:
                kick.kickThree();
                stepN = 5;
                break;
            case 5:
                if(kickTimer.getElapsedTimeSeconds() > 3.1){
                    kick.retractThree();
                    stepN = -1;
                }
                break;


        }
    }

    public void statePathUpdate() {
        switch(pathState) {
            case DRIVE_START_POS_SHOOT_POS:
                follower.setMaxPower(0.5);
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT_PRELOAD); // reset timer & transition to new state
                break;
//            case SHOOT_PRELOAD:
//                // check is follower done its path
//                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.0) {
//                    stepN = 0;
//                    telemetry.addLine("Firing");
//                    setPathState(PathState.INTAKE_ONE);
//                }
//                break;
//            case INTAKE_ONE:
//                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1.0) {
//                    follower.followPath(driveShootPosIntakePos, true);
//                    setPathState(PathState.STOP);
//                }
//                break;
//            case STOP:
//                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1.0) {
//                    drive.frontLeft.setPower(0.0);
//                    drive.frontRight.setPower(0.0);
//                    drive.backRight.setPower(0.0);
//                    drive.backLeft.setPower(0.0);
//                }
            default:
                telemetry.addLine("No state commanded");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.DRIVE_START_POS_SHOOT_POS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);

        kick.init(hardwareMap);
        hood.init(hardwareMap);
        shooter.init(hardwareMap);
        limelightConfig.blueInit(hardwareMap);
        drive.init(hardwareMap);

        buildPaths();
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        shooter.shooterMid();
        hood.hoodMed();
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        autoKickMid();
    }
}
