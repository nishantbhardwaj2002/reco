package reco.utils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
@Component
public class GradientDescent {

    private double alpha = 0.0001;
    private int iters = 1000;

    /**
     * Find gradient using mallet.
     */
    @Deprecated
    public double[] find(final double[][] x, final double[] y) {
        final OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);

        return regression.estimateRegressionParameters();
    }

    /**
     * Custom implementation to find gradient. Uses commons.math for operations.
     */
    public double[] findCustom(final double[][] x, final double[] y) {

        final double[] theta = new double[x[0].length];
        for(int i = 0; i < theta.length; i++) {
            theta[i] = new Random().nextDouble();
        }

        return grad(x, y, theta, alpha, iters);

    }

    public double[] grad(final double[][] x,
                                final double[] y,
                                final double[] theta,
                                final double alpha,
                                final int iters) {

        int m = y.length;

        final RealMatrix xRealMatrix = MatrixUtils.createRealMatrix(x);
        final RealMatrix yRealMatrix = MatrixUtils.createColumnRealMatrix(y);
        RealMatrix thetaRealMatrix = MatrixUtils.createColumnRealMatrix(theta);

        for(int i = 0; i < iters; i++) {
            final RealMatrix xIntoTheta = xRealMatrix.multiply(thetaRealMatrix);
            final RealMatrix xIntoThetaMinusY = xIntoTheta.subtract(yRealMatrix);
            final RealMatrix xTransposeIntoxIntoThetaMinusY = xRealMatrix.transpose().multiply(xIntoThetaMinusY);
            final RealMatrix xTransposeIntoxIntoThetaMinusYIntoAlphaByM = xTransposeIntoxIntoThetaMinusY.scalarMultiply(alpha/m);

            thetaRealMatrix = thetaRealMatrix.subtract(xTransposeIntoxIntoThetaMinusYIntoAlphaByM);
        }

        return thetaRealMatrix.getColumn(0);
    }

    public double cost(double[][] x, double[] y, double[] theta){

        final RealMatrix xRealMatrix = MatrixUtils.createRealMatrix(x);
        final RealMatrix yRealMatrix = MatrixUtils.createColumnRealMatrix(y);
        final RealMatrix thetaRealMatrix = MatrixUtils.createColumnRealMatrix(theta);

        final RealMatrix xIntoTheta = xRealMatrix.multiply(thetaRealMatrix);
        final RealMatrix xIntoThetaMinusY = xIntoTheta.subtract(yRealMatrix);
        final RealMatrix xIntoThetaMinusYSquared = xIntoThetaMinusY.power(2);

        int m = y.length;
        final RealMatrix xIntoThetaMinusYSquaredBy2m = xIntoThetaMinusYSquared.scalarMultiply((double)1/(double)(2 * m));

        double[][] res = xIntoThetaMinusYSquaredBy2m.getData();
        double sum = 0;
        for(int i = 0; i < res.length; i++) {
            for(int j = 0; j < res[i].length; i++) {
                sum += res[i][j];
            }
        }

        return sum;
    }
}
