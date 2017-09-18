package com.seaway.common.apps.qrcode.scan.util;

import java.util.Observable;

/**
 * Android工具类
 * Created by Leo.Chang on 2016/12/12.
 */
public class OnDataChangedNotificationObservable extends Observable {
	public OnDataChangedNotificationObservable() {
	}

	public void dataChangedNotification() {
		this.setChanged();
		this.notifyObservers(this);
	}

	public void dataChangedNotification(Object data) {
		this.setChanged();
		this.notifyObservers(data);
	}

	public class DataChangedObject {
		public String dataType;
		public Object data;

		public DataChangedObject() {
		}
	}
}
