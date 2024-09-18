package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class sampleMotors {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public DcMotorEx arm;



    boolean armlockPosition = false;
    boolean prevArmToggle = false;


    public Servo armlock;



    private int elbowTimerStart = 50;


    private static double armLockLow = 0.76;
    private static double armLockHigh = 1.0;



    public sampleMotors (HardwareMap map, Telemetry telem) {
        telemetry = telem;
        hardwareMap = map;
    }



    public void begin () {
        //This is where the servos and motors are and commands for them




    }
    public void update(float armPower, boolean armToggle,boolean TRUE){
        this.update(armPower, armToggle);
    }
    public void update (float armPower, boolean armToggle) {


        arm.setPower(armPower);
        if(armToggle && !prevArmToggle){armlockPosition=!armlockPosition;}
        if (armlockPosition){
            armlock.setPosition(armLockHigh);
        }
        else {armlock.setPosition(armLockLow);}







        telemetry.addData("arm currentPosition", arm.getCurrentPosition());

        telemetry.update();
        prevArmToggle = armToggle;

    }
}
