package com.juying.warenqi.mvp.contract;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.juying.warenqi.mvp.model.entity.TaskCount;

import io.reactivex.Observable;


public interface TaskCenterContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setAdapter(BaseQuickAdapter adapter);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<TaskCount> getTaskCount();
    }
}