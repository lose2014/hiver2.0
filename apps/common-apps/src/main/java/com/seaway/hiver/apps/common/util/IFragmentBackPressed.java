package com.seaway.hiver.apps.common.util;

/**
 * 返回监听接口，用于Fragment监听返回键
 * Created by Leo.Chang on 2017/5/11.
 */
public interface IFragmentBackPressed {
    /**
     * 返回键监听
     *
     * @return true监听返回键；false：不监听
     */
    boolean onBackPressed();
}