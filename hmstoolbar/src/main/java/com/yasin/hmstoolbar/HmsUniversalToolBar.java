package com.yasin.hmstoolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YASIN on 07,August,2019
 * Email: yasinenubd5@gmail.com
 */
public class HmsUniversalToolBar extends Toolbar {

    public static final int ICON_MENU = 100;
    public static final int ICON_BACK = 101;
    private Toolbar toolbar;
    private ImageView backIV;
    private EditText searchET;
    private TextView titleTV;
    private LinearLayout searchBarPanel;
    private Context context;
    private OnClickListener listener;

    public HmsUniversalToolBar(Context context) {
        super(context, null);
        this.context = context;
        init(context, null);
    }

    public HmsUniversalToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);

    }

    @Override
    public void setNavigationOnClickListener(OnClickListener listener) {
        super.setNavigationOnClickListener(listener);
        this.listener = listener;
    }

    @Deprecated
    @Override
    public final void setNavigationIcon(int resId) {
    }

    private void init(final Context context, AttributeSet attributeSet) {
        inflate(context, R.layout.hms_tool_bar, this);
        toolbar = findViewById(R.id.toolbar);
        backIV = findViewById(R.id.backButtonIV);
        searchET = findViewById(R.id.searchET);
        titleTV = findViewById(R.id.header);
        searchBarPanel = findViewById(R.id.searchBarPanel);
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        toolbar.setBackgroundColor(color);
        a.recycle();

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HmsToolbar);
            try {

                if (typedArray.hasValue(R.styleable.HmsToolbar_setTitleText)) {

                    if (!typedArray.getString(R.styleable.HmsToolbar_setTitleText).isEmpty()){
                        setTittleText(typedArray.getString(R.styleable.HmsToolbar_setTitleText));
                    }else {
                        titleTV.setHint("Title");
                    }
                }
                if (typedArray.hasValue(R.styleable.HmsToolbar_enableBackButton)) {
                    boolean isEnable = typedArray.getBoolean(R.styleable.HmsToolbar_enableBackButton, false);
                    if (isEnable) {
                        backIV.setVisibility(VISIBLE);
                    } else {
                        backIV.setVisibility(GONE);
                    }
                }
                if (typedArray.hasValue(R.styleable.HmsToolbar_searchAble)) {
                    boolean isEnable = typedArray.getBoolean(R.styleable.HmsToolbar_searchAble, false);
                    if (isEnable) {
                        searchBarPanel.setVisibility(VISIBLE);
                    } else {
                        searchBarPanel.setVisibility(GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                typedArray.recycle();
            }
        }

    }

    public void setMenuOrBackIcon(int iconTypeInt) {
        if (iconTypeInt == ICON_BACK) {
            backIV.setVisibility(VISIBLE);
            backIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).onBackPressed();
                }
            });

        } else if (iconTypeInt == ICON_MENU) {
            backIV.setVisibility(VISIBLE);
            backIV.setImageResource(R.drawable.drawer_menu);
            backIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                }
            });
        }
    }

    public void setTittleText(String tittleText){
        titleTV.setText(tittleText);
    }
    public String getSearchText(){
        return searchET.getText().toString();
    }

}
