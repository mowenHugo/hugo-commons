package com.hugo.commons.service;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @Author : wwg
 * @Date : 15-1-8 下午2:13.
 */
public class GeneralLabelVO {

    @Field("labelId")
    private String labelId;
    @Field("labelName")
    private String labelName;
    @Field("type")
    private Integer type;
    @Field("hotValue")
    private Integer hotValue;
    @Field("orderBy")
    private Integer orderBy;


    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHotValue() {
        return hotValue;
    }

    public void setHotValue(Integer hotValue) {
        this.hotValue = hotValue;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
