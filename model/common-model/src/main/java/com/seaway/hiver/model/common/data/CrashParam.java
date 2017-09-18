package com.seaway.hiver.model.common.data;

import com.seaway.hiver.model.common.data.param.BaseInputParam;
import com.seaway.hiver.model.common.data.param.CrashInfoParam;

public class CrashParam  {
    private String entity;
    private int operatorId;

    public CrashParam(String entity) {
        this.entity = entity;
        this.operatorId = 0;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }
}