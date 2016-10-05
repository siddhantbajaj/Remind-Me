package com.example.siddhant.remindme;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by ABC on 16-07-2016.
 */
public class CustExpListview extends ExpandableListView {
    public CustExpListview(Context context) {
        super(context);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
        //widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.UNSPECIFIED);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
