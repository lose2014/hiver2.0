package com.seaway.hiver.model.login.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.param.BaseInputParam;
import com.seaway.hiver.model.common.data.param.RequestSmsCodeParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.CheckUserNameVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.common.impl.DataSource;
import com.seaway.hiver.model.common.service.CommonService;
import com.seaway.hiver.model.login.ILoginDataSource;
import com.seaway.hiver.model.login.data.param.CheckUserNameParam;
import com.seaway.hiver.model.login.data.param.LoginParam;
import com.seaway.hiver.model.login.data.param.RequestBindDeviceManageParam;
import com.seaway.hiver.model.login.data.param.RequestLoginPwdModifyParam;
import com.seaway.hiver.model.login.data.param.RequestResetPwdParam;
import com.seaway.hiver.model.login.service.LoginService;
import com.seaway.hiver.model.login.shared.LoginSharedPreferences;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/10.
 */
public class LoginDataSource extends DataSource implements ILoginDataSource {

    /**
     * 登录
     *
     * @param user      用户名
     * @param pwd       密码
     * @return
     */
    @Override
    public Observable<LoginVo> login(String user, String pwd) {
        LoginParam param = new LoginParam();
        param.setUsername(user);
        param.setPassword(pwd);

        return Observable.just(param)
                .flatMap(new Function<LoginParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull LoginParam loginParam) throws Exception {
//                        return RetrofitClient.getInstance().create(LoginService.class).login(Util.transformat("requestUserLogin", loginParam));
                        return RetrofitClient.getInstance().create(LoginService.class).login(Util.transformat(loginParam));
                    }
                })
                .map(new ServerResponseFunc<LoginVo>(LoginVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<LoginVo>())
                .subscribeOn(Schedulers.newThread());
    }


    /**
     * 绑定设备
     *
     * @param nickName 设备别名
     * @param codeId   短信验证码编号
     * @param code     短信验证码
     * @return
     */
    @Override
    public Observable<BaseOutputVo> requestBindDeviceManage(String nickName, String codeId, String code) {
        RequestBindDeviceManageParam param = new RequestBindDeviceManageParam();
        param.setManageType("1");
        param.setNickName(nickName);
        param.setCodeId(codeId);
        param.setCode(code);

        return Observable.just(param)
                .flatMap(new Function<RequestBindDeviceManageParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull RequestBindDeviceManageParam loginParam) throws Exception {
                        return RetrofitClient.getInstance().create(LoginService.class).requestBindDeviceManage(Util.transformat("requestBindDeviceManage", loginParam));
                    }
                })
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<BaseOutputVo>())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 登录注销
     *
     * @return
     */
    @Override
    public Observable<BaseOutputVo> requestLogout() {
        return RetrofitClient.getInstance()
                .create(LoginService.class)
                .requestLogout(new BankBaseParam(""))
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<BaseOutputVo>())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 检查用户是否已注册
     *
     * @return
     */
    @Override
    public Observable<CheckUserNameVo> checkUserName(String userName) {
        CheckUserNameParam checkUserNameParam =new CheckUserNameParam();
        checkUserNameParam.setUserName(userName);
        return RetrofitClient.getInstance()
                .create(LoginService.class)
                .checkUserName(Util.transformat(checkUserNameParam))
                .map(new ServerResponseFunc<CheckUserNameVo>(CheckUserNameVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<CheckUserNameVo>())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 记住登录账号
     *
     * @param userName 登录账号
     */
    @Override
    public void rememberUserName(String userName) {
        LoginSharedPreferences.saveLoginUserName(userName);
    }

    /**
     * 获取登录账号
     *
     * @return 登录账号
     */
    @Override
    public String getUserName() {
        return LoginSharedPreferences.getLoginUserName();
    }

    /**
     * 保存登录用户信息
     *
     * @param userInfo 登录用户信息
     */
    @Override
    public void saveLoginUserInfo(LoginVo userInfo) {
        String str = "";
        if (null != userInfo) {
            str = new Gson().toJson(userInfo);
        }
        LoginSharedPreferences.saveLoginUserInfo(str);
    }

    /**
     * 记录登录用户信息
     *
     * @return 登录信息
     */
    @Override
    public LoginVo getLoginUserInfo() {
        String userInfo = LoginSharedPreferences.getLoginUserInfo();
        if (!TextUtils.isEmpty(userInfo)) {
            return new Gson().fromJson(userInfo,LoginVo.class);
        }

        return null;
    }

    /**
     * 修改登录密码
     *
     * @param oldPwd           旧密码
     * @param newPwd           新密码
     * @return
     */
    @Override
    public Observable<BaseOutputVo> requestLoginPwdModify(String oldPwd, String newPwd) {
        RequestLoginPwdModifyParam param = new RequestLoginPwdModifyParam(oldPwd, newPwd,newPwd);
        Logger.e("param"+param.getNewPassword());
        return Observable.just(param)
                .flatMap(new Function<RequestLoginPwdModifyParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull RequestLoginPwdModifyParam param) throws Exception {
                        return RetrofitClient.getInstance().create(LoginService.class).requestLoginPwdModify(Util.transformat(param));
                    }
                })
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<BaseOutputVo>())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 重置登录密码
     *
     * @param mobile           手机号码
     * @param newPwd           新密码
     * @param captcha           短信验证码
     * @param newPwdConfirm     确认手机号码
     * @return
     */
    @Override
    public Observable<BaseOutputVo> requestResetPwd(String mobile,String captcha, String newPwd, String newPwdConfirm) {
        RequestResetPwdParam param = new RequestResetPwdParam(mobile,captcha, newPwd, newPwdConfirm);
        return Observable.just(param)
                .flatMap(new Function<RequestResetPwdParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull RequestResetPwdParam param) throws Exception {
                        return RetrofitClient.getInstance().create(LoginService.class).requestResetPwd( Util.transformat(param));
                    }
                })
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc())
                .subscribeOn(Schedulers.newThread());
    }
}