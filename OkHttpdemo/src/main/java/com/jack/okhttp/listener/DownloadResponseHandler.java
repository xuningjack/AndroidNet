package com.jack.okhttp.listener;

import java.io.File;

/**
 * Created by xuning on 17/9/14.
 */

public abstract class DownloadResponseHandler {

    public abstract void onFinish(File download_file);
    public abstract void onProgress(long currentBytes, long totalBytes);
    public abstract void onFailure(String error_msg);
}
