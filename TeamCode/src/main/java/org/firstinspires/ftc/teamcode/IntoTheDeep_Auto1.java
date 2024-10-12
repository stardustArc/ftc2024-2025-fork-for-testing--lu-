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
        waitForStart();
        while (opModeIsActive()){
            mecanum.update(gamepad1.left_stick_x * 1.1, gamepad1.left_stick_y, gamepad1.right_stick_x, false, false, gamepad1.left_bumper && gamepad1.right_bumper,true,10,10,10,10);        }
    }


}
