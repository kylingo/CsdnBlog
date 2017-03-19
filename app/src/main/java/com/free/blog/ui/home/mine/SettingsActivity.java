package com.free.blog.ui.home.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.blog.BlogApplication;
import com.free.blog.R;
import com.free.blog.data.remote.NetEngine;
import com.free.blog.library.config.CacheManager;
import com.free.blog.library.util.FileUtils;
import com.free.blog.library.util.NetUtils;
import com.free.blog.library.util.ToastUtil;
import com.free.blog.library.util.VersionUtils;
import com.free.blog.library.view.dialog.BaseDialog;
import com.free.blog.library.view.dialog.LoadingDialog;
import com.free.blog.library.view.dialog.SelectionDialog;
import com.free.blog.ui.base.BaseActivity;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 设置
 *
 * @author tangqi
 * @since 2015年8月15日下午6:35:57
 */

public class SettingsActivity extends BaseActivity implements OnClickListener {
    /**
     * SD卡缓存大小
     */
    private long mExternalCacheSize;

    /**
     * 外部缓存目录
     */
    private File mExternalCacheFile;

    /**
     * 缓存数据View
     */
    private TextView mTvCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView btnBack = (ImageView) findViewById(R.id.btn_back);
        mTvCacheSize = (TextView) findViewById(R.id.tv_cache_size);
        TextView tvVersionName = (TextView) findViewById(R.id.tv_version_name);
        LinearLayout llCheckUpgrade = (LinearLayout) findViewById(R.id.ll_settings_check_upgrade);
        LinearLayout llClearCache = (LinearLayout) findViewById(R.id.ll_settings_clear_cache);
        LinearLayout llUserFeedback = (LinearLayout) findViewById(R.id.ll_settings_user_feedback);
        LinearLayout llUpdateLog = (LinearLayout) findViewById(R.id.ll_settings_update_log);
        LinearLayout llShareApp = (LinearLayout) findViewById(R.id.ll_settings_share_app);
        LinearLayout llSettingsContactUs = (LinearLayout) findViewById(R.id.ll_settings_contact_us);
        LinearLayout llSettingsExit = (LinearLayout) findViewById(R.id.ll_settings_exit);

        tvTitle.setText(R.string.settings);
        tvVersionName.setText(String.format("%s%s", getString(R.string.settings_now_version),
                VersionUtils.getVersionName(this)));
        btnBack.setOnClickListener(this);
        btnBack.setVisibility(View.VISIBLE);
        llCheckUpgrade.setOnClickListener(this);
        llClearCache.setOnClickListener(this);
        llUserFeedback.setOnClickListener(this);
        llUpdateLog.setOnClickListener(this);
        llShareApp.setOnClickListener(this);
        llSettingsContactUs.setOnClickListener(this);
        llSettingsExit.setOnClickListener(this);

        updateData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // 返回
            case R.id.btn_back:
                finish();
                break;

            // 检查更新
            case R.id.ll_settings_check_upgrade:
                checkUpgrade();
                break;

            // 清除缓存
            case R.id.ll_settings_clear_cache:
                clearCache();
                break;

            // 用户反馈
            case R.id.ll_settings_user_feedback:
                feedback();
                break;

            // 更新日志
            case R.id.ll_settings_update_log:
                updateLog();
                break;

            // 分享好友
            case R.id.ll_settings_share_app:
                shareApp();
                break;

            // 关于我们
            case R.id.ll_settings_contact_us:
                ContactUs();
                break;

            // 退出
            case R.id.ll_settings_exit:
                exit();
                break;

            default:
                break;
        }
    }

    /**
     * 更新数据
     */
    private void updateData() {
        mExternalCacheFile = new File(CacheManager.getExternalCachePath(this));
        Observable.just(mExternalCacheFile)
                .map(new Func1<File, Long>() {
                    @Override
                    public Long call(File file) {
                        return FileUtils.getFileSize(file);
                    }
                })
                .compose(NetEngine.<Long>getErrAndIOSchedulerTransformer())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long result) {
                        mExternalCacheSize = result;
                        mTvCacheSize.setText(FileUtils.formatSize(result));
                    }
                });
    }

    /**
     * 检查更新
     */
    private void checkUpgrade() {
        if (NetUtils.isNetAvailable(this)) {
            UmengUpdateAgent.forceUpdate(this);
            UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                @Override
                public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                    switch (updateStatus) {
                        case UpdateStatus.Yes:
                            ToastUtil.show(SettingsActivity.this, "软件有更新");
                            break;

                        case UpdateStatus.No:
                            ToastUtil.show(SettingsActivity.this, "没有更新");
                            break;

                        case UpdateStatus.NoneWifi:
                            ToastUtil.show(SettingsActivity.this, "没有wifi连接， 只在wifi下更新");
                            break;

                        case UpdateStatus.Timeout:
                            ToastUtil.show(SettingsActivity.this, "连接超时");
                            break;
                    }
                }
            });
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        SelectionDialog dialog = new SelectionDialog(this, "确定清除" + FileUtils.formatSize(mExternalCacheSize) + "缓存吗？");
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {

            @Override
            public void onConfirm(String result) {
                final LoadingDialog loadingDialog = new LoadingDialog(SettingsActivity.this, "正在清理缓存");
                loadingDialog.show();

                doClearCache(loadingDialog);
            }
        });
        dialog.show();
    }

    private void doClearCache(final Dialog dialog) {
        Observable.just(mExternalCacheFile)
                .map(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return FileUtils.delete(file);
                    }
                })
                .compose(NetEngine.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            ToastUtil.showCenter(SettingsActivity.this, "清理成功");
                            updateData();
                        }
                    }
                });
    }

    /**
     * 用户反馈
     */
    private void feedback() {
        // FeedbackAgent agent = new FeedbackAgent(this);
        // agent.startFeedbackActivity();

        FeedbackAgent fb = new FeedbackAgent(this);
        // check if the app developer has replied to the feedback or not.
        fb.sync();
        fb.openFeedbackPush();

        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    /**
     * 更新日志
     */
    private void updateLog() {
        startActivity(UpdateLogActivity.class);
    }

    /**
     * 分享好友
     */
    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "CSDN博客之星，非常好用，赶紧去各大应用市场下载吧。");
        startActivity(Intent.createChooser(intent, "CSDN博客分享"));
    }

    /**
     * 关于我们
     */
    private void ContactUs() {
        startActivity(ContactUsActivity.class);
    }

    /**
     * 退出
     */
    private void exit() {
        SelectionDialog dialog = new SelectionDialog(this, getString(R.string.confirm_to_exit));
        dialog.setOnConfirmListener(new BaseDialog.OnConfirmListener() {

            @Override
            public void onConfirm(String result) {
                BlogApplication.getInstance().onTerminate();
            }
        });
        dialog.show();
    }

    /**
     * 启动Activity
     */
    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
