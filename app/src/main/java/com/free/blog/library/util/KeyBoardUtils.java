package com.free.blog.library.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 *
 * @author  tangqi
 * @since    2015年8月8日下午8:48:15
 */

public class KeyBoardUtils  
{  
    /** 
     * 打卡软键盘 
     *  
     * @param mEditText 
     *            输入框 
     * @param mContext 
     *            上下文 
     */
    @SuppressWarnings("unused")
    public static void openKeyboard(EditText mEditText, Context mContext)
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);  
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,  
                InputMethodManager.HIDE_IMPLICIT_ONLY);  
    }  
  
    /** 
     * 关闭软键盘 
     *  
     * @param mEditText 
     *            输入框 
     * @param mContext 
     *            上下文 
     */  
    public static void closeKeyboard(EditText mEditText, Context mContext)
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
  
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);  
    }  
}  
