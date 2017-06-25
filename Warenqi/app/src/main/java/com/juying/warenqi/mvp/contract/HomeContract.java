package com.juying.warenqi.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.juying.warenqi.app.IStatusView;
import com.juying.warenqi.mvp.model.entity.AccountInfo;
import com.juying.warenqi.mvp.model.entity.Banner;
import com.juying.warenqi.mvp.model.entity.GainedGold;
import com.juying.warenqi.mvp.model.entity.Notice;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IStatusView {

    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<AccountInfo> getGoldInfo();
        Observable<GainedGold> getGainedGold();
        Observable<Banner> getBanner();
        Observable<List<Notice>> getNotices();
    }
}