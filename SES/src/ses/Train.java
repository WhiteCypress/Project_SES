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

    double massTrain;
    double energy;
    double runTime;
    double angle;
    double distanceX;
    double distanceY;
    double distanceNet;
    double distanceFlat;
    double distanceAngle;
    double accelerationFlat;
    double accelerationAngle;
    double movTimeFlat;
    double movTimeAngle;    
    double vMaxFlat;
    double vMaxAngle;

    public double calculateEnergy(double power, double runTime) {
        energy = power * runTime;

        return energy;
    }

    public double calculateMaxVeloctiyFlat(double energy, double massTrain) {
        vMaxFlat = Math.abs(sqrt((2 * energy) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat(double energy, double massTrain) {
        distanceFlat = energy / (massTrain * 9.81); //energy/(vMaxFlat/4)*2 not sure m*g*d = KE

        return distanceFlat;
    }

    public double calculateAccerlationFlat(double vMaxFlat, double distanceFlat) {
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateMovTimeFlat(double vMaxFlat, double acceleration) {
        movTimeFlat = vMaxFlat / accelerationFlat;

        return movTimeFlat;
    }

    public double calculateDistanceAngle(double energy, double massTrain) {
        
        return distanceAngle;
    }

    public double calculateAccerlationAngle(double vMaxFlat, double distanceFlat) {

        return accelerationAngle;
    }

    public double calculateMovTimeAngle(double vMaxFlat, double acceleration) {

        return movTimeAngle;
    }
}
