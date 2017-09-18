package com.seaway.hiver.common.biz;

/**
 * Created by dell on 2017/6/30.
 */

public interface IBasePrinter {
        /**
         * 检查打印机是否可用
         *
         */
        int checkPrinter();

        /**
         * 初始化蓝牙打印设备
         *
         */
        int initBluePrint();

        /**
         * 打印发票
         *
         *
         */
        void printTxt();

        /**
         * 蓝牙打印
         *
         *
         */
        void bluePrint();

        /**
         * POS打印
         *
         *
         */
        void posPrint();
        /**
         * 初始化打印内容
         *
         *
         */
        void setPrintContect(String printContect);


}
