package com.free.blog.ui.home.mine;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.ui.base.activity.WebActivity;
import com.free.blog.ui.base.fragment.BaseFragment;

/**
 * 个人中心
 *
 * @author tangqi
 * @since 2015年8月9日上午11:08:36
 */

public class MineFragment extends BaseFragment implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView mTitleView = (TextView) view.findViewById(R.id.tv_title);
        mTitleView.setText(R.string.user_center);

        LinearLayout mMeView01 = (LinearLayout) view.findViewById(R.id.ll_me_01);
        LinearLayout mMeView02 = (LinearLayout) view.findViewById(R.id.ll_me_02);
        LinearLayout mMeView03 = (LinearLayout) view.findViewById(R.id.ll_me_03);
        LinearLayout mMeView04 = (LinearLayout) view.findViewById(R.id.ll_me_04);
        LinearLayout mMeView05 = (LinearLayout) view.findViewById(R.id.ll_me_05);

        mMeView01.setOnClickListener(this);
        mMeView02.setOnClickListener(this);
        mMeView03.setOnClickListener(this);
        mMeView04.setOnClickListener(this);
        mMeView05.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.ll_me_01:

                break;

            case R.id.ll_me_02:
                intent = new Intent(getActivity(), BlogCollectActivity.class);
                break;

            case R.id.ll_me_03:
                intent = new Intent(getActivity(), BlogHistoryActivity.class);
                break;

            case R.id.ll_me_04:
                String link = "file:///android_asset/about/about.html";
                String name = getString(R.string.about);
                WebActivity.showWeb(getActivity(), link, name);
                break;

            case R.id.ll_me_05:
                intent = new Intent(getActivity(), SettingsActivity.class);
                break;

            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
