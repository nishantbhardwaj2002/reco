package reco.utils;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nishantbhardwaj2002 on 4/15/17.
 */
@Component
public class LatentDirichletAllocation {

    public static int numberOfTopics = 10;
    private static int numberOfIterations = 1000;

    private ParallelTopicModel model;
    private InstanceList instances;

    public LatentDirichletAllocation() throws IOException {

        final ArrayList pipeList = new ArrayList<Pipe>();

        pipeList.add(new Input2CharSequence());
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence());
        pipeList.add(new TokenSequenceRemoveStopwords());
        pipeList.add(new TokenSequence2FeatureSequence());

        instances = new InstanceList(new SerialPipes(pipeList));
        instances.addThruPipe(new FileIterator("/home/nishantbhardwaj2002/workspace/reco/1/reco/src/reco/resources/bbc"));

        model = new ParallelTopicModel(numberOfTopics);
        model.addInstances(instances);
        model.setNumIterations(numberOfIterations);
        model.estimate();
    }

    public double[] infer(final String testString) {

        final InstanceList testingInstanceList = new InstanceList(instances.getPipe());
        testingInstanceList.addThruPipe(new Instance(testString, null, "test instance", null));

        final TopicInferencer inferencer = model.getInferencer();
        final double[] testProbabilities = inferencer.getSampledDistribution(testingInstanceList.get(0), 10, 1, 5);

        return testProbabilities;
    }
}