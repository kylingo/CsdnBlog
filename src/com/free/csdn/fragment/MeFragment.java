package com.free.csdn.fragment;

import com.free.csdn.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @author tangqi
 * @data 2015年8月9日上午11:08:36
 */

public class MeFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_me, container, false);
		return view;
	}
}
