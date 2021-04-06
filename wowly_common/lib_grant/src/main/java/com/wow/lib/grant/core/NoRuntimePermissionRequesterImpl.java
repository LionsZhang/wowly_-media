package com.wow.lib.grant.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.PermissionChecker;

import com.wow.lib.grant.PermissionCallback;
import com.wow.lib.grant.PermissionRequester;
import com.wow.lib.grant.PermissionResult;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;



/**
 * <br> ClassName:   NoRuntimePermissionRequesterImpl
 * <br> Description: 不使用运行时权限的实现
 * <p>
 * <br> Author:      lionszhang
 * <br> Date:        2017/9/11
 */
class NoRuntimePermissionRequesterImpl
        implements PermissionRequester {

    private final Activity mActivity;
    private final LinkedList<String> mList = new LinkedList<>();

    NoRuntimePermissionRequesterImpl(Activity activity) {
        mActivity = activity;
    }

    @Override public PermissionRequester addPermission(@NonNull String... permission) {
        mList.addAll(Arrays.asList(permission));
        return this;
    }

    @Override public void request(@NonNull final PermissionCallback callback) {
        Disposable subscribe =observable().subscribe(new Consumer<List<PermissionResult>>() {
            @SuppressLint("CheckResult")
            @Override
            public void accept(@NonNull List<PermissionResult> permissionResults) throws Exception {
                LinkedList<String> mRefusedList = new LinkedList<>();
                LinkedList<String> mNoAskList = new LinkedList<>();
                //
                for (PermissionResult result : permissionResults) {
                    switch (result.getType()) {

                        case DENIED:
                            mRefusedList.add(result.getName());
                            break;

                        case NO_ASK:
                            mNoAskList.add(result.getName());
                            break;
                    }
                }
                //
                if ((mRefusedList.size() + mNoAskList.size()) == 0) {
                    callback.onAllAllow();
                } else {
                    if (mRefusedList.size() != 0) {
                        callback.onHasRefusedPermissions(mRefusedList);
                    }
                    if (mNoAskList.size() != 0) {
                        callback.onHadNoAskPermissions(mNoAskList);
                    }
                }
            }
        });
    }

    @Override public Observable<List<PermissionResult>> observable() {
        if (mList.size() == 0) {
            throw new IllegalStateException("need addPermission()");
        }
        if (Build.VERSION.SDK_INT >= 23) {
            //有运行时权限，但targetVersion < 23
            //大部分手机可以看作无运行时权限，不做检测（声明即授权），但有些手机（小米）依然会有运行时权限（声明了也可能不授权）
            return observableWith6();
        } else {
            //低于6.0，无运行时权限，不做检测
            return observableLowerThan6();
        }
    }

    @RequiresApi(23)
    private Observable<List<PermissionResult>> observableWith6() {
        List<PermissionResult> list = new LinkedList<>();
        for (String permission : mList) {
            int checkSelfPermission = PermissionChecker.checkSelfPermission(mActivity, permission);
            switch (checkSelfPermission) {

                case PermissionChecker.PERMISSION_GRANTED:
                    list.add(new PermissionResult(permission, PermissionResult.Type.GRANTED));
                    break;

                default:
                    list.add(new PermissionResult(permission, PermissionResult.Type.NO_ASK));
                    break;
            }
        }
        return Observable.just(list);
    }

    private Observable<List<PermissionResult>> observableLowerThan6() {
        List<PermissionResult> list = new LinkedList<>();
        for (String permission : mList) {
            list.add(new PermissionResult(permission, PermissionResult.Type.GRANTED));
        }
        return Observable.just(list);
    }
}
