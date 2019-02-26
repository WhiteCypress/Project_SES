/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author zoewong
 */
public class Train {

    private double massTrain;
    private double energy;
    private double angle;
    private double distanceX;
    private double distanceY;
    private double distanceNet;
    private double distanceFlat;
    private double distanceAngle;
    private double accelerationFlat;
    private double accelerationAngle;
    private double movTime;
    private double vMaxFlat;
    private double vMaxAngle;

    public double calculateMaxVeloctiyFlat(double power, double movTime, double massTrain) {
        vMaxFlat = Math.abs(sqrt((2 * movTime * power) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat(double movTime) {
        distanceFlat = vMaxFlat * movTime; //energy/(vMaxFlat/4)*2 not sure m*g*d = KE

        return distanceFlat;
    }

    public double calculateAccerlationFlat(double vMaxFlat, double distanceFlat) {
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateDistanceAngle(double massTrain) {

        return distanceAngle;
    }

    public double calculateMaxVeloctiyAngle(double massTrain) {

        return distanceAngle;
    }

    public double calculateAccerlationAngle(double vMaxFlat, double distanceFlat) {

        return accelerationAngle;
    }

}