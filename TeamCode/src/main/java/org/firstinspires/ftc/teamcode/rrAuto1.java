package org.firstinspires.ftc.teamcode;


import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Config
@Autonomous(name = "BLUE_TEST_AUTO_PIXEL", group = "Autonomous")
public class rrAuto1 extends LinearOpMode{
    /*public DcMotorEx lift;

    void liftUp(){
        if(lift.getPower() < 0.9){
            lift.setPower(0.9);
        }
        lift.setTargetPosition(-1867);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    void liftDown(){
        lift.setPower(0.9);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public Action liftUp(){
        if(lift.getPower() < 0.9){
            lift.setPower(0.9);
        }
        lift.setTargetPosition(-1867);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }*/


    // lift class
    public class Lift {
        private DcMotorEx lift;

        public Lift(HardwareMap hardwareMap) {
            lift = hardwareMap.get(DcMotorEx.class, "vertical");
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lift.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        public class LiftUp implements Action {
            // checks if the lift motor has been powered on
            private boolean initialized = false;

            // actions are formatted via telemetry packets as below
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // powers on motor, if it is not on
                if (!initialized) {
                    lift.setPower(0.8);
                    initialized = true;
                }

                // checks lift's current position
                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 1867) {
                    // true causes the action to rerun
                    return true;
                } else {
                    // false stops action rerun
                    lift.setPower(0);
                    return false;
                }
                // overall, the action powers the lift until it surpasses
                // 3000 encoder ticks, then powers it off
            }
        }
        public class LiftDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(-0.8);

                    initialized = true;
                }

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 150) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }
        }
        public Action liftDown() {
            return new LiftDown();
        }

        public Action liftUp() {
            return new LiftUp();
        }
    }
    @Override
    public void runOpMode() {

        // instantiate your MecanumDrive at a particular pose
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(180.00));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        // make a Claw instance

        // make a Lift instance
        Lift lift = new Lift(hardwareMap);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                //.splineToConstantHeading(new Vector2d(24,-10),Math.PI/2);
                //.lineToY(-5)
                .strafeToConstantHeading(new Vector2d(0,-10))
                .waitSeconds(0.5)
                //.splineTo(new Vector2d(0,-24),Math.toRadians(0.00));
               .lineToX(24);

        TrajectoryActionBuilder tab2 = drive.actionBuilder(initialPose)
                .lineToX(-5
                )
                .waitSeconds(1)
                        .splineTo(new Vector2d(0,-60),Math.PI/2);
                                //.waitSeconds(1)
                                        //.lineToX(30);

       /*TrajectoryActionBuilder tab3 = drive.actionBuilder(initialPose)
               .splineTo(new Vector2d(-9.11, -34.83), Math.toRadians(88.45))
               .waitSeconds(1)
               .splineTo(new Vector2d(-6.10, -45.15), Math.toRadians(-30.83))
               .waitSeconds(1)
               .splineTo(new Vector2d(56.42, -60.92), Math.toRadians(0.00))
               .waitSeconds(1)
               .splineTo(new Vector2d(3.66, -33.89), Math.toRadians(88.16))
               .waitSeconds(1)
               .splineTo(new Vector2d(6.29, -41.21), Math.toRadians(-10.62))
               .waitSeconds(1)
               .splineTo(new Vector2d(34.83, -33.89), Math.toRadians(27.76))
               .waitSeconds(1)
               .splineTo(new Vector2d(47.22, -13.24), Math.toRadians(-87.73))
               .waitSeconds(1)
               .splineTo(new Vector2d(47.41, -42.15), Math.toRadians(266.58))
               .waitSeconds(1)
               .splineTo(new Vector2d(47.22, -56.60), Math.toRadians(261.12))
               .waitSeconds(1)
               .splineTo(new Vector2d(47.41, -61.49), Math.toRadians(248.20))
               .waitSeconds(1)
               .splineTo(new Vector2d(47.78, -64.68), Math.toRadians(194.04));
        */
        /*Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .strafeTo(new Vector2d(48, 12))
                .build(); */


            Actions.runBlocking(
                    new SequentialAction(

                            lift.liftUp(),
                            tab1.build(),


                            //claw.openClaw(),
                            lift.liftDown(),
                            tab2.build()
                            //tab3.build()
                            //trajectoryActionCloseOut
                    )
            );


    }


}
