package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.concurrent.TimeUnit;

@Autonomous(group = "IN_TO_THE_DEEP")

public class autoVIdkMaybe7AtThisPoint extends LinearOpMode {

    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;
    public DcMotor par;
    public DcMotor perp;
    double compY;
    public DcMotor vertical;
    //replace with your motors encoder resolution
    double ticksPerRev = 537.7;

    /*public genericAuto(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }*/


    void forward(double length) throws InterruptedException {
         double targetPos = par.getCurrentPosition() + length;
         telemetry.addLine("I AM ENTERING FORWARDS!!!!");
         telemetry.addData("target", (par.getCurrentPosition()-length));
        telemetry.addData("current1",par.getCurrentPosition());
        telemetry.update();

        while( par.getCurrentPosition() < targetPos && opModeIsActive()){
            leftFront.setPower(-0.9);
            rightFront.setPower(0.9);
            leftBack.setPower(-0.9);
            rightBack.setPower(0.9);
            telemetry.addData("target", (targetPos));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
        };
        telemetry.addData("current",par.getCurrentPosition());
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);


    }
    void backwards(double length) throws InterruptedException {
        double targetPos = par.getCurrentPosition() - length;
        telemetry.addLine("ENTERING BACKWARDS");

        while(par.getCurrentPosition() > targetPos){
            leftFront.setPower(0.9);
            rightFront.setPower(-0.9);
            leftBack.setPower(0.9);
            rightBack.setPower(-0.9);
            telemetry.addData("target", (targetPos));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
        };
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);


    }
    void left(double length,IMU imu) throws InterruptedException {
        //tune encoder direction
        double targetPos = perp.getCurrentPosition() + length;
        telemetry.addLine("NOW ENTERING LEFT");
        double targetPower = 1.0;
        double currentPower = 0.1;
        double currentY = par.getCurrentPosition();



        while(perp.getCurrentPosition() < targetPos){
            if(par.getCurrentPosition() > currentY + 20){
                compY = -0.1;
            } else if (par.getCurrentPosition() < currentY-20) {
                compY = 0.1;
            }else {
                compY = 0;
            }
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = -1 * Math.cos(-botHeading) - compY * Math.sin(-botHeading);
            double rotY = -1 * Math.sin(-botHeading) + compY * Math.cos(-botHeading);

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

            /*double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(0), 1);
            double frontLeftPower = (rotY + rotX + 0) / denominator;
            double backLeftPower = (rotY - rotX + 0) / denominator;
            //double frontRightPower = (rotY - rotX - 0) / denominator;
            //double backRightPower = (rotY + rotX - 0) / denominator;
            double frontRightPower = (-rotY - rotX - 0) / denominator;
            double backRightPower = (-rotY + rotX - 0) / denominator;*/
            double denominator = Math.max(Math.abs(compY) + Math.abs(-1) + Math.abs(0), 1);
            double frontLeftPower = (compY + (-1) + 0) / denominator;
            double backLeftPower = (-compY - (-1) + 0) / denominator;
            //double frontRightPower = (rotY - rotX - 0) / denominator;
            //double backRightPower = (rotY + rotX - 0) / denominator;
            double frontRightPower = (-compY - (-1) - 0) / denominator;
            double backRightPower = (compY + (-1) - 0) / denominator;
            leftFront.setPower(frontLeftPower*currentPower);
            rightFront.setPower(frontRightPower*currentPower);
            leftBack.setPower(backLeftPower*currentPower);
            rightBack.setPower(backRightPower*currentPower);
            //leftBack.setPower(-currentPower);
            //rightBack.setPower(-currentPower);
            telemetry.addData("par",par.getCurrentPosition());
            telemetry.addData("perp",perp.getCurrentPosition());
            telemetry.addData("compY",compY);
            telemetry.addData("frontLeft",frontLeftPower);
            telemetry.addData("front Right",frontRightPower);
            telemetry.addData("back left", backLeftPower);
            telemetry.addData("back right",backRightPower);
            telemetry.addData("rotX",rotX);
            telemetry.addData("rotY",rotY);
            telemetry.addData("yaw rads",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.addData("yaw degs",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
            currentPower = Math.min(currentPower * 1.15,targetPower);
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
    void right(double length) throws InterruptedException {
        //tune encoder Direction
        double targetPos = perp.getCurrentPosition() + length;
        telemetry.addLine("NOW ENTERING RIGHT!!");
        double targetPower = 1.0;
        double currentPower = 0.1;


        while(perp.getCurrentPosition() < targetPos){
            leftFront.setPower(-currentPower);
            rightFront.setPower(-currentPower);
            leftBack.setPower(currentPower);
            rightBack.setPower(currentPower);
            telemetry.addData("target", (targetPos));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
            currentPower = Math.min(currentPower * 1.15,targetPower);
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
    void rightBack(double length){
        double targetPos = par.getCurrentPosition() - length;
        rightFront.setTargetPosition(-(int) Math.round(length));
        leftBack.setTargetPosition(-(int) Math.round(length));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightFront.setPower(0.9);
        leftBack.setPower(0.9);

        while(perp.getCurrentPosition() > targetPos){

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    void leftBack(double length){
        double targetPos = par.getCurrentPosition() - length;
        leftFront.setTargetPosition(-(int) Math.round(length));
        rightBack.setTargetPosition(-(int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
        rightBack.setPower(0.9);
        while(perp.getCurrentPosition() < targetPos){

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    void rightForward(double length){
        double targetPos = par.getCurrentPosition() - length;
        leftFront.setTargetPosition((int) Math.round(length));
        rightBack.setTargetPosition((int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
        rightBack.setPower(0.9);
        while(perp.getCurrentPosition() < targetPos){

        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    void leftForward(double length){
        double targetPos = par.getCurrentPosition() - length;
        rightFront.setTargetPosition((int) Math.round(length));
        leftBack.setTargetPosition((int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setPower(0.9);
        leftBack.setPower(0.9);
        while(perp.getCurrentPosition() < targetPos){

        }
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    //UNTESTED!!!! YOU WILL NEED TO TAKE TIME TO FIGURE OUT LENGTH TO DEGREES!!!
    void clockwise(double length){
        double targetPos = perp.getCurrentPosition() - 1/2 * (length);

        while(perp.getCurrentPosition() > targetPos){
            leftFront.setPower(1);
            rightFront.setPower(1);
            leftBack.setPower(1);
            rightBack.setPower(1);
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
    //UNTESTED!!!! YOU WILL NEED TO TAKE TIME TO FIGURE OUT LENGTH TO DEGREES!!!
    void counterClockwise(double length){
        double targetPos = perp.getCurrentPosition() + length;

        while(perp.getCurrentPosition() < targetPos){
            leftFront.setPower(-1);
            rightFront.setPower(-1);
            leftBack.setPower(-1);
            rightBack.setPower(-1);
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        //replace with names of your motors or chang names of your motors idk
       /* leftFront = hardwareMap.dcMotor.get("front_left");
        leftBack = hardwareMap.dcMotor.get("back_left");
        rightFront = hardwareMap.dcMotor.get("front_right");
        rightBack = hardwareMap.dcMotor.get("back_right");*/
        leftFront = hardwareMap.get(DcMotor.class,"front_left");
        leftBack = hardwareMap.get(DcMotor.class,"back_left");
        rightFront = hardwareMap.get(DcMotor.class,"front_right");
        rightBack = hardwareMap.get(DcMotor.class,"back_right");
        vertical = hardwareMap.get(DcMotor.class,"vertical");
        par = hardwareMap.get(DcMotor.class, "front_left");
        perp = hardwareMap.get(DcMotor.class, "back_right");
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        par.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        perp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        par.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        perp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setTargetPosition(0);
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        par.setPower(0);
        perp.setPower(0);
        vertical.setPower(1);

        waitForStart();
        while (opModeIsActive()){
            vertical.setTargetPosition(-1867);
            TimeUnit.MILLISECONDS.sleep(500);
            /*while (vertical.getCurrentPosition()!=vertical.getTargetPosition()){
                telemetry.addData("current vert",vertical.getCurrentPosition());
                telemetry.addData("target vert",vertical.getTargetPosition());
            }*/
            //backwards(4000);
            TimeUnit.MILLISECONDS.sleep(500);
            par.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            par.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            vertical.setTargetPosition(-1200);
            TimeUnit.MILLISECONDS.sleep(500);
            //forward(5);

            left(100,imu);
            //clockwise(5);
            TimeUnit.MILLISECONDS.sleep(500);
            //forward(5);
            TimeUnit.MILLISECONDS.sleep(500);
            //right(5);
            terminateOpModeNow();


        }
    }


}