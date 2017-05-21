package test.java;

import org.testng.annotations.Test;
import reco.utils.LatentDirichletAllocation;

import static org.testng.Assert.*;

/**
 * Created by nishantbhardwaj2002 on 21/5/17.
 */
public class LatentDirichletAllocationTest {

    @Test
    public void testInfer() throws Exception {
        final LatentDirichletAllocation lda = new LatentDirichletAllocation();
        assertNotNull(lda);
        final double[] probabilities = lda.infer("play player player playing sport sports sportsmen sportsmen football cricket tennis");
        assertNotNull(probabilities);
        assertEquals(probabilities.length, LatentDirichletAllocation.numberOfTopics);
    }

}