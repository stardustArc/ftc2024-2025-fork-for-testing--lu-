package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//import org.firstinspires.ftc.teamcode.PIDController;


//@TeleOp(name = "DoNotRun", group = "Libraries")
// @Disabled
public class sampleMechanum {
    public sampleMechanum(HardwareMap map, Telemetry telem) {
        telemetry = telem;
        hardwareMap = map;
        imu = hardwareMap.get(IMU.class, "imu");

        parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );



    }
    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    public static YawPitchRollAngles angles = new YawPitchRollAngles(AngleUnit.DEGREES,0.0,0.0,0.0,10L);
    public DcMotor lF;
    public DcMotor lB;
    public DcMotor rF;
    public DcMotor rB;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;
    boolean isRunning =true;
    private IMU imu;
    private IMU.Parameters parameters;

    static final double HD_COUNTS_PER_REV = 537;
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_CIRCUMFERENCE_MM = 96 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;
    public void begin(){                                                                                                                                                                                                                                                                                                                                                                                                
        lF = hardwareMap.dcMotor.get("front_left");
        lB = hardwareMap.dcMotor.get("back_left");
        rF = hardwareMap.dcMotor.get("front_right");
        rB = hardwareMap.dcMotor.get("back_right");
        imu.initialize(parameters);
        telemetry.addData("Status", "Initialized");
        lF.setDirection(DcMotor.Direction.FORWARD);
        rF.setDirection(DcMotor.Direction.REVERSE);
        lB.setDirection(DcMotor.Direction.FORWARD);
        rB.setDirection(DcMotor.Direction.REVERSE);
        lF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    public void update(double x, double y, double t, boolean power75, boolean power25, boolean resetOrient,boolean isAuto,int lFTarget,int lBTarget, int rFTarget, int rBTarget,Directions directions) {
        // rotation
        if(!isAuto) {
            y= y;
            x=-x;
            t = t;
            angles = imu.getRobotYawPitchRollAngles();
            double yaw = angles.getYaw(AngleUnit.RADIANS) - (Math.PI/2);
            double roll = angles.getRoll(AngleUnit.RADIANS) - (Math.PI/2);
            double pitch = angles.getPitch(AngleUnit.RADIANS) - (Math.PI/2);
            double x_rotated = x * Math.cos(-angles.getYaw(AngleUnit.RADIANS)) - y * Math.sin(angles.getYaw(AngleUnit.RADIANS));
            double y_rotated = x * Math.sin(-angles.getYaw(AngleUnit.RADIANS)) + y * Math.cos(angles.getYaw(AngleUnit.RADIANS));
            telemetry.addData(" roll angle: ", roll);
            telemetry.addData(" pitch angle: ", pitch);
            telemetry.addData(" yaw angle: ", yaw);
            // x, y, theta input mixing
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(t), 1);
            frontLeftPower = (y_rotated + x_rotated + t) / denominator;
            backLeftPower = (y_rotated - x_rotated + t) / denominator;
            frontRightPower = (y_rotated - x_rotated - t) / denominator;
            backRightPower = (y_rotated + x_rotated - t) / denominator;
            // Send calculated power to motors
            if (power75) {
                lF.setPower(frontLeftPower * 0.75);
                rF.setPower(frontRightPower * 0.75);
                lB.setPower(backLeftPower * 0.75);
                rB.setPower(backRightPower * 0.75);
            } else if (power25) {
                lF.setPower(frontLeftPower * 0.25);
                rF.setPower(frontRightPower * 0.25);
                lB.setPower(backLeftPower * 0.25);
                rB.setPower(backRightPower * 0.25);
            } else {
                lF.setPower(frontLeftPower * 0.90);
                rF.setPower(frontRightPower * 0.90);
                lB.setPower(backLeftPower * 0.90);
                rB.setPower(backRightPower * 0.90);
            }
            // reinitialize field oriented
            if (resetOrient) {
                imu.initialize(parameters);
            }
        }else{

                int rightTarget = 0;
                int leftTarget = 0;
                int rightFrontTarget = 0;
                int leftBackTarget = 0;
                //go forwards
                if (directions.operation==0) {
                    rightTarget = rB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    //go bacjwars
                } else if (directions.operation == 1){
                    rightTarget = rB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    //go sideways
                } else if (directions.operation ==2){
                    rightTarget = rB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    //Go siedways
                } else if (directions.operation == 3){
                    rightTarget = rB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                }else if (directions.operation == 4){
                    rightTarget = rB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                }else if (directions.operation ==5){
                    rightTarget = rB.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftTarget = lF.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    rightFrontTarget = rF.getCurrentPosition() - (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                    leftBackTarget = lB.getCurrentPosition() + (int) (directions.inches * DRIVE_COUNTS_PER_IN);
                }
                lF.setTargetPosition(leftTarget);
                rB.setTargetPosition(rightTarget);
                lB.setTargetPosition(leftBackTarget);
                rF.setTargetPosition(rightFrontTarget);
                //switch to run to position mode
                lF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                //run to position at the desiginated power
                lF.setPower(0.5);
                rB.setPower(0.5);
                lB.setPower(0.5);
                rF.setPower(0.5);

                // wait until both motors are no longer busy running to position
                while ((lF.isBusy() || rB.isBusy() || rF.isBusy() || lB.isBusy())) {
                }

                // set motor power back to 0
                lF.setPower(0);
                rB.setPower(0);
                lB.setPower(0);
                rF.setPower(0);


        }
    }
}
