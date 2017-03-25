/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-22 04:30:23
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.gcssloop.diycode.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gcssloop.diycode.R;
import com.gcssloop.diycode.base.app.BaseActivity;
import com.gcssloop.diycode.base.app.ViewHolder;
import com.gcssloop.diycode.utils.DataCleanManager;
import com.gcssloop.diycode.utils.FileUtil;

import java.io.File;

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        initActionBar(holder);
        showCacheSize(holder);

        if (mDiycode.isLogin()) {
            holder.get(R.id.user).setVisibility(View.VISIBLE);
        } else {
            holder.get(R.id.user).setVisibility(View.GONE);
        }

        holder.setOnClickListener(this, R.id.clear_cache, R.id.logout);
    }

    // 显示缓存大小
    private void showCacheSize(ViewHolder holder) {
        try {
            File cacheDir = new File(FileUtil.getExternalCacheDir(this));
            String cacheSize = DataCleanManager.getCacheSize(cacheDir);
            if (!cacheSize.isEmpty()) {
                holder.setText(R.id.cache_size, cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initActionBar(ViewHolder holder) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("设置");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                if (!mDiycode.isLogin())
                    return;
                mDiycode.logout();
                toastShort("退出成功");
                break;
            case R.id.clear_cache:
                DataCleanManager.deleteFolderFile(FileUtil.getExternalCacheDir(this), false);
                showCacheSize(getViewHolder());
                toastShort("清除缓存成功");
                break;
        }
    }
}
