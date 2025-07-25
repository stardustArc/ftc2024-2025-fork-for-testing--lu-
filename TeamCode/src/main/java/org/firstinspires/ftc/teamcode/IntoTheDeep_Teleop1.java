
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;




@TeleOp(group = "IN_TO_THE_DEEP")
public class IntoTheDeep_Teleop1 extends LinearOpMode{
    //instantiating mecanum and motor libraries/maps
    private newMecanum mecanum;
    private sampleMotors motors;
    private sampleMotors2 motors2;
    //declaring variables
    private double test;
    private boolean twenntyFive = false;
    private boolean seventyFive = false;
    private Directions fake = new Directions(Operation.FORWARD,0);


    @Override
    //where all the code goes
    public void runOpMode() throws InterruptedException {
        //instantiating mecanum and motor libraries/maps
        mecanum = new newMecanum(hardwareMap, telemetry);
        motors = new sampleMotors(hardwareMap, telemetry);
        motors2 = new sampleMotors2(hardwareMap,telemetry);

        mecanum.begin();
        //motors.begin();
        motors2.begin();
        waitForStart();
        //where most of the code goes. this runs on a loop while the opmode is active
        while (opModeIsActive()) {
            //set values for motors
            if(gamepad1.left_bumper){
                twenntyFive = true;
            }else{
                twenntyFive = false;
            }
            if(gamepad1.right_bumper){
                seventyFive = true;
            }else{
                seventyFive = false;
            }
            if (gamepad2.left_trigger>gamepad2.right_trigger){
                test = -gamepad2.left_trigger;
            }else if (gamepad2.right_trigger>gamepad2.left_trigger){
                test = gamepad2.right_trigger;
            }

            //sending values to mecanum and motors
            mecanum.updateBotOriented(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x,gamepad2.right_bumper);
            //motors.update(gamepad2.a, false,false,gamepad2.left_stick_y,gamepad2.right_stick_y,test,gamepad2.x);
            motors2.update(gamepad2.left_stick_y, gamepad2.a,gamepad2.right_stick_y,gamepad2.b,gamepad2.x,gamepad2.y,gamepad2.left_bumper,gamepad2.a);
        }
        Thread.sleep(1500);
    }
}
