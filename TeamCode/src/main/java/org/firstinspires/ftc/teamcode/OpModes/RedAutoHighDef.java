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
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Red Auto")
public class RedAutoHighDef extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    private KickConfig kick = new KickConfig();
    private Hood hood = new Hood();
    private Shooter shooter = new Shooter();

    public enum PathState {
        // START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT TO SCORE THE ARTIFACT
        DRIVE_START_POS_SHOOT_POS,
        SHOOT_PRELOAD,
        STOP
    }

    PathState pathState;

    private final Pose startPose = new Pose(123.536585366, 122.92682926829264, Math.toRadians(36));
    private final Pose shootPos = new Pose(106.341463415, 126.04878048780488, Math.toRadians(24));

    private PathChain driveStartPosShootPos;

    public void buildPaths() {
        // put in coordinates for starting pose > ending pose
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPos))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPos.getHeading())
                .build();
    }


    public int stepN = -1;
    public void autoKickNear(){
        if(stepN == -1) return;
        switch(stepN){
            case 0:
                hood.hoodLow();
                kick.kickOne();
                stepN = 1;
                break;
            case 1:
                if(pathTimer.getElapsedTimeSeconds() > 2.6){
                    kick.retractOne();
                    stepN = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                stepN = 3;
                break;
            case 3:
                if(pathTimer.getElapsedTimeSeconds() > 3.2){
                    kick.retractTwo();
                    stepN = 4;
                }
                break;
            case 4:
                kick.kickThree();
                stepN = 5;
                break;
            case 5:
                if(pathTimer.getElapsedTimeSeconds() > 3.8){
                    kick.retractThree();
                    stepN = -1;
                }
                break;


        }
    }

    public void statePathUpdate() {
        switch(pathState) {
            case DRIVE_START_POS_SHOOT_POS:
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT_PRELOAD); // reset timer & transition to new state
                break;
            case SHOOT_PRELOAD:
                // check is follower done its path
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.0) {
                    stepN = 0;
                    telemetry.addLine("Firing");
                    setPathState(PathState.STOP);
                }
                break;
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

        buildPaths();
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        shooter.shooterNear();
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        autoKickNear();

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
    }
}
