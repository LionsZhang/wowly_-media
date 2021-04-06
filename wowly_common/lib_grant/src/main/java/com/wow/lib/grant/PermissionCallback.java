package com.wow.lib.grant;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;


/**
 * <br> ClassName:   PermissionCallback
 * <br> Description: 权限请求的结果监听者
 * <p>
 * <br> Author:      lionszhang
 * <br> Date:        2017/9/6
 */
public interface PermissionCallback {

    /**
     *<br> Description: 所有权限请求都通过时的回调
     *<br> Author:      lionszhang
     *<br> Date:        2017/9/6 19:15
     */
    void onAllAllow();

    /**
     *<br> Description: 存在未通过的权限
     *<br> Author:      lionszhang
     *<br> Date:        2017/9/6 19:15
     */
    void onHasRefusedPermissions(@NonNull List<String> permissions);

    /**
     *<br> Description: 存在不再询问的权限
     *<br> Author:      lionszhang
     *<br> Date:        2017/9/6 19:15
     */
    void onHadNoAskPermissions(@NonNull List<String> permissions);
}
