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
    private double angle;
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

    double maxFLatTime;

    public Train(double massTrain, double power, double angle, double maxFlatTime) {
        this.massTrain = massTrain;
        this.power = power;
        this.angle = angle;
        this.maxFLatTime = maxFlatTime;
    }

    public void setMovTime(double movTime) {
        this.movTime = movTime;
    }

    public double calculateMaxVeloctiyFlat() {
        vMaxFlat = Math.abs(sqrt((2 * movTime * power) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat() {
        distanceFlat = vMaxFlat / 2 * movTime; //energy/(vMaxFlat/4)*2 not sure m*g*d = KE

        return distanceFlat;
    }

    public double calculateAccerlationFlat() {
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateDistanceOnRamp() {
        //distanceY = Math.pow(vMaxFlat * Math.sin(angle), 2) / (2 * 9.81);
        distanceY = vMaxFlat * Math.sin(angle) * (movTime - maxFLatTime) + 0.5 * (-9.8) * (movTime - maxFLatTime) * (movTime - maxFLatTime);
        timeTravelledOnRamp = (vMaxFlat * Math.sin(angle)) / 9.81;
        distanceX = vMaxFlat * Math.cos(angle) * timeTravelledOnRamp;
        distanceOnRamp = Math.sqrt((Math.pow(distanceX, 2)) + (Math.pow(distanceY, 2)));

        return distanceOnRamp;
    }

    public double calculateHeightOnRamp() {
        distanceY = Math.pow(vMaxFlat * Math.sin(angle), 2) / (2 * 9.81);

        return distanceY;
    }

    public double calculateVelocityAngle(double time) {
        vAngle = Math.sqrt(Math.pow(Math.cos(angle) * vMaxFlat, 2) + Math.pow(Math.sin(angle) * vMaxFlat * time / (Math.pow(10, 9)), 2));

        return vAngle;
    }

}
