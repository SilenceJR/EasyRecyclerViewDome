package com.silence.xutils3httpdome;

import android.os.Environment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


public class FileServerManage {

    private static FileServerManage Instance;


    public static final String SERVER_URL = "http://192.168.1.166:8080/duimy/upload/";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/abc.jpg";

    public static final String SERVER_FILE_PATH = "http://192.168.1.166:8080/upload/pic/5ffebc8b-0771-4824-896e-b509729a5f00.jpg";
    public static final String PATH = Environment.getExternalStorageDirectory() + "/aDuimy/abc.jpg";

    public static FileServerManage getInstance() {
        if (Instance == null) {
            Instance = new FileServerManage();
        }
        return Instance;
    }

    public void uploadFile(final FileCallback fileCallback) {
        final File file = new File(FILE_PATH);
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(SERVER_URL);
                params.setMultipart(true);
                params.addBodyParameter("file", file);
                params.addBodyParameter("fileType", "1");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(final String result) {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                fileCallback.onSuccess(result);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable ex, boolean isOnCallback) {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                fileCallback.onError(ex);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });


    }

    public void downloadFile(final FileCallback fileCallback) {

        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(SERVER_FILE_PATH);
                params.setSaveFilePath(PATH);
//                params.setAutoRename(true);
                x.http().post(params, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        final String absolutePath = result.getAbsolutePath();
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                fileCallback.onSuccess(absolutePath);
                            }
                        });

                    }

                    @Override
                    public void onError(final Throwable ex, boolean isOnCallback) {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                fileCallback.onError(ex);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {

                    }
                });
            }
        });

    }

}
