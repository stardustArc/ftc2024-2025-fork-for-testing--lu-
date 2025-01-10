package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class sampleMotors2 {
    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    
    public DcMotor horizontal;
    public DcMotor vertical;
   
    public DcMotor hangMotor;
    public DcMotor hangMain;
    int freezeCount = 0;
    

    public double pulsesPerRev0019 = 537.7;

    boolean prevHangMain =  false;
    boolean hangPos = false;
    boolean prevHangChange = false;
    boolean hangChange = false;


    public sampleMotors2(HardwareMap map, Telemetry telem) {
        telemetry = telem;
        hardwareMap = map;
    }



    public void begin () {
        //This is where the servos and motors are and commands for them
        horizontal = hardwareMap.dcMotor.get("horizontal");
       vertical = hardwareMap.dcMotor.get("vertical");
       hangMotor = hardwareMap.dcMotor.get("hang_motor");
       hangMain = hardwareMap.dcMotor.get("hang_main");
        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setTargetPosition(0);
        hangMotor.setTargetPosition(0);



    }




    public void update (double limitedArmMotorPower, boolean hangOn,double horizontalPowewr,boolean pos1, boolean pos2, boolean pos3,boolean hangmain) {
        limitedArmMotorPower =-limitedArmMotorPower;
        double hangMovement = 5/360 * pulsesPerRev0019;

        if(hangOn && !prevHangChange){
            hangChange = !hangChange;
        }
        if(hangmain && !prevHangMain){
            hangPos = !hangPos;
        }
        
        /*telemetry.addData("position",intakeServo.getPosition());
        telemetry.addData("intakePos",intakePos);
        telemetry.addData("intakeOn",intakeOn);
        telemetry.addData("prevIntakeOn",prevIntakeOn);*/


        if (hangChange){
            //hangMotor.setTargetPosition((int) Math.round(hangMovement));
            hangMotor.setTargetPosition(100);
        }else{
            hangMotor.setTargetPosition(0);
        }
        hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangMotor.setPower(0.9);
        if (hangPos){
            hangMain.setTargetPosition(-(int) Math.round(pulsesPerRev0019));
        }else{
            hangMain.setTargetPosition(0);
        }
        hangMain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangMain.setPower(0.9);




        

        /*if (infiniteArmMotorPower != 0){
            armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            currentArmPosition= armMotor1.getCurrentPosition();

        }else{
            armMotor1.setTargetPosition(currentArmPosition);
            armMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }*/
        double limit = pulsesPerRev0019 *1;//origional: 4.2
        /*if(limitedArmMotorPower <= 0 && vertical.getCurrentPosition() <= (limit * -1)){
            telemetry.addLine("if 1");
            vertical.setPower(limitedArmMotorPower/2);
        } else if(limitedArmMotorPower >= 0 && vertical.getCurrentPosition() >= 0) {
            telemetry.addLine("if 2");
            vertical.setPower(limitedArmMotorPower / 2);
        } else {
            telemetry.addLine("else");
            vertical.setPower(0);
        }*/
        /*if(
                (-vertical.getCurrentPosition()<limit && -vertical.getCurrentPosition() >0) ||
                        ((-vertical.getCurrentPosition() >= limit && limitedArmMotorPower<=0) ||
                                (-vertical.getCurrentPosition() <= 0 && limitedArmMotorPower >= 0))){
            vertical.setPower(limitedArmMotorPower/2);

        }*/
        if (pos1){
            vertical.setTargetPosition(0);
        }
        if (pos2){
            vertical.setTargetPosition(-(int) Math.round(limit));

        }
        if (pos3){
            vertical.setTargetPosition(-(int) Math.round(limit*2));
        }

        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertical.setPower(0.9);

        if(
                (-horizontal.getCurrentPosition()<limit && -horizontal.getCurrentPosition() >0) ||
                        ((-horizontal.getCurrentPosition() >= limit && horizontalPowewr<=0) ||
                                (-horizontal.getCurrentPosition() <= 0 && horizontalPowewr >= 0))){
            horizontal.setPower(horizontalPowewr/2);

        }
        /*if(
                (vertical.getCurrentPosition()>-limit && vertical.getCurrentPosition() <0) ||
                        ((vertical.getCurrentPosition() <= -limit && limitedArmMotorPower<=0) ||
                                (vertical.getCurrentPosition() >= 0 && limitedArmMotorPower >= 0))){
            vertical.setPower(limitedArmMotorPower/2);

        }*/
        telemetry.addData("hang movment",Math.round(hangMovement));
        telemetry.addData("hang",hangMotor.getCurrentPosition());
        telemetry.addData("hangTarget",hangMotor.getTargetPosition());
        telemetry.addData("hnag on",hangChange);

        telemetry.addData("vertical",vertical.getCurrentPosition());
        telemetry.addData("revs",vertical.getCurrentPosition()/pulsesPerRev0019);
        telemetry.addData("limit",limit);
        telemetry.addData("input",limitedArmMotorPower);



        prevHangChange = hangOn;
        prevHangMain = hangmain;
        telemetry.update();



    }
}
