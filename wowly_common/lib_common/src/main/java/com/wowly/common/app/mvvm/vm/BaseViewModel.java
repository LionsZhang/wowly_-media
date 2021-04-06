package com.wowly.common.app.mvvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wowly.common.app.BaseApplication;
import com.wowly.common.app.mvvm.model.BaseModel;

/**
 * @Author Lionszhang
 * @Date 2021/2/25 9:46
 * @Name BaseViewModel.java
 * @Instruction MVVM架构VM层基类
 */
public abstract class BaseViewModel extends ViewModel implements IVModel {
    /**
     * 吐司提示等消息LiveData
     */
    public MutableLiveData<String> mHintInfoLiveData;
    protected BaseModel mCurrentModel;
    /**
     * 只提供与VIEW层交互的不需要数据的LIVEDAT,View层根据传值做不同操作，跳转
     * 如果需要交互带实体数据需要重新创建LIVEDATA
     */
    public MutableLiveData<Integer> mNavLiveDate;
    /**弹窗显示常量*/
    public static int ACTION_SHOW_DIALOG=100;
    /**弹窗隐藏常量*/
    public static int ACTION_HIDE_DIALOG=101;
    public BaseViewModel() {
        createModel();
        mHintInfoLiveData = new MutableLiveData<>();
        mNavLiveDate = new MutableLiveData<>();
    }

    protected void sendHintMessage(int stringId) {
        mHintInfoLiveData.postValue(getString(stringId));
    }



    protected String getString(int stringId) {
      return BaseApplication.getContext().getResources().getString(stringId);
    }



    protected void sendHintMessage(String msg) {
        mHintInfoLiveData.postValue(msg);
    }

    @Override
    public abstract BaseModel  createModel();

    /**
     * @Author Lionszhang
     * @Date 2021/3/4 11:09
     * @Param action   参数常量在子类定义
     * @Instruction 只提供与VIEW层交互的不需要数据的LIVEDAT,View层根据传值做不同操作，跳转
     *  如果需要交互带实体数据需要重新创建LIVEDATA
     */
    @Override
    public void toView(int action) {
        Log.e("BaseViewMode","toView>>action="+action);
        mNavLiveDate.postValue(action);
    }

    protected void showDialog(){
        mNavLiveDate.postValue(ACTION_SHOW_DIALOG);
    }

    protected void hideDialog(){
        mNavLiveDate.postValue(ACTION_HIDE_DIALOG);
    }

    /**
     * @Author Lionszhang
     * @Date 2021/2/25 9:33
     * @Instruction 子类ViewModel需要重写该方法释放资源
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCurrentModel != null) {
            mCurrentModel.onModelCleared();
        }
    }

}
