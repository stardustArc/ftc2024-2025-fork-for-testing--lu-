package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

public class Directions {
    int operation;

    int inches;

    Directions(int operation, int inches) {
        this.operation = operation;
        this.inches = inches;
    }

    public void setOperation(int o) {
        this.operation = o;

    }

    public void setInches(int i) {
        this.inches = i;
    }

    public int getOperation() {
        return this.operation;
    }

    public int getInches() {
        return this.inches;
    }
    @NonNull

    private String numToOp(){
        if (this.operation==0){
            return("forward");
        }else if (this.operation == 1){
            return("backwards");
        }else if (this.operation == 2){
            return("left?");
        }else if (this.operation == 3){
            return("right?");
        }else if (this.operation == 4){
            return("clockwise?");
        }else if (this.operation == 5){
            return("counterclockwise?");
        }else{
            return("ERROR");
        }
    }
    @NonNull
    @Override
    public String toString() {
       return("Current Operation: \nMoving "+this.numToOp()+" " + this.inches);
    }
}

