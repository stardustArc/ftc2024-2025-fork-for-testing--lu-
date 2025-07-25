package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

enum Operation {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT,
    CLOCKWISE,
    COUNTERCLOCKWISE
}

public class Directions {
    Operation operation;

    int inches;

    Directions(Operation operation, int inches) {
        this.operation = operation;
        this.inches = inches;
    }

    public void setOperation(Operation o) {
        this.operation = o;

    }

    public void setInches(int i) {
        this.inches = i;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public int getInches() {
        return this.inches;
    }
    @NonNull

    private String numToOp(){
        return this.operation.name();
    }

    @NonNull
    @Override
    public String toString() {
       return("Current Operation: \nMoving "+this.numToOp()+" " + this.inches);
    }
}

