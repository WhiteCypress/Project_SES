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
    double volLiq;          //this thing is in L
    double liquidInitialTemp;
    String materialComb;
    double liquidMass;

    double fireTemp;    //all these are derived values              all values from here to latentVapHeat come from www.engineeringtoolbox.com
    double transferConstant;
    double liquidBoilPoint;
    double liquidSpecificHeat;
    double liquidDensity;           //this is in kg/L
    double latentVapHeat;
    double liquidMolarMass;

    double heatTransferRate;   //all these are calculated values that will be reused
    double power;
    double vapTime;
    double liquidMoles;
    double tempLiquid;
    double pressure;

    double area = 0.01;     //this is the area of the button and it's set to be 50-sqcm by default

    final double SIConvertion = 1000;
    
    public Engine() {

    }

    public Engine(String materialCont, double volCont, double thicCont, String typeLiq, double volLiq, String materialComb) {
        this.materialCont = materialCont;
        this.volCont = volCont;
        this.thicCont = thicCont;
        this.typeLiq = typeLiq;
        this.volLiq = volLiq;
        this.tempLiquid = 25;        //set this to 25 degrees C as a constant
        this.materialComb = materialComb;
        deriveliquidInfo();
        this.liquidMass = calcLiquidMass();
        deriveFireTemp();
        deriveTransferConstant();
        
        calcMoles();
        calcLiquidMass();
        calcHeatTransferRate();
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

    public void deriveFireTemp() {          //using this method will provide the fire temperature of the combustion
        switch (materialComb) {
            case "stove":
                fireTemp = 230;
                break;

            case "natural gas":
                fireTemp = 1960;
                break;

            case "methane":
                fireTemp = 1950;
                break;

            case "hydrogen":
                fireTemp = 2210;
                break;

            case "carbon monoxide":
                fireTemp = 2121;
                break;

            case "wood":
                fireTemp = 600;
                break;

            case "charcoal":
                fireTemp = 1100;
                break;
        }
    }

    public void deriveTransferConstant() {                   //this shows the heatTransferConstant for the container
        switch (materialCont) {
            case "copper":
                transferConstant = 413;
                break;

            case "aluminium":
                transferConstant = 237;
                break;

            case "beryllium":
                transferConstant = 301;
                break;

            case "boron":
                transferConstant = 301;
                break;

            case "cadmium":
                transferConstant = 99.3;
                break;

            case "cesium":
                transferConstant = 36.8;
                break;

            case "chromium":
                transferConstant = 111;
                break;

            case "cobalt":
                transferConstant = 122;
                break;
            
            case "gold":
                transferConstant = 327;

                break;

            case "hafnium":
                transferConstant = 24.4;
                break;

            case "iridium":
                transferConstant = 153;
                break;

            case "iron":
                transferConstant = 94;
                break;

            case "lead":
                transferConstant = 36.6;
                break;

            case "nickel":
                transferConstant = 106;
                break;

            case "platinum":
                transferConstant = 72.4;
                break;

            case "stainless-steel":
                transferConstant = 14.4;
                break;
        }
    }

    public void deriveliquidInfo() {                   //this shows the heatTransferConstant for the container
        switch (typeLiq) {
            case "water":
                liquidBoilPoint = 100;
                liquidSpecificHeat = 4.19 * SIConvertion;         //everything here is in J, L
                latentVapHeat = 2256 * SIConvertion;
                liquidDensity = 1000 / SIConvertion;
                liquidMolarMass = 18.01528 / SIConvertion;
                break;

            case "alcohol":
                liquidBoilPoint = 78.37;
                liquidSpecificHeat = 2.3 * SIConvertion;         //everything here is in J, L
                latentVapHeat = 846 * SIConvertion;
                liquidDensity = 785.1 / SIConvertion;
                liquidMolarMass = 46.07 / SIConvertion;
                break;

            case "ether":
                liquidBoilPoint = 34.6;
                liquidSpecificHeat = 2.21 * SIConvertion;         //everything here is in J, L
                latentVapHeat = 377 * SIConvertion;
                liquidDensity = 713.5 / SIConvertion;
                liquidMolarMass = 74.12 / SIConvertion;
                break;

            case "hexane":
                liquidBoilPoint = 68;
                liquidSpecificHeat = 2.26 * SIConvertion;         //everything here is in J, L
                latentVapHeat = 365 * SIConvertion;
                liquidDensity = 654.8 / SIConvertion;
                liquidMolarMass = 86.18 / SIConvertion;
                break;

            case "gasoline":
                liquidBoilPoint = 35;
                liquidSpecificHeat = 2.22 * SIConvertion;         //everything here is in J, L
                latentVapHeat = 2093.4 * SIConvertion;
                liquidDensity = 711 / SIConvertion;
                liquidMolarMass = 114.232 / SIConvertion;
                break;
        }
    }

    public double calcMoles() {
        liquidMoles = liquidMass / liquidMolarMass;
        return liquidMoles;
    }

    public double calcLiquidMass() {
        this.liquidMass = liquidDensity * volLiq;
        return liquidMass;
    }

    public double calcVapTime() {       //will return how long it takes to vaporize all liquid in container
        vapTime = volLiq * liquidDensity * latentVapHeat / heatTransferRate;

        return vapTime;
    }

    public double calcBoilTime() {       //this gives you the amount of time it takes to boil the liuqid
        calcHeatTransferRate();

        double boilTime = (liquidSpecificHeat * liquidMass * (liquidBoilPoint - tempLiquid)) / heatTransferRate;
        return boilTime;
    }

    public double calcHeatTransferRate() {       //maybe easier to call later
        heatTransferRate = (transferConstant * area * (fireTemp - tempLiquid)) / thicCont;
        return heatTransferRate;
    }

    public double calcTempInCont(double energyTranfered) {       //energyTranfered is calcualted from heatTransferRate*timePast,
        //so, heatTransferRate is in [J/s], heatTransferRate*time = [J] and that will
        //be the final temperature of the liquid after a given time.
        double tempChangeInCont = (energyTranfered) / (liquidMass * liquidSpecificHeat);//it should be calculated each frame
        if (tempLiquid < liquidBoilPoint) {
            tempLiquid = tempLiquid + tempChangeInCont;
        }
        return tempLiquid;            //maybe i should add it automatically, also this is kinda similar to calcBoilTime()
    }

    public double calcPressure() {                   //this returns in pa
        pressure = liquidMoles * 8.3144598 * tempLiquid / volCont;
        return pressure;
    }
    
    public double calcPopOutTime(){
        int time = 0;
        while(calcPressure() < 300){
            time++;
            calcTempInCont(heatTransferRate*time);
        }
        
        return time;
    }

    public double calcPower() {
        power = (liquidMoles * 8.3144598 * liquidBoilPoint / volCont) / liquidDensity * liquidMass;

        return power;
    }

}
