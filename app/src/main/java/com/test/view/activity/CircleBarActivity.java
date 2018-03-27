package com.test.view.activity;

import android.content.Context;
import android.content.Intent;

import com.test.view.BaseActivity;
import com.test.view.R;
import com.test.view.widget.CircleBar;

import butterknife.BindView;

/**
 * author：WangLei
 * date:2018/3/27.
 * QQ:619321796
 */

public class CircleBarActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, CircleBarActivity.class);
    }

    @BindView(R.id.weightCB)
    CircleBar weightCB;
    @BindView(R.id.stepsCB)
    CircleBar stepsCB;
    @BindView(R.id.caloriesCB)
    CircleBar caloriesCB;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ac_circle_bar;
    }

    @Override
    protected void initView() {
        weightCB.update(70, 500);
        stepsCB.update(80, 800);
        caloriesCB.update(90, 1000);
    }
}
