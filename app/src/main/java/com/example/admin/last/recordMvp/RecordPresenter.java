package com.example.admin.last.recordMvp;

import android.app.Activity;
import android.support.annotation.NonNull;

public interface RecordPresenter {
    void getRequest_permission(Activity activity);
    void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
