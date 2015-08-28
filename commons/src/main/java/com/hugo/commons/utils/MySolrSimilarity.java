package com.hugo.commons.utils;

import org.apache.lucene.search.similarities.DefaultSimilarity;

/**
 * @Author : wwg
 * @Date : 15-1-15 上午9:11.
 */
public class MySolrSimilarity extends DefaultSimilarity {
    @Override  //idf值全部为1.0f
    public float idf(long docFreq, long numDocs) {
        return 1.0F;
    }

    @Override //tf值全部为1.0f
    public float tf(float freq) {
        return 1.0F;
    }

    @Override
    public String toString() {
        return "nlpSimilarity";
    }

}