package com.jeeson.android.mvp.demo.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jeeson.android.mvp.base.BaseHolder;
import com.jeeson.android.mvp.demo.R;
import com.jeeson.android.mvp.demo.app.DemoApp;
import com.jeeson.android.mvp.demo.mvp.model.entity.User;
import com.jeeson.android.mvp.di.component.AppComponent;
import com.jeeson.android.mvp.widget.imageloader.ImageLoader;
import com.jeeson.android.mvp.widget.imageloader.glide.GlideImageConfig;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by jess on 9/4/16 12:56
 * Contact with jess.yan.effort@gmail.com
 */
public class UserItemHolder extends BaseHolder<User> {

    @Nullable
    @BindView(R.id.iv_avatar)
    ImageView mAvater;
    @Nullable
    @BindView(R.id.tv_name)
    TextView mName;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public UserItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mAppComponent = ((DemoApp) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(User data, int position) {
        Observable.just(data.getLogin())
                .subscribe(RxTextView.text(mName));

        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(data.getAvatarUrl())
                        .imageView(mAvater)
                        .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.Application(), GlideImageConfig.builder()
                .imageViews(mAvater)
                .build());
    }
}
