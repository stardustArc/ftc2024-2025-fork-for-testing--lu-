package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

@Autonomous(group = "IN_TO_THE_DEEP")

public class autoVIdkMaybe7AtThisPoint extends LinearOpMode {

    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;
    public DcMotor par;
    public DcMotor perp;
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
            leftFront.setPower(-0.9);
            rightFront.setPower(0.9);
            leftBack.setPower(-0.9);
            rightBack.setPower(0.9);
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
    void left(double length) throws InterruptedException {
        //tune encoder direction
        double targetPos = perp.getCurrentPosition() + length;
        telemetry.addLine("NOW ENTERING LEFT");


        while(perp.getCurrentPosition() < targetPos){
            leftFront.setPower(0.9);
            rightFront.setPower(0.9);
            leftBack.setPower(-0.9);
            rightBack.setPower(-0.9);
            telemetry.addData("target", (targetPos));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

    }
    void right(double length) throws InterruptedException {
        //tune encoder Direction
        double targetPos = perp.getCurrentPosition() - length;
        telemetry.addLine("NOW ENTERING RIGHT!!");


        while(perp.getCurrentPosition() > targetPos){
            leftFront.setPower(-0.9);
            rightFront.setPower(-0.9);
            leftBack.setPower(0.9);
            rightBack.setPower(0.9);
            telemetry.addData("target", (targetPos));
            telemetry.addData("current",par.getCurrentPosition());
            TimeUnit.MILLISECONDS.sleep(250);
            telemetry.update();
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
        leftFront.setTargetPosition((int) Math.round(length));
        rightFront.setTargetPosition(-(int) Math.round(length));
        leftBack.setTargetPosition((int) Math.round(length));
        rightBack.setTargetPosition(-(int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
        rightFront.setPower(0.9);
        leftBack.setPower(0.9);
        rightBack.setPower(0.9);
        while(leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy()){

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
    //UNTESTED!!!! YOU WILL NEED TO TAKE TIME TO FIGURE OUT LENGTH TO DEGREES!!!
    void counterClockwise(double length){
        leftFront.setTargetPosition(-(int) Math.round(length));
        rightFront.setTargetPosition((int) Math.round(length));
        leftBack.setTargetPosition(-(int) Math.round(length));
        rightBack.setTargetPosition((int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
        rightFront.setPower(0.9);
        leftBack.setPower(0.9);
        rightBack.setPower(0.9);

        while(leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy()){

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

    @Override
    public void runOpMode() throws InterruptedException {
        //replace with names of your motors or chang names of your motors idk
       /* leftFront = hardwareMap.dcMotor.get("front_left");
        leftBack = hardwareMap.dcMotor.get("back_left");
        rightFront = hardwareMap.dcMotor.get("front_right");
        rightBack = hardwareMap.dcMotor.get("back_right");*/
        leftFront = hardwareMap.get(DcMotor.class,"front_left");
        leftBack = hardwareMap.get(DcMotor.class,"back_left");
        rightFront = hardwareMap.get(DcMotor.class,"front_right");
        rightBack = hardwareMap.get(DcMotor.class,"back_right");
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
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        par.setPower(0);
        perp.setPower(0);

        waitForStart();
        while (opModeIsActive()){
            forward(50);
            backwards(500);
            right(500);
            left(500);
            terminateOpModeNow();


        }
    }


}