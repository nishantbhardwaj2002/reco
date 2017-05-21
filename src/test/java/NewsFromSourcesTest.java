package test.java;

import org.testng.annotations.Test;
import reco.model.newsFromSource.NewsFromSourceModel;
import reco.model.newsFromSource.Result;
import reco.utils.NewsFromSources;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by nishantbhardwaj2002 on 21/5/17.
 */
public class NewsFromSourcesTest {

    @Test
    public void testGet() throws Exception {
        final NewsFromSources nfs = new NewsFromSources();
        assertNotNull(nfs);
        assertNull(nfs.get(0));
        assertNotNull(nfs.get(1));
        assertNotNull(nfs.get(2));
    }

    @Test
    public void testUnmarshalling() throws Exception {
        final NewsFromSources nfs = new NewsFromSources();
        assertNotNull(nfs);
        final NewsFromSourceModel nfsModel = nfs.unmarshalling(nfs.get(1));
        assertNotNull(nfsModel);
        final List<Result> newsList = nfsModel.getResponse().getResults();
        assertNotNull(newsList);
    }

}