package com.seaway.hiver.biz.login.presenter;


import com.seaway.hiver.common.biz.contract.RequestSmsCodeContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.BasePresenter;
import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 获取短信验证码
 * Created by Leo.Chang on 2017/5/18.
 */
public class RequestSmsCodePresenter<T extends RequestSmsCodeContract.RequestSmsCodeView,E extends IDataSource> extends BasePresenter<T, E> implements RequestSmsCodeContract.RequestSmsCodePresenter {

    public RequestSmsCodePresenter(T t) {
        super(t);
        // 注入数据源
//        setDataSource(new DataSource());
//        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    /**
     * 获取短信验证码
     *
     * @param mobile       手机号码
     * @param businessType 业务类型
     * @param cardId       卡号
     * @param transAmt     交易金额
     * @param codeId       图形验证码编号
     * @param code         图形验证码内容
     */
    @Override
    public void requestSmsCode(String mobile, String businessType, String cardId, String transAmt, String codeId, String code) {
        mDataSource.requestSmsCode(mobile, businessType, cardId, transAmt, codeId, code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BaseOutputVo>(mView,mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull BaseOutputVo vo) {
                        mView.getSmsCodeSuccess(vo);
                    }
                });
    }
}
