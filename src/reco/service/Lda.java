package reco.service;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Lda {

    private static ParallelTopicModel model;
    private static InstanceList instances;

    public static void main(String[] args) throws IOException {

        Double db = Double.parseDouble("0.124324238723456789234567823456754323456786543234567");
        System.out.println(db);
        double[] arr = {1.2, 2.3, 3.1};
        String cd = Arrays.toString(arr);
        System.out.println(cd);
        String[] arrs  = cd.replaceAll("[\\[\\] ]", "").split(",");
        final Double[] featureVector = new Double[arrs.length];
        for(int i = 0; i < featureVector.length; i++)
            featureVector[i] = Double.parseDouble(arrs[i]);
        for(int i = 0; i < featureVector.length; i++)
        System.out.println(featureVector[i]);


    }

    public static void model() throws IOException {

        // Begin by importing documents from text to feature sequences
        final ArrayList pipeList = new ArrayList<Pipe>();

        // Pipes: char sequence from files, lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new Input2CharSequence());
        pipeList.add( new CharSequenceLowercase());
        pipeList.add( new CharSequence2TokenSequence());
        pipeList.add( new TokenSequenceRemoveStopwords());
        pipeList.add( new TokenSequence2FeatureSequence());
        // pipeList.add( new Target2Label());


        instances = new InstanceList(new SerialPipes(pipeList));
        instances.addThruPipe(new FileIterator("/home/nishantbhardwaj2002/Downloads/bbc-fulltext/bbc", FileIterator.LAST_DIRECTORY));

        final int numTopics = 5;
        model = new ParallelTopicModel(numTopics);
        model.addInstances(instances);
        model.setNumIterations(50);
        model.estimate();

        //for(int i = 0; i < model.tokensPerTopic.length; i++)
        // System.out.println(model.getTopicAlphabet());
    }

    public static double[] infer(final String testString) throws IOException {

        if(model == null) {
            model();
        }
        // Create a new instance named "test instance" with empty target and source fields.
        final InstanceList testing = new InstanceList(instances.getPipe());
        testing.addThruPipe(new Instance(testString, null, "test instance", null));

        final TopicInferencer inferencer = model.getInferencer();
        final double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);

        /*System.out.println("0\t" + testProbabilities[0]);
        System.out.println("1\t" + testProbabilities[1]);
        System.out.println("2\t" + testProbabilities[2]);
        System.out.println("3\t" + testProbabilities[3]);
        System.out.println("4\t" + testProbabilities[4]);*/

        return testProbabilities;
    }
}