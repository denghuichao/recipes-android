package com.deng.recipes.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huichaodeng on 2017/4/30.
 */
public class ExpandingListView extends RecyclerView {
    public ExpandingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandingListView(Context context) {
        super(context);
    }

    public ExpandingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
