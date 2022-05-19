package knn.kmeans;

import static java.lang.Math.abs;
import java.util.ArrayList;


public class Line implements Comparable<Line> {
    private String Class;
    private ArrayList <Double> Data;
    private double distance;
    private String Predicted;
    



    public String get_Class() {
        return Class;
    }


    public void setClass(String Class) {
        this.Class = Class;
    }

    public ArrayList<Double> getData() {
        return Data;
    }

    public void setData(ArrayList<Double> Data) {
        this.Data = Data;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Line(String Class, ArrayList<String> Data, double distance) {
        this.Class = Class;
        this.Data = new ArrayList<Double>();
        for (String i : Data) {
            this.Data.add(Double.parseDouble(i));
        }
        this.distance = distance;
    }
    
    public void calc_distance(ArrayList<Double> dataaux){
        double distanceaux =0;
        for (int i = 0; i < dataaux.size(); i++) {
            distanceaux += abs(dataaux.get(i)-this.Data.get(i));
        }
        this.distance = distanceaux;        
    }

    public String getPredicted(){
        return this.Predicted;
    }

    public void setPredicted(String predicted){
        this.Predicted = predicted;
    }
    
    @Override
    public int compareTo(Line a){
        return (int) (this.distance - a.getDistance());
    }
    
    
}
