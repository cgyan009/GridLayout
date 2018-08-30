package com.example.chenguoyan.gridlayoutdemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout mGridLayout;
    private int index;
    private View dragedView;
    private DragableGridLayout mDragableGridlayout;

    private View.OnLongClickListener olcl = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            dragedView = view;
            view.startDrag(null, new View.DragShadowBuilder(view), null, 0);
            view.setEnabled(false);
            System.out.println("long click-----" + view.toString());
            return false;
        }
    };
    private View.OnDragListener odl = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            String dragEventAction = getDragEventAction(dragEvent);

            System.out.println("on drag*******");
//            System.out.println(dragEventAction);
//            Rect rect = new Rect();
//            rect.contains();
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    initRects();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    int touchIndex = getTouchIndex(dragEvent);
                    if (touchIndex > -1 && dragedView != null &&
                            dragedView != mGridLayout.getChildAt(touchIndex)) {
                        System.out.println("index " + touchIndex);
                        mGridLayout.removeView(dragedView);
                        mGridLayout.addView(dragedView, touchIndex);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (dragedView != null) {
                        dragedView.setEnabled(true);
                    }
                    break;
            }
            return true;
        }
    };
    private DragableGridLayout mHideGridlayout;

    private int getTouchIndex(DragEvent dragEvent) {
        for (int i = 0; i < mRects.length; i++) {
            Rect rect = mRects[i];
            System.out.println("X:" + (int) dragEvent.getX() + "--" + (int) dragEvent.getY());
            if (rect.contains((int) dragEvent.getX(), (int) dragEvent.getY())) {
                System.out.println("Got i:" + i);
                return i;
            }
        }
        return -1;
    }

    private Rect[] mRects;

    private void initRects() {
        mRects = new Rect[mGridLayout.getChildCount()];
        for (int i = 0; i < mGridLayout.getChildCount(); i++) {
            View childView = mGridLayout.getChildAt(i);
            Rect rect = new Rect(childView.getLeft(),
                    childView.getTop(),
                    childView.getRight(),
                    childView.getBottom()
            );
            mRects[i] = rect;
        }
        for (int i = 0; i < mRects.length; i++) {

            System.out.println("======" + mRects[i].toString());

        }
    }

    static SparseArray<String> dragEventType = new SparseArray<String>();

    static {
        dragEventType.put(DragEvent.ACTION_DRAG_STARTED, "STARTED");
        dragEventType.put(DragEvent.ACTION_DRAG_ENDED, "ENDED");
        dragEventType.put(DragEvent.ACTION_DRAG_ENTERED, "ENTERED");
        dragEventType.put(DragEvent.ACTION_DRAG_EXITED, "EXITED");
        dragEventType.put(DragEvent.ACTION_DRAG_LOCATION, "LOCATION");
        dragEventType.put(DragEvent.ACTION_DROP, "DROP");
    }

    public static String getDragEventAction(DragEvent de) {
        return dragEventType.get(de.getAction());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridLayout = (GridLayout) findViewById(R.id.gridlayout);
        mDragableGridlayout = (DragableGridLayout) findViewById(R.id.dragablegridlayout);
        List<String> titles = new ArrayList<>();
        titles.add("上海站");
        titles.add("昆山南站");
        titles.add("苏州站");
        titles.add("无锡站");
        titles.add("常州站");
        titles.add("丹阳站");
        titles.add("镇江站");
        titles.add("南京南站");
        mDragableGridlayout.setAllowDrag(true);
        mDragableGridlayout.setItems(titles);
        mHideGridlayout = findViewById(R.id.hidegridlayout);
        List<String> titles2 = new ArrayList<>();
        titles2.add("上海站");
        titles2.add("昆山南站");
        titles2.add("苏州站");
        titles2.add("无锡站");
        titles2.add("常州站");
        titles2.add("丹阳站");
        titles2.add("镇江站");
        titles2.add("南京南站");
        mHideGridlayout.setAllowDrag(false);
        mHideGridlayout.setItems(titles2);
    }

    public void addItem(View view) {

        TextView tv = new TextView(MainActivity.this);
        final int margin = 5;

        tv.setText(index + "");
        tv.setBackgroundResource(R.drawable.selector_tv_bg);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / 4 - 2 * margin;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(margin, margin, margin, margin);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(layoutParams);

        index++;

        tv.setOnLongClickListener(olcl);
        tv.setOnDragListener(odl);
        mGridLayout.addView(tv, 0);

    }
}
