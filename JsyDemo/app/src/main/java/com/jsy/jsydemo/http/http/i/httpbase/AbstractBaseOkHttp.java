package com.jsy.jsydemo.http.http.i.httpbase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.http.http.i.IOkHttpRequest;
import com.jsy.jsydemo.http.http.i.IOkHttpResponse;
import com.jsy.jsydemo.http.http.i.OkHttpCallBack;
import com.jsy.jsydemo.utils.FileUtils;
import com.jsy.jsydemo.utils.TimeUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * 项目名称：groupBackstage 类描述： 创建时间：2015/10/20 14:03 修改人：Administrator
 * 修改时间：2015/10/20 14:03 修改备注：
 */
@SuppressWarnings({"JavaDoc", "ConstantConditions", "StatementWithEmptyBody"})
@SuppressLint("SimpleDateFormat")
public abstract class AbstractBaseOkHttp implements IOkHttpRequest, IOkHttpResponse, Handler.Callback {
    public static final int START = 1;
    public static final int PROCESSING = 2;
    public static final int NOT_THIS_TASK = 3;
    public static final int START4 = 4;
    public static final int END = 5;
    private int httpState = PROCESSING;
    private static Map<String, AbstractBaseOkHttp> taskState = new HashMap<>();
    private static int taskId = 0;
    private static long connectTimeout = 10;
    private static long readTimeout = 10;
    // private static long writeTimeout = 10;
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    protected static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String ACCEPT_KEY = "Accept";
    private static final String ACCEPT = "application/json";
    public static final String UTF8 = "utf8";
    protected HttpEntity httpEntity;
    private static OkHttpClient.Builder mOkHttpClientBuilder;
    protected Context context;
    private int cacheSize = 10 * 1024 * 1024; // 10 MiB

    /**
     * 网络请求 异常信息
     */
    protected String errorMessage;
    OkHttpClient okHttpClient;
    protected String requestJson;

    protected int responseCode = 200;
    protected Handler handler;
    protected OkHttpCallBack okHttpCallBack;
    private final static int HTTP_ERROR = 1000;
    private final static int HTTP_SUCCESS = 1001;
    private String taskName;
    private int retries = 0;

    /**
     * @param context
     */
    public AbstractBaseOkHttp(Context context, HttpEntity httpEntity, OkHttpCallBack okHttpCallBack) {
        this.okHttpCallBack = okHttpCallBack;
        this.context = context.getApplicationContext();
        this.httpEntity = httpEntity;
        this.httpEntity.setStartDate(TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss:SSS")));
        handler = new Handler(context.getMainLooper(), this);
        taskId++;
        okHttpClient = getOkHttpClient();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HTTP_SUCCESS) {// success
            onMtsSuccess();
        } else if (msg.what == HTTP_ERROR) {// error
            onFailed(errorMessage + "");
        }
        return false;
    }

    public String getTaskName() {
        return taskName;
    }

    public static long getTimeOut() {
        return connectTimeout * 1000;// + connectTimeout * 1000 + connectTimeout
        // * 1000;
    }

    /**
     * 设置任务名
     *
     * @param taskName
     * @return
     */
    public AbstractBaseOkHttp setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void start() {
        if (taskName == null || "".equals(taskName)) {
            taskName = "taskId_" + taskId;
        }
        notNetWork(false, false);
        if (taskState.containsKey(taskName)) {
            AbstractBaseOkHttp abstractBaseOkHttp = taskState.get(taskName);
            if (abstractBaseOkHttp != null) {
                abstractBaseOkHttp.okHttpCallBack = null;
            }
        }
        httpState = START;
        taskState.put(taskName, this);
//        if (!NetWorkUtils.isNetworkConnected(context)) {
//            errorMessage = "没有网络";
//            notNetWork(true, true);
//        }
        execute(okHttpClient, getRequest(), getResponseCallBack());
        httpState = PROCESSING;
    }

    private void notNetWork(boolean isRemoveTask, boolean isCallBackError) {

        this.httpEntity.setEndDate(TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss:SSS")));
        if (httpEntity.isCach()) {
            String fileName = FileUtils.getSxsExternalStorageFilePath(context, httpEntity.getCachFileName());
            if (fileName == null) {
                httpEntity.setSuccess(false);
                if (isRemoveTask) {
                    httpState = END;
                    removeTask();
                }
                if (isCallBackError) {
                    handler.sendEmptyMessage(HTTP_ERROR);
                }
                return;
            }
            final String responseDate = FileUtils.readFile(fileName, "utf-8").toString();
            httpEntity.setFromCach(true);
            httpEntity.setResponseDate(responseDate);
            if (!deserialization()) {
            }
            httpEntity.setSuccess(true);
            if (isRemoveTask) {
                httpState = END;
                removeTask();
            }
            handler.sendEmptyMessage(HTTP_SUCCESS);
        } else {
            httpEntity.setSuccess(false);
            if (isRemoveTask) {
                httpState = END;
                removeTask();
            }
            if (isCallBackError) {
                handler.sendEmptyMessage(HTTP_ERROR);
            }
        }
    }

    public static int getTaskState(String taskName) {
        if (!taskState.containsKey(taskName)) {
            return NOT_THIS_TASK;
        }
        return taskState.get(taskName).httpState;
    }

    /**
     * 获取HttpClient
     *
     * @return
     */
    @Override
    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClientBuilder == null) {
            mOkHttpClientBuilder = new OkHttpClient.Builder();
            mOkHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.readTimeout(connectTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.writeTimeout(connectTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.retryOnConnectionFailure(true);
            // mOkHttpClientBuilder.cache(
            // new Cache(new File(FileUtils.getExternalDownloadPath() +
            // File.separator + "cache.tmp"), cacheSize));
        }
        return mOkHttpClientBuilder.build();
    }

    /**
     * 请求配置
     *
     * @return
     */
    @Override
    public Builder getRequestBuilder() {
        Builder buider = new Builder();
        buider.addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE).addHeader(ACCEPT_KEY, ACCEPT);
        return buider;
    }

    /**
     * 处理接口返回
     *
     * @return
     */
    @Override
    public Callback getResponseCallBack() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    errorMessage = context.getString(R.string.network_timed);
                } else if (e instanceof ConnectException) {
                    errorMessage = context.getString(R.string.network_timed);
                } else if (e.toString().contains("SocketException")) {
                    errorMessage = context.getString(R.string.network_timed);
                } else if (e.toString().contains("SocketTimeoutException")) {
                    errorMessage = context.getString(R.string.network_timed);
                } else if (e.toString().contains("UnknownHostException")) {
                    errorMessage = context.getString(R.string.no_network_timed);
                } else {
                    errorMessage = context.getString(R.string.no_network_timed);
                }
                notNetWork(true, true);
                onFailed(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                responseCode = response.networkResponse().code();
                httpEntity.setCode(responseCode);
                if (response.isSuccessful()) {
                    onSuccess(response);
                } else {
                    if (responseCode >= 400 && responseCode < 500) {
                        errorMessage = context.getString(R.string.no_network_timed);
                    } else if (responseCode > 500) {
                        errorMessage = context.getString(R.string.no_network_timed);
                    }
                    onFailed(new IOException("onFailed" + response));
                }
            }
        };
    }

    /**
     * 如果子类需要处理失败信息 重写该方法
     *
     * @param exception
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onFailed(final Exception exception) {
        this.httpEntity.setEndDate(TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss:SSS")));
        httpEntity.setSuccess(false);
        httpEntity.setErrorMsg(exception.toString());
        httpState = END;
        removeTask();
        handler.sendEmptyMessage(HTTP_ERROR);
    }

    @Override
    public void onSuccess(Response response) {
        final String responseString;
        try {
            responseString = response.body().string();
            this.httpEntity
                    .setEndDate(TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss:SSS")));
            this.httpEntity.setResponseDate(responseString);
            if (httpEntity.isCach()) {
                String fileName = FileUtils.getSxsExternalStorageFilePath(context, httpEntity.getCachFileName());
                if (fileName != null) {
                    if (FileUtils.writeFile(fileName, responseString)) {

                    } else {

                    }
                }
            }
            httpEntity.setFromCach(false);
            if (!deserialization() && retries < 0) {
                retries++;
                execute(getOkHttpClient(), getRequest(), getResponseCallBack());
                return;
            }
            httpEntity.setSuccess(true);
            httpState = END;
            removeTask();
            // onMtsSuccess(responseString);
            handler.sendEmptyMessage(HTTP_SUCCESS);
        } catch (final IOException e) {
            onFailed(e);
        }
    }

    public void onMtsSuccess() {
        if (okHttpCallBack != null) {
//            if (!StringUtil.isNullOrEmpty(httpEntity.getResponseDate()) && httpEntity.getResponseDate().contains("{")) { // 用正则表达式来做截取中间{}中间部分
//                Pattern pattern = Pattern.compile("\\{(.*)\\}");
//                Matcher matcher = pattern.matcher(httpEntity.getResponseDate());
//                String ResponseDates = "";
//                while (matcher.find()) {
//                    ResponseDates = matcher.group(1);
//                }
//                httpEntity.setResponseDate("{" + ResponseDates + "}");
//            }
            okHttpCallBack.success(getTaskName(), httpEntity.getResponseDate(), httpEntity);
        }
    }

    public void onFailed(final String errorMsg) {
        if (okHttpCallBack != null) {
            okHttpCallBack.onFailure(getTaskName(), errorMsg, httpEntity);
        }
    }

    private synchronized void removeTask() {
        taskState.remove(taskName);
    }

    public int getResponseCode() {
        return responseCode;
    }

    /**
     * { "Key": "M=", "MSG": "0", "COD": "102" }
     */
    protected abstract boolean deserialization();

    /**
     * 启动网络请求
     *
     * @param okHttpClient
     * @param request
     * @param callback
     */
    @Override
    public void execute(OkHttpClient okHttpClient, Request request, Callback callback) {
        try {
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Exception ignored) {
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRequestJson() {
        return requestJson;
    }

//	public void setCertificates(InputStream... certificates) {
//		try {
//			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//			keyStore.load(null);
//			int index = 0;
//			for (InputStream certificate : certificates) {
//				String certificateAlias = Integer.toString(index++);
//				keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//				try {
//					if (certificate != null)
//						certificate.close();
//				} catch (IOException e) {
//				}
//			}
//
//			SSLContext sslContext = SSLContext.getInstance("TLS");
//
//			TrustManagerFactory trustManagerFactory = TrustManagerFactory
//					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//			trustManagerFactory.init(keyStore);
//			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//			okHttpClient = okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory())
//					.hostnameVerifier(new HostnameVerifier() {
//
//						@Override
//						public boolean verify(String hostname, SSLSession session) {
//							// TODO Auto-generated method stub
//							return true;
//
//						}
//					}).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
}
