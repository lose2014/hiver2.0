package com.seaway.hiver.apps.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * User: Howard chen(howardchen@mooyoo.com.cn)
 * Date: 2017/2/8
 * Time: 下午7:41
 * Desc:
 */
public class AssistFragment extends Fragment {
    Map<Integer, PublishSubject<ActivityBackWrapper>> map = new HashMap<>();

    int requestCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
//            sm = (SerializableMap) savedInstanceState.getSerializable("map");
            requestCode = savedInstanceState.getInt("requestCode",0);
            if (0 != requestCode) {
                PublishSubject<ActivityBackWrapper> subject = PublishSubject.create();
                map.put(requestCode, subject);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PublishSubject<ActivityBackWrapper> subject = map.remove(requestCode);
        subject.onNext(new ActivityBackWrapper(data, requestCode, resultCode));
        subject.onComplete();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("map",sm);
        outState.putInt("requestCode",requestCode);
    }

    public Observable<ActivityBackWrapper> startRxActivity(Intent intent, int requestCode) {
        if (map.containsKey(requestCode)) {
            throw new IllegalArgumentException("requestCode: " + requestCode + " 重复定义");
        }
        this.requestCode = requestCode;

        startActivityForResult(intent, requestCode);
        PublishSubject<ActivityBackWrapper> subject = PublishSubject.create();
        map.put(requestCode, subject);
        return subject;
    }
}
