/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import static java.lang.Math.sqrt;
/**
 *
 * @author zoewong
 */
public class Train {

    private double massTrain;
    private double energy;
    double angle;
    private double distanceFlat;
    private double distanceX;
    private double distanceY;
    private double distanceOnRamp;
    private double timeTravelledOnRamp;
    private double accelerationFlat;
    private double vAngle;
    private double movTime;
    private double vMaxFlat;
    private double trainCurrentVelocity;
    private double power;

    double maxFlatTime;

    public Train() {

    }

    public Train(double massTrain, double power, double angle, double maxFlatTime) {
        this.massTrain = massTrain;
        this.power = power;
        this.angle = angle / 360 * 2 * 3.1415926;
        this.maxFlatTime = maxFlatTime;
    }

    public void setAngle(double angle) {
        this.angle = angle / 360 * 2 * 3.1415926;           //converts angle from deg to rad
    }

    public void setMovTime(double movTime) {                //sets the value for move time
        this.movTime = movTime;
    }

    public double calculateMaxVeloctiyFlat() {              //calculates the maximum possible velocity for the flat surface
        vMaxFlat = Math.abs(sqrt((2 * maxFlatTime * power) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat() {             //calculates the max distance on flat surface in a set time
        distanceFlat = vMaxFlat / 2 * maxFlatTime;

        return distanceFlat;
    }

    public double calculateAccerlationFlat() {          //calculates the accelaration on flat surface
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateDistanceOnRamp() {          //calculates the max distance on ramp in a set time 
        double maxTimeGoFront = vMaxFlat / (Math.sin(angle) * 9.8);
        distanceOnRamp = vMaxFlat / 2 * maxTimeGoFront;

        return distanceOnRamp;
    }

    public double calculateHeightOnRamp() {         //calculates the max height reached on ramp in a set time
        if (angle == 0) {
            distanceY = 0;
        } else {
            distanceY = calculateDistanceOnRamp() * Math.sin(angle);
        }
        return distanceY;
    }

    public double calculateVelocityAngle(double time) {         //calculates instantaneous velocity on ramp
        vAngle = vMaxFlat - (Math.sin(angle) * 9.8) * time;
        //do not erase
        //vAngle = (vYI-9.8*time)/Math.sin(angle);
        return vAngle;
    }

    public double calculateCurrentPositionOnRamp(double currentTime) {      //calculates instataneous position of train on ramp
        return (vMaxFlat + calculateVelocityAngle(currentTime)) / 2 * currentTime;
    }

}
