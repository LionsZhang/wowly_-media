package com.wowly.media_module.vm;
import com.wowly.common.app.mvvm.model.BaseModel;
import com.wowly.common.app.mvvm.vm.BaseViewModel;
import com.wowly.media_module.model.MainModel;


/**
 * @Author Lionszhang
 * @Date 2021/2/25 17:33
 * @Name RegisterViewModel.java
 * @Instruction mainActivity VM类
 */
public class MainViewModel extends BaseViewModel {
    private MainModel mMainModel;





    @Override
    public void initData() {

    }


    /**
     * @Author Lionszhang
     * @Date 2021/2/25 10:36
     * @Instruction 释放资源
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mMainModel = null;
    }

    @Override
    public BaseModel createModel() {
        mMainModel = new MainModel();
        return mMainModel;
    }


}
