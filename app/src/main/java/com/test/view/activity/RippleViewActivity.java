package com.test.view.activity;

import android.content.Context;
import android.content.Intent;

import com.test.view.BaseActivity;
import com.test.view.R;
import com.test.view.widget.RippleView;

import butterknife.BindView;

/**
 * author：WangLei
 * date:2018/3/27.
 * QQ:619321796
 */

public class RippleViewActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, RippleViewActivity.class);
    }

    @BindView(R.id.mRippleView)
    RippleView mRippleView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ac_ripple_view;
    }

    @Override
    protected void initView() {
        mRippleView.startAnimation();
    }
}
