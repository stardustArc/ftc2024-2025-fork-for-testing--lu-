package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(group = "IN_TO_THE_DEEP")
public class test extends LinearOpMode{
    //instantiating mecanum and motor libraries/maps

    private sampleMotors motors;
    //declaring variables
    //testmotor
    private boolean runServo;
    private boolean runMotor;


    @Override
    public void runOpMode() throws InterruptedException {

        motors = new sampleMotors(hardwareMap, telemetry);

        motors.begin();
        waitForStart();
        while (opModeIsActive()) {
            runMotor = gamepad1.a;
            runServo = gamepad1.b;
            motors.update(gamepad1.a, runMotor,runServo,1.0,1.0,1.0,false);

        }
        //sending values to mecanum and motors


        //Thread.sleep(1500);
    }
}

