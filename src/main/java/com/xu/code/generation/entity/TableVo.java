package com.xu.code.generation.entity;

import lombok.Data;

import java.util.Map;
@Data
public class TableVo {
    private String tableName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String entityPackage;
    private String entityName;
    private Integer fieldRowNum;
    private Integer searchFieldNum;
    private Integer fieldRequiredNum;
    private Map<?, ?> extendParams;
}

