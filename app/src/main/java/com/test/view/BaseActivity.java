package com.test.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * author：WangLei
 * date:2018/3/27.
 * QQ:619321796
 */

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = attachLayoutRes();
        if (layout != 0) {
            setContentView(layout);
            ButterKnife.bind(this);
            initView();
        }
    }

    protected abstract int attachLayoutRes();

    protected void initView() {

    }
}
