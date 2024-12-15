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
    int freezeCount = 0;
    int armHoldPose;
    boolean prevFreeze = false;
    boolean freezing = false;
    boolean prevIntakeOn = false;
    int currentArmPosition;
    boolean intakePos = false;
    public Servo armlock;

    public double pulsesPerRev0019 = 537.7;





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
        armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }




    public void update (boolean intakeOn,boolean testMotorOn,boolean testServoOn,double infiniteArmMotorPower,double limitedArmMotorPower,double intakeMovingServo,boolean freezeLift) {
        limitedArmMotorPower =-limitedArmMotorPower;


        if(intakeOn && !prevIntakeOn){
                intakePos = !intakePos;
        }
        if (intakePos){
            intakeServo.setPosition(0.628);
            //intakeServo.setPosition(0.1);

        } else if (!intakePos){intakeServo.setPosition(0.0);}
        telemetry.addData("position",intakeServo.getPosition());
        telemetry.addData("intakePos",intakePos);
        telemetry.addData("intakeOn",intakeOn);
        telemetry.addData("prevIntakeOn",prevIntakeOn);
        if (freezeLift && !prevFreeze){
            armHoldPose = armMotor1.getCurrentPosition();
            freezing = !freezing;
        }
        if (freezing){
            armMotor1.setTargetPosition(armHoldPose);
            armMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }else{
            armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        if (testServoOn) {
            testServo.setDirection(Servo.Direction.FORWARD);

        }
        if (testMotorOn){
            testMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            testMotor.setPower(1);


        }
        armMotor1.setPower(infiniteArmMotorPower);

        /*if (infiniteArmMotorPower != 0){
            armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            currentArmPosition= armMotor1.getCurrentPosition();

        }else{
            armMotor1.setTargetPosition(currentArmPosition);
            armMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }*/
        double limit = pulsesPerRev0019 *4;//origional: 4.2
        /*if(limitedArmMotorPower <= 0 && armMotor2.getCurrentPosition() <= (limit * -1)){
            telemetry.addLine("if 1");
            armMotor2.setPower(limitedArmMotorPower/2);
        } else if(limitedArmMotorPower >= 0 && armMotor2.getCurrentPosition() >= 0) {
            telemetry.addLine("if 2");
            armMotor2.setPower(limitedArmMotorPower / 2);
        } else {
            telemetry.addLine("else");
            armMotor2.setPower(0);
        }*/
        if(
                (-armMotor2.getCurrentPosition()<limit && -armMotor2.getCurrentPosition() >0) ||
                        ((-armMotor2.getCurrentPosition() >= limit && limitedArmMotorPower<=0) ||
                                (-armMotor2.getCurrentPosition() <= 0 && limitedArmMotorPower >= 0))){
            armMotor2.setPower(limitedArmMotorPower/2);

        }
        /*if(
                (armMotor2.getCurrentPosition()>-limit && armMotor2.getCurrentPosition() <0) ||
                        ((armMotor2.getCurrentPosition() <= -limit && limitedArmMotorPower<=0) ||
                                (armMotor2.getCurrentPosition() >= 0 && limitedArmMotorPower >= 0))){
            armMotor2.setPower(limitedArmMotorPower/2);

        }*/
        intakeServo2.setPower(intakeMovingServo);
        telemetry.addData("armMotor2",armMotor2.getCurrentPosition());
        telemetry.addData("revs",armMotor2.getCurrentPosition()/pulsesPerRev0019);
        telemetry.addData("limit",limit);
        telemetry.addData("input",limitedArmMotorPower);



        telemetry.update();
        prevIntakeOn = intakeOn;


    }
}
