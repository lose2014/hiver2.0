package com.seaway.hiver.model.login.data.param;

import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 检测用户名是否存在(POST /teacher/checkUsernameExist)
 */
public class CheckUserNameParam extends BaseInputParam {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
