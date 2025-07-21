package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp
public abstract class ITDControls_OpModeLinear extends LinearOpMode {
    public DcMotor frontLeftDrive;
    public DcMotor backLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backRightDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left");
        backLeftDrive = hardwareMap.get(DcMotor.class,"back_left");
        frontRightDrive = hardwareMap.get(DcMotor.class,"front_right");
        backRightDrive = hardwareMap.get(DcMotor.class,"back_right");
    }


}
