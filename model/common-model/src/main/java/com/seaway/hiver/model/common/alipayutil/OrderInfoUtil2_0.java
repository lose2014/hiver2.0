package com.seaway.hiver.model.common.alipayutil;

import com.seaway.hiver.model.common.data.param.CrashInfoParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderInfoUtil2_0 {
	private int i=0;

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2017091008649825";

	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088821112108800";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "kkkkk091125";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDdKe9+s4+9pb58aaGkQh464neGan5Ug6XTLxv7LxzlaZ3A4i9pfyfsQtc5U62y2PuMpH6lJIe4XelNkDZp7Lis1TseygTTayULpKBKUnT2JudEHsB+UQvyeinXM90HHISLnBEMx6mWed45DZV3xGLCzTLRKqrHTT985gVOJJoZ4AND3lSUIdV4AAJ0FrNGpJb4TqjVFK72hE9AdCL7EVhqJJ/8j8oY0nEebih1MykdctC32t5EvygoxYglzrjZcXKBNMnVKnWPgsofsTeFt3j8PjAjC272VC+8JH7CaBWm32lFrlEgYcjmWtDWBDFUXu1l0CV96X9QT6d7VziS6vuHAgMBAAECggEASQ+bcI29BHeGj+8NWQgGmIlL1vnDNgQvea5sXfRYZWhiverlimiZAV/mMsT8no0PYr0vqdwtvl0ZHGRqu/jKebrsxzV5Pvz0CvrFweRdDv9K3v6lkRabuAzRC0bOV6Gu9yt5Ljv4PrsiStFQC++mswjnBrRmMuxkxlvycDrK79nsPf0WSj0KzMwdj27uf0zKEh2PQ2/eUE2n0s8/w4E99XVxdRqG7VimchdCo5cC4foHIW39QT7FSr75rV5Rxy5+lyxObu1PyygVOihYcTpgFtmnmPgUtXPc5ltKUfKFDO4oEverez5AqBk6HwmknEJBKm3dn4SfX4z7cXBlY+xreyb8QKBgQD9G3B8LY+iNquPgj/EolTnqgh42v/8qGUGowqAo73saCuHBIXSOAwfs55CrAher72iBQRZjnqC3SefcfSxMvG0T3ntbKzJ6PjM77ZsICmTAG2iQXfkWVa5LrIG0EZBf3zUbeTawINQxkQfHeHZmT0xu51RkuyfXQHXGspcfZc1PQKBgQDfsQiivUaZNmznBF6RrxN35ueb5hzthCNZDCYRnHdHQt0TAgZYqM9RjFp5MOpsKKGSXsdJil/4o1CHndusI8J2J4zB7z6Cjdydc6LIHj30DqgzXkCHvCw1ReXV81cx08Gpj9PJMqjEmH8joRrOGPxdwJl//i670Kl2F+7MjOqoEwKBgQDpdf/hap0MAOVqL7fmBd3O+bVq1IY+ZI0a/BRZ567teoB7v543816rdyBJWyk1PWDrKzmB6bvzLU648YHEWLs9l/ldGFqguvWTAqD/RwNSBpkZis2toQIvOXUYd8/15kQcJWlspRn6zYZUNVn3PjOFxgosDhuYO0/RO3LgsS9h+QKBgG7TsWmfyVijhHU4zvny+fxP5LRy4CY1b6XwxPO4x8gAsrN2L1rB2GWY2kJtH9gSkWqJlscyzbXMhFuMtIem9/O+SJjVRe09B+7imeX4Rc2jbWZ17hbR/j0usl68P5kqRysRXWazuLYer+8hJLyz2GVCxUqKzvCywyMFXITv1FsNAoGAP3XXvcmKn/s67neQ6zeBhBBRokd2MtUG86dcmie6apKVa+1dQy9ubwbm1Rhz0BAu7AyICFy6EXWMegDHk0NC7do/Gxo3NChEHWmQS68x5Z4XlHBYk78DekGIeCcDp6JCZfQxFo9zIf04D0dVjUwPitSbEbtLKfZ3lqFG1rPPmAw=";

	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	private static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id, boolean rsa2) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		return keyValues;
	}

	/**
	 * 对支付参数信息进行签名
	 *
	 * @param map
	 *            待签名授权信息
	 *
	 * @return
	 */
	private static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}

	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * 构造支付订单参数列表
	 * @param app_id
	 * @return
	 */
	private static Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);

		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() +  "\"}");

		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		keyValues.put("timestamp", "2016-07-29 16:55:53");

		keyValues.put("version", "1.0");

		return keyValues;
	}


	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}


	/**
	 * 构造支付订单参数信息
	 *
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	private static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}

	public static String authInfo(){
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);

		Map<String, String> authInfoMap = buildAuthInfoMap(PID, APPID, System.currentTimeMillis()+"", rsa2);
		String info = buildOrderParam(authInfoMap);
		String privateKey = RSA2_PRIVATE;
		String sign = getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		return authInfo;
	}
}
