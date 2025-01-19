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
    public DcMotor hangTilter;
    public DcMotor hangMain;
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
       hangTilter = hardwareMap.dcMotor.get("hang_motor");
       hangMain = hardwareMap.dcMotor.get("hang_main");
        vertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangTilter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangMain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangTilter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertical.setTargetPosition(0);
        hangTilter.setTargetPosition(0);
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);



    }




    public void update (double limitedArmMotorPower, boolean hangTilt,double horizontalPowewr,boolean pos1, boolean pos2, boolean pos3,boolean hangRise,double verticalTest) {
        
        double limit = pulsesPerRev0019 *1;//origional: 4.2

        if(hangTilt && !prevHangChange){
            hangChange = !hangChange;
        }
        if(hangRise && !prevHangMain){
            hangPos = !hangPos;
        }

        if (!hangChange){
            
            hangTilter.setTargetPosition(0);
        }else{
            hangTilter.setTargetPosition(700);
        }
        hangTilter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangTilter.setPower(0.9);
        if (hangPos){
            hangMain.setTargetPosition(-13500);
        }else{
            hangMain.setTargetPosition(0);
        }
        hangMain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangMain.setPower(0.9);

        if (pos1){
            vertical.setTargetPosition(0);
        }
        if (pos2){
            vertical.setTargetPosition(-470);

        }
        if (pos3){
            vertical.setTargetPosition(-1867);
        }
        vertical.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        vertical.setPower(1);

        if(
                (-horizontal.getCurrentPosition()<limit && -horizontal.getCurrentPosition() >0) ||
                        ((-horizontal.getCurrentPosition() >= limit && horizontalPowewr<=0) ||
                                (-horizontal.getCurrentPosition() <= 0 && horizontalPowewr >= 0))){
            horizontal.setPower(horizontalPowewr/2);

        }
        
        prevHangChange = hangTilt;
        prevHangMain = hangRise;
        telemetry.update();

    }
}
