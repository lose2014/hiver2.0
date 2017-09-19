package com.seaway.hiver.model.login.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.param.BaseInputParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.common.impl.DataSource;
import com.seaway.hiver.model.login.ILoginDataSource;
import com.seaway.hiver.model.login.data.param.LoginParam;
import com.seaway.hiver.model.login.data.param.RequestBindDeviceManageParam;
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
     * @param loginMode 登录方式 1:账号2：手势3：指纹
     * @param codeId    图片验证密码编号
     * @param code      验证码
     * @return
     */
    @Override
    public Observable<LoginVo> login(String user, String pwd, String loginMode, String codeId, String code) {
        LoginParam param = new LoginParam();
        param.setUserName(user);
        param.setLoginPasswd(pwd);
        param.setCodeId(codeId);
        param.setCode(code);
        param.setLoginMode(loginMode);

        return Observable.just(param)
                .flatMap(new Function<LoginParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull LoginParam loginParam) throws Exception {
                        return RetrofitClient.getInstance().create(LoginService.class).login(Util.transformat("requestUserLogin", loginParam));
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
                .requestLogout(Util.transformat("requestLogout", new BaseInputParam()))
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<BaseOutputVo>())
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

}