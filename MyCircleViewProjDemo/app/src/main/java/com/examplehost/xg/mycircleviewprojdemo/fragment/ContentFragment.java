package com.examplehost.xg.mycircleviewprojdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examplehost.xg.mycircleviewprojdemo.R;

/**
 * Created by XG on 2017/10/26.
 * author by liuchao
 */

public class ContentFragment extends Fragment {
    /**
     * 创建view时回调
     *
     * @param inflater           布局填充器
     * @param container          容器
     * @param savedInstanceState 保存实例状态
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充布局view
        View v = inflater.inflate(R.layout.content_fragment_layout, null);
        return v;
    }
}
