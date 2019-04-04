package com.example.admin.last.loginMvp;

import android.app.Activity;
import android.support.annotation.NonNull;

public interface LoginPersenter {
    void getRequest_permission(Activity activity);
    void getRequest_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    void goToLs();
    void goToMs();
}
