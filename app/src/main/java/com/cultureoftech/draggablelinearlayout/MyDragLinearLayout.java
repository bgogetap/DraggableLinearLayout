package com.cultureoftech.draggablelinearlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by bgogetap on 10/11/15.
 */
public class MyDragLinearLayout extends LinearLayout {

    private static final long NOMINAL_SWITCH_DURATION = 150;
    private static final long MIN_SWITCH_DURATION = NOMINAL_SWITCH_DURATION;
    private static final long MAX_SWITCH_DURATION = NOMINAL_SWITCH_DURATION * 2;
    private static final float NOMINAL_DISTANCE = 20;
    private final float nominalDistanceScaled;

    private final int slop;

    private static final int INVALID_POINTER_ID = -1;
    private int downY = -1;
    private int activePointerId = INVALID_POINTER_ID;

    /**
     * The shadow to be drawn above the {@link #draggedItem}.
     */
    private final Drawable dragTopShadowDrawable;
    /**
     * The shadow to be drawn below the {@link #draggedItem}.
     */
    private final Drawable dragBottomShadowDrawable;
    private final int dragShadowHeight;

    /**
     * See {@link #setContainerScrollView(android.widget.ScrollView)}.
     */
    private ScrollView containerScrollView;
    private int scrollSensitiveAreaHeight;
    private static final int DEFAULT_SCROLL_SENSITIVE_AREA_HEIGHT_DP = 48;
    private static final int MAX_DRAG_SCROLL_SPEED = 16;

    List<LinearLayout> groupLayouts;
    List<LinearLayout> estimateLayouts;

    public MyDragLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);

        ViewConfiguration vc = ViewConfiguration.get(context);
        slop = vc.getScaledTouchSlop();

        final Resources resources = getResources();
        dragTopShadowDrawable = ContextCompat.getDrawable(context, R.drawable.ab_solid_shadow_holo_flipped);
        dragBottomShadowDrawable = ContextCompat.getDrawable(context, R.drawable.ab_solid_shadow_holo);
        dragShadowHeight = resources.getDimensionPixelSize(R.dimen.downwards_drop_shadow_height);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DragLinearLayout, 0, 0);
        try {
            scrollSensitiveAreaHeight = a.getDimensionPixelSize(R.styleable.DragLinearLayout_scrollSensitiveHeight,
                    (int) (DEFAULT_SCROLL_SENSITIVE_AREA_HEIGHT_DP * resources.getDisplayMetrics().density + 0.5f));
        } finally {
            a.recycle();
        }

        nominalDistanceScaled = (int) (NOMINAL_DISTANCE * resources.getDisplayMetrics().density + 0.5f);
    }
}
