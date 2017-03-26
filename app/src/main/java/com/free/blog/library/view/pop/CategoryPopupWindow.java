package com.free.blog.library.view.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.free.blog.R;
import com.free.blog.model.entity.BlogCategory;
import com.free.blog.ui.list.BlogCategoryAdapter;

import java.util.List;

/**
 * @author studiotang on 17/3/26
 */
public class CategoryPopupWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private AdapterView.OnItemClickListener mOnItemClickListener;

    @SuppressWarnings("deprecation")
    public CategoryPopupWindow(Context context, List<BlogCategory> categoryList) {
        super(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        @SuppressLint("InflateParams")
        View contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_bloglist, null);
        setContentView(contentView);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        ListView listView = (ListView) contentView.findViewById(R.id.lv_blog_type);
        BlogCategoryAdapter adapter = new BlogCategoryAdapter(context, categoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showAsDropDown(View view) {
        int xOffset = (int) ((int) view.getResources().getDimension(R.dimen.popwindow_bloglist_width)
                - view.getResources().getDimension(R.dimen.share_bar_height));
        showAsDropDown(view, (-1) * xOffset, 0);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dismiss();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(adapterView, view, i, l);
        }
    }
}
