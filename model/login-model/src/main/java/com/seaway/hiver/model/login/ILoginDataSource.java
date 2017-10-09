package com.seaway.hiver.model.login;


import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;

import io.reactivex.Observable;

/**
 * Created by Leo.Chang on 2017/5/10.
 */
public interface ILoginDataSource extends IDataSource {


    /**
     * 登录
     *
     * @param user      用户名
     * @param pwd       密码
     * @param loginMode 1:账号2：手势3：指纹
     * @param codeId    图片验证密码编号
     * @param code      验证码
     * @return
     */
    Observable<LoginVo> login(String user, String pwd, String loginMode, String codeId, String code);

    /**
     * 绑定设备
     *
     * @param nickName 设备别名
     * @param codeId   短信验证码编号
     * @param code     短信验证码
     * @return
     */
    Observable<BaseOutputVo> requestBindDeviceManage(String nickName, String codeId, String code);

    /**
     * 登录注销
     *
     * @return
     */
    Observable<BaseOutputVo> requestLogout();

    /**
     * 记录登录账号
     *
     * @param userName 登录账号
     */
    void rememberUserName(String userName);

    /**
     * 获取登录账号
     *
     * @return 登录账号
     */
    String getUserName();

    /**
     * 记录登录用户信息
     *
     * @param userInfo 登录用户信息
     */
    void saveLoginUserInfo(LoginVo userInfo);

    /**
     * 记录登录用户信息
     *
     * @return 登录信息
     */
    LoginVo getLoginUserInfo();

    /**
     * 修改登录密码
     *
     * @param oldPwd           旧密码
     * @param newPwd           新密码
     * @param credentialType   证件类型
     * @param credentialNumber 证件号码
     * @return
     */
    Observable<BaseOutputVo> requestLoginPwdModify(String oldPwd, String newPwd, String credentialType, String credentialNumber);
}