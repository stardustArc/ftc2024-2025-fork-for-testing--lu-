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
public class itdControlsV2 extends LinearOpMode {
    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;
    boolean prevInvert = false;
    boolean isInvert = false;


    //speed controls/bumper outputs
    double frontLeftOutput;
    double backLeftOutput;
    double frontRightOutput;
    double backRightOutput;

    //default motor speed
    double multiplier;


    @Override
    public void runOpMode() throws InterruptedException {

        //declaring motors (ID must match DS config)
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // GoBilda motors spin counterclockwise by default, must reverse direction
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

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

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            if ((gamepad1.left_trigger != 0 && gamepad1.right_trigger != 0) && !prevInvert) {
                isInvert = !isInvert;
                prevInvert = true;
            } else if (!(gamepad1.left_trigger != 0 && gamepad1.right_trigger != 0)) {
                prevInvert = false;
            }
            if (isInvert) {
                y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
                x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            } else {
                y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
                x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            }

            if (gamepad1.a) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            /*double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;*/
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            frontLeftOutput = (rotY + rotX + rx) / denominator;
            backLeftOutput = (rotY - rotX + rx) / denominator;
            frontRightOutput = (rotY - rotX - rx) / denominator;
            backRightOutput = (rotY + rotX - rx) / denominator;
            //not outputting values to motor power, sending to below section for simplified controls

            //(probably) simpler bumper controls, input mecanum output and output power %
            frontLeftDrive.setPower(multiplier * frontLeftOutput);
            backLeftDrive.setPower(multiplier * backLeftOutput);
            frontRightDrive.setPower(multiplier * frontRightOutput);
            backRightDrive.setPower(multiplier * frontRightOutput);

            //bumper speed controls
            if (gamepad1.right_bumper) {
                multiplier = 1; //overdrive
                //frontLeftDrive.setPower(0.75 * frontLeftPower);
                //backLeftDrive.setPower(0.75 * backLeftPower);
                //frontRightDrive.setPower(0.75 * frontRightPower);
                //backRightDrive.setPower(0.75 * backRightPower);
            } else if (gamepad1.left_bumper) {
                multiplier = 0.4; //low speed
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
            telemetry.addData("Yaw:",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();


        }
    }
}
