package com.jsy.jsydemo.http.http.i.httpbase;

import android.content.Context;

import com.jsy.jsydemo.http.http.i.OkHttpCallBack;
import com.jsy.jsydemo.http.http.i.params.GetParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HttpEntity {
	/**
	 * 用去获取Mid接口
	 */
	public final static int LEVEL1 = 1;
	/**
	 * 只是对Key值进行解密，但是不做反序列化，解密后的值通过getDecodingKey获取
	 */
	public final static int LEVEL10 = 10;
	/**
	 * 进行数据解析，只需要设置Type后，会自动为您把interfaceLevel设置为该值
	 */
	public final static int LEVEL9 = 9;
	/**
	 * 不进行解析
	 */
	public final static int LEVEL8 = 8;
	/**
	 * 设置解析对应的实体类
	 */
	private Type type;
	/**
	 * 设置解析成功后的返回值
	 */
	private Object object;
	/**
	 * 网络请求失败，重试次数
	 */
	private int retries;
	/**
	 * 接口等级，默认接口等级为10，Mid接口等级为1,目前只有两个等级，后期可以扩展，接口等级可以理解为对不同的接口返回值做相应的处理
	 */
	private int interfaceLevel = LEVEL10;
	/**
	 * 是否需要解密
	 */
	private boolean isDecode = false;
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 是否是post请求
	 */
	private boolean isPost;
	/**
	 * 请求是否返回成功，目前是根据code码进行的判断
	 */
	private boolean isSuccess;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 服务器返回的Code
	 */
	private int code;
	/**
	 * 沙小僧的code。暂时没有使用
	 */
	private String sxsCode;
	/**
	 * 沙小僧服务器返回的msg
	 */
	private String sxsMsg;
	/**
	 * 沙小僧返回的数据Key
	 */
	private String Key;
	/**
	 * 解密后的Key
	 */
	private String decodingKey;
	/**
	 * 请求返回值
	 */
	private String responseDate;
	/**
	 * 请求开始的时间
	 */
	private String startDate;
	/**
	 * 请求结束的时间
	 */
	private String endDate;
	/**
	 * post请求参数
	 */
	private Map<String, String> map;
	/**
	 * 是否需要缓存当前接口返回值
	 */
	private boolean isCach = false;
	/**
	 * 缓存文件名
	 */
	private String cachFileName;
	/**
	 * 当前返回值是否来自缓存
	 */
	private boolean isFromCach = false;
	/**
	 * 是否应该提示用户了
	 */
	private boolean isRemindCustomer = false;
	/**
	 * 网络请求失败信息
	 */
	private String errorMsg = null;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isRemindCustomer() {
		return isRemindCustomer;
	}

	public void setRemindCustomer(boolean isRemindCustomer) {
		this.isRemindCustomer = isRemindCustomer;
	}

	private HttpEntity() {
	}

	public Type getType() {
		return type;
	}

	/**
	 * 如果是对象，则传对象名点Class，比如String.class<br>
	 * 如果是集合，则new TypeToken《List《String》》(){}.getType()
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
		this.interfaceLevel = LEVEL9;
		this.isDecode = true;
	}

	public void setType(Type type, int level) {
		this.type = type;
		this.interfaceLevel = level;
		this.isDecode = true;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public String getDecodingKey() {
		return decodingKey;
	}

	public StringBuffer getStringBuffer() {
		return stringBuffer;
	}

	public void setDecodingKey(String decodingKey) {
		this.decodingKey = decodingKey;
	}

	/**
	 * 获取get请求的Entity
	 * 
	 * @param url
	 * @param getParams
	 * @return
	 */
	public static HttpEntity getGetEntity(String url, GetParams getParams) {
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.isPost = false;
		httpEntity.url = url;
		httpEntity.params = getParams.toString();
		return httpEntity;
	}

	/**
	 * 获取一个Post请求
	 * 
	 * @param url
	 * @param dateValue
	 *            加密后的date value数据
	 * @return
	 */
	public static HttpEntity getPostEntity(String url, String dateValue) {
		if (dateValue == null || dateValue.equals("")) {
			return getPostEntity(url);
		}
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.isPost = true;
		httpEntity.url = url;
		Map<String, String> map = new HashMap<>();
		map.put("data", dateValue);
		httpEntity.map = map;
		return httpEntity;
	}

	/**
	 * 获取一个Post请求
	 * 
	 * @param url
	 *            加密后的date value数据
	 * @return
	 */
	public static HttpEntity getPostEntity(String url, Map<String, String> map) {
		if (map == null) {
			return getPostEntity(url);
		}
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.isPost = true;
		httpEntity.url = url;
		httpEntity.map = map;
		return httpEntity;
	}

	/**
	 * 获取一个post请求Entity
	 * 
	 * @param url
	 * @return
	 */
	public static HttpEntity getPostEntity(String url) {
		HttpEntity httpEntity = new HttpEntity();
		httpEntity.isPost = true;
		httpEntity.url = url;
		httpEntity.map = null;
		return httpEntity;
	}

	private StringBuffer stringBuffer = new StringBuffer();

	/**
	 * 组织参数 @Title: postParams @author: xusonghui @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param key @param: @param
	 * value @param: @return @return: HttpEntity @throws
	 */
	public HttpEntity postParams(String key, String value) {
		if (stringBuffer.length() > 0) {
			stringBuffer.append("&");
		}
		stringBuffer.append(key);
		stringBuffer.append("=");
		stringBuffer.append(value);
		return this;
	}

	/**
	 * 参数加密 @Title: build @author: xusonghui @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param context @param: @return @return:
	 * HttpEntity @throws
	 */
	public HttpEntity build(Context context) {
		if (stringBuffer.length() <= 0)
			return this;
//		Map<String, String> map = new HashMap<String, String>();
//		String dateValue = Utils_passwod.jiaMi(stringBuffer.toString(), GetMid.getQianBa(context));
//		map.put("data", dateValue);
		this.map = map;
		return this;
	}

	/**
	 * 参数组织完成后调用，直接进行发送请求 @Title: build @author: xusonghui @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param context @param: @param
	 * okHttpCallBack @param: @param taskName @return: void @throws
	 */
	public void build(Context context, OkHttpCallBack okHttpCallBack, String taskName) {
//		if (stringBuffer.length() > 0) {
//			Map<String, String> map = new HashMap<String, String>();
//			String dateValue = Utils_passwod.jiaMi(stringBuffer.toString(), GetMid.getQianBa(context));
//			map.put("data", dateValue);
//			this.map = map;
//		}
		new HttpPostRequest(context, this, okHttpCallBack).setTaskName(taskName).start();
	}

	public int getInterfaceLevel() {
		return interfaceLevel;
	}

	public void setInterfaceLevel(int interfaceLevel) {
		this.interfaceLevel = interfaceLevel;
	}

	public void setInterfaceLevel(int interfaceLevel, boolean isDecode) {
		this.interfaceLevel = interfaceLevel;
		this.isDecode = isDecode;
	}

	public boolean isFromCach() {
		return isFromCach;
	}

	public void setFromCach(boolean isFromCach) {
		this.isFromCach = isFromCach;
	}

	public boolean isCach() {
		return isCach;
	}

	/**
	 * 设置缓存文件名
	 * 
	 * @param isCach
	 * @param cachFileName
	 */
	public void setCach(boolean isCach, String cachFileName) {
		this.isCach = isCach;
		this.cachFileName = cachFileName;
	}

	public String getCachFileName() {
		return cachFileName;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public boolean isDecode() {
		return isDecode;
	}

	public void setDecode(boolean isDecode) {
		this.isDecode = isDecode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSxsCode() {
		return sxsCode;
	}

	public void setSxsCode(String sxsCode) {
		this.sxsCode = sxsCode;
	}

	public String getSxsMsg() {
		return sxsMsg;
	}

	public void setSxsMsg(String sxsMsg) {
		this.sxsMsg = sxsMsg;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String decodError() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\n");
		stringBuffer.append("=========================================\n");
		stringBuffer.append("| url = ").append(url).append("\n");
		stringBuffer.append("|isPost = ").append(isPost).append("\n");
		stringBuffer.append("|params = ").append(params).append("\n");
		stringBuffer.append("|type = ").append(type).append("\n");
		stringBuffer.append("|params = ").append(params).append("\n");
		if (map != null)
			stringBuffer.append("| map = ").append(map.toString()).append("\n");// post请求参数
		stringBuffer.append("| sxsCode = ").append(sxsCode).append("\n");// 沙小僧服务器返回的code
		stringBuffer.append("| responseDate = ").append(responseDate).append("\n");// 请求返回值
		stringBuffer.append("| startDate = ").append(startDate).append("\n");// 请求开始的时间
		stringBuffer.append("| startDate = ").append(startDate).append("\n");// 请求开始的时间
		stringBuffer.append("| endDate = ").append(endDate).append("\n");// 请求结束的时间
		stringBuffer.append("=========================================");
		return stringBuffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\n");
		stringBuffer.append("=========================================\n");
		stringBuffer.append("| isFromCach = ").append(isFromCach).append("\n");// 是否来自缓存文件读取
		stringBuffer.append("| isDecode = ").append(isDecode).append("\n");// 是否需要解密
		stringBuffer.append("| url = ").append(url).append("\n");// 请求地址
		stringBuffer.append("| isPost = ").append(isPost).append("\n");// 请求方式
		stringBuffer.append("| isSuccess = ").append(isSuccess).append("\n");// 是否成功
		stringBuffer.append("| params = ").append(params).append("\n");// get请求参数
		stringBuffer.append("| decodingKey = ").append(decodingKey).append("\n");// get请求参数
		if (map != null)
			stringBuffer.append("| map = ").append(map.toString()).append("\n");// post请求参数
		stringBuffer.append("| code = ").append(code).append("\n");// 服务器返回的code
		stringBuffer.append("| sxsCode = ").append(sxsCode).append("\n");// 沙小僧服务器返回的code
		stringBuffer.append("| responseDate = ").append(responseDate).append("\n");// 请求返回值
		stringBuffer.append("| httpErrorMsg = ").append(errorMsg).append("\n");// 失败信息
		stringBuffer.append("| startDate = ").append(startDate).append("\n");// 请求开始的时间
		stringBuffer.append("| endDate = ").append(endDate).append("\n");// 请求结束的时间
		stringBuffer.append("=========================================");
		return stringBuffer.toString();
	}

}
