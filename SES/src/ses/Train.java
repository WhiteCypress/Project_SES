/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import static java.lang.Math.abs;
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
    double accelerationFlat;
    double accelerationAngle;
    double movTime;
    double vMaxFlat;
    double vMaxAngle;

    public double calculateMaxVeloctiyFlat(double energy, double massTrain) {
        vMaxFlat = Math.abs(sqrt((2 * energy) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat(double energy) {
        distanceFlat = energy/(vMaxFlat/4)*2 ;//not sure

        return distanceFlat;
    }

    public double calculateAccerlation(double vMax, double runTime) {
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateMovTime(double vMax, double acceleration) {

        return acceleration;
    }

    public double calculateMaxVeloctiyAngle(double energy, double massTrain, double angle) {
        vMaxFlat = Math.abs(sqrt((2 * energy) / massTrain));

        double vX = vMaxFlat * cos(angle);
        double vY = vMaxFlat * sin(angle);
        vMaxAngle = Math.abs(sqrt(Math.pow(vX, 2) + Math.pow(vY, 2)));

        return vMaxAngle;
    }
}
