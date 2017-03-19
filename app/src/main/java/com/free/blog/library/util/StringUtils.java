package com.free.blog.library.util;

import android.text.TextUtils;

/**
 * @author studiotang on 17/3/19
 */
public class StringUtils {

    public static String trimLastChar(String string) {
        if (!TextUtils.isEmpty(string)) {
            string = string.substring(0, string.length());
        }

        return string;
    }
}
