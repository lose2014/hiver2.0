package com.seaway.hiver.model.common.data.param;

import com.seaway.android.sdk.tools.ApplicationUtil;
import com.seaway.hiver.model.common.Constants;
import com.seaway.hiver.model.common.data.vo.ClientInfoVo;

/**
 * Created by Leo.Chang on 2017/5/16.
 */

public class BaseCheckInputParam {
    private ClientInfoVo entity;

    public BaseCheckInputParam(ClientInfoVo entity) {
        this.entity = entity;
    }

    public ClientInfoVo getEntity() {
        return entity;
    }

    public void setEntity(ClientInfoVo entity) {
        this.entity = entity;
    }
}