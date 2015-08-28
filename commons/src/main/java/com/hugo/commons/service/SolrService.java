package com.hugo.commons.service;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : wwg
 * @Date : 14-11-27 上午10:32
 */
public class SolrService {

    public static void main(String args[]) {
        SolrService solrService = new SolrService();
        solrService.testGeneralLabel();
//        solrService.testIdentity("w四");
    }


    public void testGeneralLabel() {

        String serverUrl = "http://192.168.10.107:8080/solr/collection1";
        String proUrl="http://112.124.37.33:8088/solr/core0";
        SolrServer solrServer = new HttpSolrServer(proUrl);

        ModifiableSolrParams params = new ModifiableSolrParams();

//        params.set("q", "labelName:*" + searchStr + "*");
        params.set("q", "*:*");
//        if (verificationString(searchStr)) {
//            params.set("q", "labelName:*" + searchStr + "*");
//        } else {
//            params.set("q", searchStr);
//        }

        params.set("qt", "/select");
//            params.set("defType", "edismax");
        params.set("start", "0");
        params.set("rows", "20000");
//            params.set("sort", "identityLabelId asc");
//            定义使用哪个request
//            handler进行搜索，若无指定，则使用默认的handler.默认是/select。若solrConfig.xml中无/select这个searchHandler，则返回以下错误
        QueryResponse response = null;
        try {
            response = solrServer.query(params);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
//        List<GeneralLabelVO> generalLabelVOs = response.getBeans(GeneralLabelVO.class);
//        System.out.println(generalLabelVOs.size());
        int i = 1;
        for (SolrDocument doc : results) {
            System.out.println("------------------------Document" + (i++) + "--------------------------------");
            for (String fieldName : doc.getFieldNames()) {
                System.out.println(fieldName + " : " + doc.getFieldValue(fieldName) + "  ");
            }
            System.out.println();
        }

        String proUrl1="http://112.124.37.33:8088/solr/core1";

    }

    public void testIdentity(String searchStr) {

        String serverUrl = "http://192.168.10.107:8080/solr/core0";
        SolrServer solrServer = new HttpSolrServer(serverUrl);

        ModifiableSolrParams params = new ModifiableSolrParams();

        if (verificationString(searchStr)) {
            //labelName是不分词的，有特殊字符时不走分词
            params.set("q", "labelName:*" + searchStr + "*");
        } else {
            //无特殊字符时候走分词 默认的搜索字段为text。使用solr自带的分词器，是按单个字分词。特殊字符会过滤掉。
            params.set("q", searchStr);
        }
        params.set("qt", "/select");
//            params.set("defType", "edismax");
        params.set("start", "0");
        params.set("rows", "20");
//            params.set("sort", "identityLabelId asc");
//            定义使用哪个request
//            handler进行搜索，若无指定，则使用默认的handler.默认是/select。若solrConfig.xml中无/select这个searchHandler，则返回以下错误
        QueryResponse response = null;
        try {
            response = solrServer.query(params);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        SolrDocumentList results = response.getResults();
        int i = 1;
        for (SolrDocument doc : results) {
            System.out.println("------------------------Document" + (i++) + "--------------------------------");
            for (String fieldName : doc.getFieldNames()) {
                System.out.println(fieldName + " : " + doc.getFieldValue(fieldName) + "  ");
            }
            System.out.println();
        }
    }

    public boolean verificationString(String inputStr) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\-\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.find()) return true;
        matcher = Pattern.compile("^[A-Za-z0-9]+$").matcher(inputStr);
        if (matcher.find()) return true;
        return false;
    }



}
