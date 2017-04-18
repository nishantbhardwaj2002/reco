package reco;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by nishantbhardwaj2002 on 4/18/17.
 */
public class testing {

    public static void main(final String[] args) {

        final double[] arr = new double[10];
        Arrays.fill(arr, 1 + (new Random().nextDouble()/10));
        System.out.print(Arrays.toString(arr));
    }
}
