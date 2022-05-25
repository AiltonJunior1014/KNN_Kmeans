package knn.kmeans;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class KNN {
    private ArrayList <String[]> readData;
    private ArrayList<Line> BaseLine;
    private ArrayList<Line> TestLines;
    private ArrayList<String> possibleClass;
    private ArrayList <String> Data;
    private int k;
    private String path_base;
    private String path_test;
    private int[][] ConfusionMatrix;
    private BufferedReader br = null;
    
    public KNN(int k, String path_base, String path_test) {
        this.BaseLine = new ArrayList<Line>();
        this.TestLines = new ArrayList<Line>();

        this.possibleClass = new ArrayList<String>();
        this.readData = new ArrayList<String[]>();
        this.k = k;
        this.path_base = path_base;
        this.path_test = path_test;
    }
    
    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getPathBase() {
        return path_base;
    }

    public void setPathBase(String path) {
        this.path_base = path;
    }
    
    public ArrayList<String[]> getReadData() {
        return readData;
    }

    public void setReadData(ArrayList<String[]> readData) {
        this.readData = readData;
    }
       

    public void lerArquivo_base() throws IOException{
        String line = "";
        String csvDivisor = ",";
        int aux=0;
        String arqBaseCSV = this.path_base;
        br = new BufferedReader(new FileReader(arqBaseCSV));
        line = br.readLine();
        while (line != null) {
            String[] data = line.split(csvDivisor);
            this.Data = new ArrayList<String>();
            for (int i = 0; i < data.length-1; i++) {
                Data.add(data[i]);
            }
            BaseLine.add(new Line(data[data.length-1],Data,0));   
            line = br.readLine();            
        }        
        br.close();

         
        String testArqCSV = this.path_test;
        line = "";
        br = new BufferedReader(new FileReader(testArqCSV));
        line = br.readLine();
        while (line != null) {
            String[] data = line.split(csvDivisor);
            this.Data = new ArrayList<String>();
            for (int i = 0; i < data.length-1; i++) {
                Data.add(data[i]);
            }
            TestLines.add(new Line(data[data.length-1],Data,0));   
            line = br.readLine();
        }        
        br.close();
        setpossibleclass();
    }

    public void setpossibleclass(){
        this.possibleClass = new ArrayList<String>();
        for(int i = 0; i < this.BaseLine.size(); i++){
            if(!this.possibleClass.contains(this.BaseLine.get(i).get_Class())){
                this.possibleClass.add(this.BaseLine.get(i).get_Class());
            }
        }
    }
    
    public void calcdist(){
        ArrayList<Line> bigger = null;
        ArrayList<String> predicted = null;
        
        Line auxLine;
        int auxi = 0, positionpredicted = 0;
        for (Line i : TestLines) {
            for (Line j : BaseLine) {
                j.calc_distance(i.getData());
            }
            Line j = BaseLine.get(0);
            bigger = new ArrayList<Line>();
            bigger.add(j);
            for (int k =1; k < this.BaseLine.size(); k++){
                j = BaseLine.get(k);
                if(j.getDistance() < bigger.get(bigger.size()-1).getDistance()){
                    if(bigger.size() < k){
                        bigger.add(j);
                    }
                    else{
                        bigger.add(bigger.size()-1,j);
                    }
                }
                Collections.sort(bigger);
            
            }
             int[] aux = new int[this.possibleClass.size()];
             for (int x = 0; x < this.k && x < bigger.size(); x++) {
                 auxLine = bigger.get(x);
                 positionpredicted = this.possibleClass.indexOf(auxLine.get_Class());
                 aux[positionpredicted]++;
            }
            int big=0, pos =0;
            for(int x=0; x < aux.length;x++){
                if(aux[x]>big){
                    big = aux[x];
                    pos = x;
                }
            }

            i.setPredicted(this.possibleClass.get(pos));
            System.out.println("Predicted: "+i.getPredicted()+"| Correct: "+i.get_Class());
        }
        createConfusionMatrix();
    }
    public int[] countAccuracy(int Size, int posClass) {
        int[] aux = new int[Size];
        for(int i=0; i<this.TestLines.size(); i++){
            if(this.TestLines.get(i).get_Class().equalsIgnoreCase(this.possibleClass.get(posClass))){
                aux[this.possibleClass.indexOf(this.TestLines.get(i).getPredicted())]++;
            }
            if(this.TestLines.get(i).getPredicted().equalsIgnoreCase(this.possibleClass.get(posClass)) 
            && !this.TestLines.get(i).get_Class().equalsIgnoreCase(this.possibleClass.get(posClass))){
                aux[this.possibleClass.indexOf(this.TestLines.get(i).get_Class())]++;
            }
        }
        return aux; 
    }

    public void createConfusionMatrix(){
        int Size = this.possibleClass.size();
        this.ConfusionMatrix = new int[Size][Size];
        for(int i=0; i<this.possibleClass.size(); i++){
            this.ConfusionMatrix[i] = countAccuracy(Size, i);
        }
        for(int i=0;i<Size;i++){
            for(int j=0;j<Size;j++){
                System.out.print(this.ConfusionMatrix[i][j]+" | ");        
            }
            System.out.println();
        }
        
    }
}
