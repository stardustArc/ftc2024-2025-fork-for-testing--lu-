package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.CRServo;
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
    public CRServo intakeServo2;
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

    public double pulsesPerRev0019 = 537.7;

    private int elbowTimerStart = 50;


    private static double armLockLow = 0.76;
    private static double armLockHigh = 1.0;



    public sampleMotors (HardwareMap map, Telemetry telem) {
        telemetry = telem;
        hardwareMap = map;
    }



    public void begin () {
        //This is where the servos and motors are and commands for them
        intakeServo = hardwareMap.servo.get("intakeServo");//113 degrees
        intakeServo.setPosition(0.0);
        intakeServo2 = hardwareMap.crservo.get("intakeServo2");
        testMotor = (DcMotorEx) hardwareMap.dcMotor.get("perp");
        armMotor1 = hardwareMap.dcMotor.get("armMotor1");
        armMotor2 = hardwareMap.dcMotor.get("armMotor2");
        testMotor.setPower(0.0);
        testServo = hardwareMap.servo.get("testServo");
        armMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

    public void update (boolean intakeOn,boolean testMotorOn,boolean testServoOn,double infiniteArmMotorPower,double limitedArmMotorPower,double intakeMovingServo) {

        if(intakeOn && !prevIntakeOn){
                intakePos = !intakePos;
        }
        if (intakePos){
            //intakeServo.setPosition(0.628);
            intakeServo.setPosition(0.1);

        } else {intakeServo.setPosition(0.0);}
        telemetry.addData("position",intakeServo.getPosition());

        if (testServoOn) {
            testServo.setDirection(Servo.Direction.FORWARD);

        }
        if (testMotorOn){
            testMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            testMotor.setPower(1);
            //testMotor.setVelocity(1);

        }
        armMotor1.setPower(infiniteArmMotorPower/2);
        double limit = pulsesPerRev0019 *4;//origional: 4.2
        if(
                (armMotor2.getCurrentPosition()<limit && armMotor2.getCurrentPosition() >0) ||
                        ((armMotor2.getCurrentPosition() >= limit && limitedArmMotorPower>=0) ||
                                (armMotor2.getCurrentPosition() <= 0 && limitedArmMotorPower <= 0))){
            armMotor2.setPower(limitedArmMotorPower/2);

        }
        telemetry.addData("armMotor2",armMotor2.getCurrentPosition());
        telemetry.addData("revs",armMotor2.getCurrentPosition()/pulsesPerRev0019);
        telemetry.addData("limit",limit);
        telemetry.addData("input",limitedArmMotorPower);

        intakeServo2.setPower(intakeMovingServo);








        //telemetry.addData("arm currentPosition", arm.getCurrentPosition());
        //telemetry.addData();

        telemetry.update();
        prevIntakeOn = intakeOn;


    }
}
