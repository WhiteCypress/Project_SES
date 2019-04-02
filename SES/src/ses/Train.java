/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

import static java.lang.Math.sqrt;
import javafx.scene.shape.Rectangle;

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

    double maxFlatTime;
    
    public Train(){
        
    }

    public Train(double massTrain, double power, double angle, double maxFlatTime) {
        this.massTrain = massTrain;
        this.power = power;
        this.angle = angle/360*2*3.1415926;
        this.maxFlatTime = maxFlatTime;
    }

    public void setMovTime(double movTime) {                //set the value for move time
        this.movTime = movTime;
    }

    public double calculateMaxVeloctiyFlat() {              //calculate the maximum possible value for the flat surface
        vMaxFlat = Math.abs(sqrt((2 * maxFlatTime * power) / massTrain));

        return vMaxFlat;
    }

    public double calculateDistanceFlat() {             //calculate the max distant on distance in a set time
        distanceFlat = vMaxFlat / 2 * maxFlatTime; //energy/(vMaxFlat/4)*2 not sure m*g*d = KE

        return distanceFlat;
    }

    public double calculateAccerlationFlat() {          //calculate the accelaration
        accelerationFlat = (Math.pow(vMaxFlat, 2)) / (2 * distanceFlat);

        return accelerationFlat;
    }

    public double calculateDistanceOnRamp() {
        //double Vfinal = vMaxFlat-(Math.cos(0.5*3.1415926-angle))*9.8*maxFlatTime;
        double maxTimeGoFront = vMaxFlat/(Math.cos(0.5*3.1415926-angle)*9.8);
        distanceOnRamp = vMaxFlat/2*maxTimeGoFront;
        //distanceOnRamp = (vMaxFlat+Vfinal)/2*maxFlatTime;
        
        return distanceOnRamp;
    }

    public double calculateHeightOnRamp() {
        distanceY = Math.pow(vMaxFlat * Math.sin(angle), 2) / (2 * 9.81);
        
        return distanceY;
    }

    public double calculateVelocityAngle(double time) {
//        vAngle = Math.sqrt(Math.pow(Math.cos(angle) * vMaxFlat, 2) + Math.pow(Math.sin(angle) * vMaxFlat * time / (Math.pow(10, 9)), 2));
//        double vYI = Math.sin(angle)*vMaxFlat;
        vAngle = vMaxFlat-(Math.cos(angle)*9.8)*time;
        //do not erase
        //vAngle = (vYI-9.8*time)/Math.sin(angle);
        return vAngle;
    }
    
    public double calculateCurrentPositionOnRamp(double currentTime){
        return vMaxFlat*currentTime - 1/2*(Math.cos(angle)*9.8)*currentTime;
    }
    
}
