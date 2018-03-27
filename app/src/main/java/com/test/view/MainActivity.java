package com.test.view;

import com.test.view.activity.CircleBarActivity;
import com.test.view.activity.RippleViewActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.rippleViewBt)
    public void rippleViewBt() {
        startActivity(RippleViewActivity.createIntent(this));
    }

    @OnClick(R.id.CircleBarBt)
    public void CircleBarBt() {
        startActivity(CircleBarActivity.createIntent(this));
    }
}
