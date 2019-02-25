/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ses;

/**
 *
 * @author zoewong
 */
public class Engine {
    String materialCont;     //all these are values entered by users
    double volCont;
    double thicCont;
    String typeLiq;
    double volLiq;
    double vapTime;
    double energy;
    double liquidInitialTemp;
    
    double fireTemp;    //all these are derived values
    double transferConstant;
    double liquidBoilPoint;
    double liquidSpecificHeat;
    double liquidMass;
    double liquidDensity;
    double latentVapHeat;
    
    double heatTransferRate;   //all these are calculated values that will be reused
    
    double area;     //this is the area of the button and it's set to be 1sqmeter by default
    
    public Engine(){
        
    }

    public String getMaterialCont() {
        return materialCont;
    }

    public void setMaterialCont(String materialCont) {
        this.materialCont = materialCont;
    }

    public double getVolCont() {
        return volCont;
    }

    public void setVolCont(double volCont) {
        this.volCont = volCont;
    }

    public double getThicCont() {
        return thicCont;
    }

    public void setThicCont(double thicCont) {
        this.thicCont = thicCont;
    }

    public String getTypeLiq() {
        return typeLiq;
    }

    public void setTypeLiq(String typeLiq) {
        this.typeLiq = typeLiq;
    }

    public double getVolLiq() {
        return volLiq;
    }

    public void setVolLiq(double volLiq) {
        this.volLiq = volLiq;
    }

    public double calcVapTime() {       //will return how long it takes to vaporize all liquid in container
        vapTime = volLiq*liquidDensity*latentVapHeat/heatTransferRate;
        
        return vapTime;
    }
    
    public double calcBoilTime(){       //this gives you the amount of time it takes to boil the liuqid
        heatTransferRate = (transferConstant * area * (fireTemp - liquidInitialTemp))/thicCont;
        
        double boilTime = (liquidSpecificHeat * liquidMass * (liquidBoilPoint - liquidInitialTemp))/heatTransferRate;
        return boilTime;
    }
    
    public double calcHeatTransferRate(){       //maybe easier to call later
        heatTransferRate = (transferConstant * area * (fireTemp - liquidInitialTemp))/thicCont;
        return heatTransferRate;
    }
    
    public double calcTempInContChange(double energyTranfered){       //energyTranfered is calcualted from heatTransferRate*timePast,
                                                                        //so, heatTransferRate is in [J/s], heatTransferRate*time = [J] and that will
                                                                        //be the final temperature of the liquid after a given time.
        double tempChangeInCont = (energyTranfered)/(liquidMass*liquidSpecificHeat);//it should be calculated each frame
        
        return tempChangeInCont;            //maybe i should add it automatically, also this is kinda similar to calcBoilTime()
    }

    public double calcEnergy() {
        energy = 0;                              //THis is not what it is
        
        return energy;
    }
    
}
