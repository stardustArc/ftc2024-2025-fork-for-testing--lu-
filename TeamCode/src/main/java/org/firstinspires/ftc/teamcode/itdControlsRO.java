package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.BasicOmniOpMode_Linear;

@TeleOp
public class itdControlsRO extends LinearOpMode {
    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;

    //speed controls/bumper outputs
    double frontLeftOutput;
    double backLeftOutput;
    double frontRightOutput;
    double backRightOutput;

    //default motor speed
    double multiplier;

    public void runOpMode() throws InterruptedException {

        //declaring motors (ID must match DS config)
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right");

        // GoBilda motors spin counterclockwise by default, must reverse direction
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        //sets drive motors to run without encoder
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //brakes to slow motor/robot down faster
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            frontLeftOutput = axial + lateral + yaw;
            frontRightOutput = axial - lateral - yaw;
            backLeftOutput = axial - lateral + yaw;
            backRightOutput = axial + lateral - yaw;

            frontLeftDrive.setPower(multiplier * frontLeftOutput);
            backLeftDrive.setPower(multiplier * backLeftOutput);
            frontRightDrive.setPower(multiplier * frontRightOutput);
            backRightDrive.setPower(multiplier * backRightOutput);

            //bumper speed controls
            if (gamepad1.right_bumper) {
                multiplier = 1; //overdrive
                //frontLeftDrive.setPower(0.75 * frontLeftPower);
                //backLeftDrive.setPower(0.75 * backLeftPower);
                //frontRightDrive.setPower(0.75 * frontRightPower);
                //backRightDrive.setPower(0.75 * backRightPower);
            } else if (gamepad1.left_bumper) {
                multiplier = 0.45; //low speed
                //frontLeftDrive.setPower(0.5 * frontLeftPower);
                //backLeftDrive.setPower(0.5 * backLeftPower);
                //frontRightDrive.setPower(0.5 * frontRightPower);
                //backRightDrive.setPower(0.5 * backRightPower);

            } else {
                multiplier = 0.65; //standard speed
            }
            // Show the elapsed game time and wheel power.
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", (frontLeftOutput * multiplier), (frontRightOutput * multiplier));
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", (backLeftOutput * multiplier), (backRightOutput * multiplier));
            telemetry.update();
        }
    }
}
