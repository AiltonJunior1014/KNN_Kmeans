package knn.kmeans;

import java.io.IOException;

public class KNNKmeans {

    public static void main(String[] args) throws IOException {
        KNN knn;
        int k=3;
        String path_base, path_test;
        path_base = "C:\\IA\\base_teste.csv";
        path_test = "C:\\IA\\base_treinamento.csv";

        System.out.println("Base: " + path_base+"\nTest: "+path_test);

        knn = new KNN(k, path_base, path_test);
        try{
            knn.lerArquivo_base();
            knn.calcdist();
            //knn.print_tests();
        }
        catch(Exception e){
            System.out.println("Deu bosta"+e);
        }
        
        
    }
    
}
