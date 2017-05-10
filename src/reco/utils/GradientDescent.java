package reco.utils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
@Component
public class GradientDescent {

    private double alpha = 0.00001;
    private int iters = 1000000;

    /**
     * Custom implementation to find gradient. Uses commons.math for operations.
     */
    public double[] find(final double[][] x, final double[] y) {

        final double[] theta = new double[x[0].length];
        for(int i = 0; i < theta.length; i++) {
            theta[i] = new Random().nextDouble();
        }

        return grad(x, y, theta);

    }

    private double[] grad(final double[][] x,
                          final double[] y,
                          final double[] theta) {

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

    private double cost(final double[][] x,
                        final double[] y,
                        final double[] theta) {

        final RealMatrix xRealMatrix = MatrixUtils.createRealMatrix(x);
        final RealMatrix yRealMatrix = MatrixUtils.createColumnRealMatrix(y);
        final RealMatrix thetaRealMatrix = MatrixUtils.createColumnRealMatrix(theta);

        final RealMatrix xIntoTheta = xRealMatrix.multiply(thetaRealMatrix);
        final RealMatrix xIntoThetaMinusY = xIntoTheta.subtract(yRealMatrix);
        final RealMatrix xIntoThetaMinusYSquared = xIntoThetaMinusY.power(2);

        final int m = y.length;
        final RealMatrix xIntoThetaMinusYSquaredBy2m = xIntoThetaMinusYSquared.scalarMultiply((double)1/(double)(2 * m));

        final double[][] res = xIntoThetaMinusYSquaredBy2m.getData();
        double sum = 0;
        for(int i = 0; i < res.length; i++) {
            for(int j = 0; j < res[i].length; i++) {
                sum += res[i][j];
            }
        }

        return sum;
    }
}
