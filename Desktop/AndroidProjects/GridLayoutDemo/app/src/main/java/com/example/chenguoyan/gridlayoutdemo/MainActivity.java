package com.example.chenguoyan.gridlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridLayout mGridLayout;
    private int index;
    
    private View.OnLongClickListener olcl = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            view.startDrag(null, new View.DragShadowBuilder(view), null, 0);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridLayout = (GridLayout)findViewById(R.id.gridlayout);
    }

    public void addItem(View view) {

        TextView tv = new TextView(MainActivity.this);
        final int margin = 5;

        tv.setText(index + "");
        tv.setBackgroundResource(R.drawable.shape_tv_normal);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / 4 - 2 * margin;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(margin,margin,margin,margin);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(layoutParams);
        index++;

        tv.setOnLongClickListener(olcl);

        mGridLayout.addView(tv,0);
    }
}
