package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.concurrent.TimeUnit;

@TeleOp
public class IntoTheDeepTeleOp2 extends LinearOpMode {
    void pickUp() throws InterruptedException {
        intakeClaw.setPosition(1);


        intakeUpDown.setPosition(0);
        TimeUnit.MILLISECONDS.sleep(250);
        intakeClaw.setPosition(0);
        TimeUnit.MILLISECONDS.sleep(250);
        intakeUpDown.setPosition(0.2);
    }

    public Servo intakeUpDown;
    public CRServo intakeWrist;
    public Servo intakeClaw;
    public DcMotor horizontal;
    public DcMotor vertical;
    public DcMotor hangTilter;
    public DcMotor hangMain;
    public int loopcount = 0;
    public DcMotor frontLeftMotor;
    public DcMotor backLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backRightMotor;
    public double pulsesPerRev0019 = 537.7;
    boolean prevHangMain =  false;
    String test;
    boolean changed = false;
    boolean clawPos = false;
    boolean prevClawPos = false;
    boolean hangPos = false;
    boolean prevHangChange = false;
    boolean hangChange = false;
    boolean pos2 = false;
    boolean pos3 = false;
    boolean prevWheelDebug = false;
    boolean prevInvert = false;
    boolean isInvert = false;
    double limit = pulsesPerRev0019 *1.5;//origional: 4.2
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        frontLeftMotor = hardwareMap.get(DcMotor.class,"front_left");
        backLeftMotor = hardwareMap.get(DcMotor.class,"back_left");
        frontRightMotor = hardwareMap.get(DcMotor.class,"front_right");
        backRightMotor = hardwareMap.get(DcMotor.class,"back_right");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontal = hardwareMap.dcMotor.get("horizontal");
        vertical = hardwareMap.dcMotor.get("vertical");
        hangTilter = hardwareMap.dcMotor.get("hang_motor");
        hangMain = hardwareMap.dcMotor.get("hang_main");
        intakeClaw = hardwareMap.servo.get("intake_claw");
        intakeUpDown = hardwareMap.servo.get("intake_up_down");
        intakeWrist = hardwareMap.crservo.get("intake_wrist");

        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangTilter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangTilter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setTargetPosition(0);
        hangTilter.setTargetPosition(0);
        hangMain.setTargetPosition(0);
        hangMain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);



        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            if((gamepad1.left_trigger !=0 && gamepad1.right_trigger != 0)&&!prevInvert){
                isInvert = !isInvert;
                prevInvert = true;
            } else if (!(gamepad1.left_trigger !=0 && gamepad1.right_trigger !=0 )) {
                prevInvert = false;
            }
            if (isInvert){
                y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
                x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            }else{
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
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;
            if (gamepad1.left_bumper){
                frontLeftMotor.setPower(0.75*frontLeftPower);
                backLeftMotor.setPower(0.75*backLeftPower);
                frontRightMotor.setPower(0.75*frontRightPower);
                backRightMotor.setPower(0.75*backRightPower);
            } else if (gamepad1.right_bumper) {
                frontLeftMotor.setPower(0.5 * frontLeftPower);
                backLeftMotor.setPower(0.5 * backLeftPower);
                frontRightMotor.setPower(0.5 * frontRightPower);
                backRightMotor.setPower(0.5 * backRightPower);

            }else{
                frontLeftMotor.setPower(frontLeftPower);
                backLeftMotor.setPower(backLeftPower);if(gamepad2.dpad_up){
                test="IN THINGW";
                //intakeUpDown.setPosition(60/360);
            }
                frontRightMotor.setPower(frontRightPower);
                backRightMotor.setPower(backRightPower);
            }
            if(gamepad2.a && !prevClawPos){
                if(intakeClaw.getPosition() == 1){
                    intakeClaw.setPosition(0);
                }else{
                    intakeClaw.setPosition(1);
                }
                prevClawPos = true;
            }else if(!gamepad2.a){
                prevClawPos = false;
            }
            if(gamepad2.left_bumper && !prevHangMain){
                hangPos = !hangPos;
                if(hangMain.getTargetPosition() == 0){
                    hangMain.setTargetPosition(-17500);
                }else{
                    hangMain.setTargetPosition(0);
                }
                prevHangMain = true;
            }else if(!gamepad2.left_bumper){
                prevHangMain = false;
            }
            if(gamepad2.right_bumper && !changed){
                if(intakeUpDown.getPosition() == 0){
                    intakeUpDown.setPosition(0.2);
                }else{
                    intakeUpDown.setPosition(0);
                }
                changed = true;
            }else if (!gamepad2.right_bumper){
                changed = false;
            }
            if(gamepad2.left_trigger !=0){
                intakeWrist.setPower(-gamepad2.left_trigger);
            } else if (gamepad2.right_trigger != 0) {
                intakeWrist.setPower(gamepad2.right_trigger);

            }else{
                    intakeWrist.setPower(0);}



            if(gamepad2.dpad_down && !prevHangChange){
                hangChange = !hangChange;
            }


            if (!hangChange){

                hangTilter.setTargetPosition(0);
            }else{
                hangTilter.setTargetPosition(700);
            }
            hangTilter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hangTilter.setPower(0.9);


            hangMain.setPower(0.9);

            if(gamepad2.dpad_up){
                pickUp();
            }
            if (gamepad2.b){
                vertical.setTargetPosition(0);
            }
            if (gamepad2.x){
                vertical.setTargetPosition(-470);

            }
            if (gamepad2.y){
                vertical.setTargetPosition(-1867);
            }
            vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertical.setPower(1);



            if(
                    (-horizontal.getCurrentPosition()<limit && -horizontal.getCurrentPosition() >0) ||
                            ((-horizontal.getCurrentPosition() >= limit && gamepad2.left_stick_y<=0) ||
                                    (-horizontal.getCurrentPosition() <= 0 && gamepad2.left_stick_y >= 0))){
                horizontal.setPower(gamepad2.left_stick_y/2);

            }
            //prevClawPos = gamepad2.a;
            prevHangChange = gamepad2.dpad_down;


            telemetry.addData("changed",changed);
            telemetry.addData("right bumper",gamepad2.right_bumper);
            telemetry.addData("servopos",intakeUpDown.getPosition());
            telemetry.addLine(test);
            telemetry.addData("Loop Count",loopcount);
            telemetry.addData("prevClawPos",prevClawPos);
            telemetry.addData("clawPos",clawPos);
            telemetry.addData("a",gamepad2.a);
            telemetry.addData("intake claw pos",intakeClaw.getPosition());
            telemetry.addData("intake wirst pow",intakeWrist.getPower());
            telemetry.addData("intake up down", intakeUpDown.getPosition());

            telemetry.update();

        }
    }
}