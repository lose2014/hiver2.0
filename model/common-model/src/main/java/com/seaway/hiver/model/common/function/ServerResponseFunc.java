package com.seaway.hiver.model.common.function;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.seaway.android.ndk.NativeSDK;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.exception.ServerResponseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务端返回信息过滤
 * Created by Leo.Chang on 2017/5/5.
 */
public class ServerResponseFunc<T> implements Function<BaseVo, T> {
    private Class<T> t;

    public ServerResponseFunc(Class<T> t) {
        this.t = t;
    }

    @Override
    public T apply(@NonNull BaseVo vo) throws Exception {
//        if (!"000000".equals(vo.getRespCode())) {
//            // 如果服务端返回错误，则抛出异常
//            throw new ServerResponseException(vo.getRespCode(), vo.getRespMsg());
//        } else {
//            if (!NativeSDK.d(vo.getOutput(),vo.getCheckSign())) {
//                // 如果验签不通过，则抛出异常
//                throw new ServerResponseException("-100002", "系统异常，请稍后再试");
//            }
//        }
        if (!vo.getSucc()) {
            // 如果服务端返回错误，则抛出异常
            throw new ServerResponseException(vo.getCode(), vo.getMessage());
        }
//        else {
//            if (!NativeSDK.d(vo.getOutput(),vo.getCheckSign())) {
//                // 如果验签不通过，则抛出异常
//                throw new ServerResponseException("-100002", "系统异常，请稍后再试");
//            }
//        }
        if(vo.getEntity()!=null){
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                        @Override
                        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                            if (src == src.longValue())
                                return new JsonPrimitive(src.longValue());
                            return new JsonPrimitive(src);
                        }
                    }).create();
            String json = gson.toJson(vo.getEntity());
            return (T) gson.fromJson(json,t);
        }else{
            return (T) new Gson().fromJson("{}",t);
        }

    }
}