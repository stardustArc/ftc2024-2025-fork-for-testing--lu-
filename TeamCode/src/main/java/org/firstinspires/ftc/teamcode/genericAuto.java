package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(group = "IN_TO_THE_DEEP")
public class genericAuto extends LinearOpMode {
   
    private Telemetry telemetry;
    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;
    //replace with your motors encoder resolution
    double ticksPerRev = 537.7;

    /*public genericAuto(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }*/

    void forward(double length){
        leftFront.setTargetPosition((int) Math.round(length));
        rightFront.setTargetPosition((int) Math.round(length));
        leftBack.setTargetPosition((int) Math.round(length));
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
    void backwards(double length){
        leftFront.setTargetPosition(-(int) Math.round(length));
        rightFront.setTargetPosition(-(int) Math.round(length));
        leftBack.setTargetPosition(-(int) Math.round(length));
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
    void left(double length){
        leftFront.setTargetPosition(-(int) Math.round(length));
        rightFront.setTargetPosition((int) Math.round(length));
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
    void right(double length){
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
    void rightBack(double length){

        rightFront.setTargetPosition(-(int) Math.round(length));
        leftBack.setTargetPosition(-(int) Math.round(length));

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightFront.setPower(0.9);
        leftBack.setPower(0.9);

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
    void leftBack(double length){
        leftFront.setTargetPosition(-(int) Math.round(length));
        rightBack.setTargetPosition(-(int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
        rightBack.setPower(0.9);
        while(leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy());
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
        leftFront.setTargetPosition((int) Math.round(length));
        rightBack.setTargetPosition((int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setPower(0.9);
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
    void leftForward(double length){
        rightFront.setTargetPosition((int) Math.round(length));
        leftBack.setTargetPosition((int) Math.round(length));
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setPower(0.9);
        leftBack.setPower(0.9);
        while(leftFront.isBusy() || rightFront.isBusy() || leftBack.isBusy() || rightBack.isBusy()){

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
        waitForStart();
        while (opModeIsActive()){
            forward(ticksPerRev);
            backwards(ticksPerRev);
            right(ticksPerRev);
            left(ticksPerRev);
            rightBack(ticksPerRev);
            leftBack(ticksPerRev);
            leftForward(ticksPerRev);
            rightForward(ticksPerRev);

        }
    }

}
