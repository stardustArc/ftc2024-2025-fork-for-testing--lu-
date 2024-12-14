package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.Base64;
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
    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;
    public DcMotor par;
    public DcMotor perp;
    public DcMotor currentDeadWheel;
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
    //static final double DRIVE_COUNTS_PER_IN = 8;
    public void begin(){                                                                                                                                                                                                                                                                                                                                                                                                
        leftFront = hardwareMap.dcMotor.get("front_left");
        leftBack = hardwareMap.dcMotor.get("back_left");
        rightFront = hardwareMap.dcMotor.get("front_right");
        rightBack = hardwareMap.dcMotor.get("back_right");
        par = hardwareMap.dcMotor.get("par");
        perp = hardwareMap.dcMotor.get("perp");
        imu.initialize(parameters);
        telemetry.addData("Status", "Initialized");
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }
    public void update(double x, double y, double t, boolean power75, boolean power25, boolean resetOrient,boolean isAuto,int leftFronTarget,int leftBackTrget, int rightFrontTargt, int rightBackTaget,Directions directions) {
        // rotation
        if(!isAuto) {
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            y= y;
            x=x;

            t = t;
            angles = imu.getRobotYawPitchRollAngles();
            double yaw = angles.getYaw(AngleUnit.RADIANS);
            double roll = angles.getRoll(AngleUnit.RADIANS);
            double pitch = angles.getPitch(AngleUnit.RADIANS);
            //double x_rotated = x * Math.cos(angles.getYaw(AngleUnit.RADIANS)) + y * Math.sin(-angles.getYaw(AngleUnit.RADIANS));
            //double y_rotated = x * Math.sin(-angles.getYaw(AngleUnit.RADIANS)) - y * Math.cos(angles.getYaw(AngleUnit.RADIANS));
            double x_rotated = x;
            double y_rotated = y;

            telemetry.addData(" roll angle: ", roll);
            telemetry.addData(" pitch angle: ", pitch);
            telemetry.addData(" yaw angle: ", yaw);
            telemetry.addData("x_rotated", x_rotated);
            telemetry.addData("y_rotated", y_rotated);
            // x, y, theta input mixing
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(t), 1);
            telemetry.addData("denominator",denominator);
            frontLeftPower = (y_rotated + x_rotated + t) / denominator;
            backLeftPower = (y_rotated - x_rotated + t) / denominator;
            frontRightPower = -(y_rotated - x_rotated - t) / denominator;
            backRightPower = -(y_rotated + x_rotated - t) / denominator;
            telemetry.addData("front left power", frontLeftPower);
            telemetry.addData("Front RIght power",frontRightPower);
            telemetry.addData("back left Power",backLeftPower);
            telemetry.addData("back right power",backRightPower);
            telemetry.addData("x:", x);
            telemetry.addData("y",y);
            telemetry.addData("t",t);
            // Send calculated power to motors
            if (power75) {
                leftFront.setPower(frontLeftPower * 0.75);
                rightFront.setPower(frontRightPower * 0.75);
                leftBack.setPower(backLeftPower * 0.75);
                rightBack.setPower(backRightPower * 0.75);
            } else if (power25) {
                leftFront.setPower(frontLeftPower * 0.25);
                rightFront.setPower(frontRightPower * 0.25);
                leftBack.setPower(backLeftPower * 0.25);
                rightBack.setPower(backRightPower * 0.25);
            } else {
               leftFront.setPower(frontLeftPower * 0.90);
                rightFront.setPower(frontRightPower * 0.90);
                leftBack.setPower(backLeftPower * 0.90);
                rightBack.setPower(backRightPower * 0.90);
            }
            // reinitialize field oriented
            if (resetOrient) {
                imu.initialize(parameters);
            }
        }else{
                double inchesToTicks = directions.inches*2000;


                double lfPower = 0.5;
                double rfPower = 0.5;
                double lbPower = 0.5;
                double rbPower = 0.5;
                switch (directions.operation){
                    case FORWARD:
                        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
                        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
                        currentDeadWheel = par;
                    case BACKWARD:
                        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
                        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                        currentDeadWheel = par;

                    case RIGHT:
                        //right
                        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
                        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
                        currentDeadWheel = perp;

                    case LEFT:
                        //left
                        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
                        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
                        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
                        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
                        currentDeadWheel = perp;
                    case CLOCKWISE:
                        //figure out turning later

                    case COUNTERCLOCKWISE:
                        //figure out turning later
                }

                rightBack.setPower(rbPower);
                rightFront.setPower(rfPower);
                leftBack.setPower(lbPower);
                leftFront.setPower(lfPower);

                while (currentDeadWheel.getCurrentPosition()<=inchesToTicks){

                }


                // set motor power back to 0
                telemetry.addLine("loop ended");
                leftFront.setPower(0);
                rightBack.setPower(0);
                leftBack.setPower(0);
                rightFront.setPower(0);
                telemetry.update();

        }
    }
}
