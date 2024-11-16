package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class sampleMotors {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public DcMotorEx arm;
    public Servo intakeServo;
    public DcMotor armMotor1;
    public DcMotor armMotor2;
    public Servo testServo;
    public DcMotorEx testMotor;

    boolean intakeOn = false;
    boolean armlockPosition = false;
    boolean prevArmToggle = false;
    boolean prevIntakeOn = false;

    boolean intakePos = false;
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
        intakeServo = hardwareMap.servo.get("intakeServo");
        intakeServo.setPosition(0.0);
        testMotor = (DcMotorEx) hardwareMap.dcMotor.get("testMotor");
        armMotor1 = hardwareMap.dcMotor.get("armMotor1");
        armMotor2 = hardwareMap.dcMotor.get("armMotor2");
        testMotor.setPower(0.0);
        testServo = hardwareMap.servo.get("testServo");



    }

    public void update (boolean intakeOn,boolean testMotorOn,boolean testServoOn,) {

        if(intakeOn && !prevIntakeOn){
                intakePos = !intakePos;
        }
        if (intakePos){
                intakeServo.setPosition(0.694);
        } else {intakeServo.setPosition(0.0);}
        if (testServoOn) {
            testServo.setDirection(Servo.Direction.FORWARD);
        }
        if (testMotorOn){
            testMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            testMotor.setPower(1);
            //testMotor.setVelocity(1);

        }







        //telemetry.addData("arm currentPosition", arm.getCurrentPosition());
        //telemetry.addData();

        telemetry.update();
        prevIntakeOn = intakeOn;


    }
}
