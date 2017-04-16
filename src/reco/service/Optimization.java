package reco.service;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.Arrays;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
public class Optimization {

    public static void main(String[] args) {


        double[] y = new double[]{1, 1, 1, 1, 1};
        double[][] x = new double[5][];
        x[0] = new double[]{1-0.0000001, 0.0000001};
        x[1] = new double[]{1-0.0000002, 0.0000002};
        x[2] = new double[]{1, 0};
        x[3] = new double[]{1-0.00000011, 0.00000011};
        x[4] = new double[]{1-0.0000006, 0.0000006};

        /*

        double x[][] = {
                {3000,100}, {6000,512},{10000,1024},
                {10000,2048},{20000,2048},{20000,3072},
                {30000,1024},{30000,2048},{30000,3072},
                {45000,1024},{45000,2048},{45000,3072},
                {45000,4098},{60000,1024},{60000,2048},
                {60000,3072},{60000,4098}};
        double[] y = {100, 85, 70, 67,
                65, 60, 66, 60,
                55, 70, 65, 60,
                55, 67, 60, 50, 40};

         */
        reg(x, y);
    }

    public static double[] reg(double[][] x, double[] y) {
        System.out.println("a");
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);
        System.out.println("b");
        System.out.print(Arrays.toString(regression.estimateRegressionParameters()));
        System.out.println("yo boy done insizeede opti.reg");
        return regression.estimateRegressionParameters();
    }

}
