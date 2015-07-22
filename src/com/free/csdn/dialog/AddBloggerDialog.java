package com.free.csdn.dialog;



import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.free.csdn.R;
import com.free.csdn.interfaces.DialogListener;

/**
 *修改用户密码
 */
public class AddBloggerDialog extends Dialog implements android.view.View.OnClickListener {
	
	private EditText et_userid;
	private Button btn_confirm;
	private DialogListener dialogListener;


	// 构造方法
	public AddBloggerDialog(Context context, DialogListener dialogListener) {
		super(context, R.style.Theme_Light_FullScreenDialogAct);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.dialogListener = dialogListener;
		
		// 绑定Layout
		this.setContentView(R.layout.dialog_add_blogger);
		// 初始化控件
		et_userid = (EditText) this.findViewById(R.id.et_userid);
		btn_confirm = (Button) this.findViewById(R.id.btn_confirm);
		
		btn_confirm.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			dialogListener.confirm(String.valueOf(et_userid.getText()));
			dismiss();
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 显示在底部
	 */
	public void showDialogBottom(float dimAmount){
		Window window = this.getWindow();
		window.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = dimAmount;
        window.setAttributes(lp);
		this.show();
	}
}

