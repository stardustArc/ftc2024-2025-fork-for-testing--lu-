package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(group = "IN_TO_THE_DEEP")
public class IntoTheDeep_Auto1 extends LinearOpMode {
    private sampleMechanum mecanum;
    private sampleMotors motors;

    @Override
    public void runOpMode() throws InterruptedException {
        mecanum = new sampleMechanum(hardwareMap, telemetry);
        mecanum.begin();
        Directions forward = new Directions(Operation.FORWARD,5);
        Directions backward = new Directions(Operation.RIGHT,5);



        Directions[] directionsArray = {forward,backward};
        waitForStart();
        while (opModeIsActive()){
            for (int i = 0; i < directionsArray.length; i++) {
                telemetry.addData("loopstart","done");

                mecanum.update(gamepad1.left_stick_x * 1.1, gamepad1.left_stick_y, gamepad1.right_stick_x, false, false, gamepad1.left_bumper && gamepad1.right_bumper,true,10,10,10,10,directionsArray[i]);
                telemetry.addData("done1","done");
                telemetry.update();
            }
            terminateOpModeNow();
            telemetry.addData("done","done");
            telemetry.update();

        }
    }


}
