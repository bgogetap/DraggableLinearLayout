package com.cultureoftech.draggablelinearlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by bgogetap on 10/11/15.
 */
public class MyDragListener implements View.OnDragListener {

    public static int initialItemHeight = 0;
    public static View placeholderView;

    private final Context context;
    private Drawable enterShape, normalShape;

    MyDragListener(Context context) {
        this.context = context;
        enterShape = context.getResources().getDrawable(R.drawable.ab_solid_shadow_holo);
        normalShape = context.getResources().getDrawable(R.drawable.ab_solid_shadow_holo_flipped);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        final View draggingView = (View) event.getLocalState();
        if (initialItemHeight == 0) {
            initialItemHeight = draggingView.getLayoutParams().height;
        }
        ViewGroup originalParent = (ViewGroup) draggingView.getParent();
        ViewGroup eventParent = (ViewGroup) v;
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (v == originalParent) {
                    draggingView.getLayoutParams().height = WRAP_CONTENT;
                }
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                // TODO Check for child views vs. dragged item position and animate
                int x = (int)event.getX();
                int y = (int)event.getY();
                if (placeholderView == null) {
                    placeholderView = new View(eventParent.getContext());
                }
                if (eventParent.getChildCount() == 0) {
                    if (placeholderView.getParent() != null) {
                        ((ViewGroup)placeholderView.getParent()).removeView(placeholderView);
                    }
                    eventParent.addView(placeholderView);
                    instantCollapseThenExpand(placeholderView);
                } else {
                    Log.d("View child count", "" + eventParent.getChildCount());
                    for (int i = 0; i < eventParent.getChildCount(); i++) {
                        if (placeholderView.getParent() != null) {
                            ((ViewGroup)placeholderView.getParent()).removeView(placeholderView);
                        }
                        if (!isPointInsideView(x, y, eventParent.getChildAt(i))) {
                            if (isAtUpperHalf(x, y, eventParent.getChildAt(i))) {
                                if (i > 0) {
                                    eventParent.addView(placeholderView, i - 1);
                                    instantCollapseThenExpand(placeholderView);
                                } else {
                                    eventParent.addView(placeholderView, 0);
                                    instantCollapseThenExpand(placeholderView);
                                }
                            } else {
                                if (i == eventParent.getChildCount() - 1) {
                                    eventParent.addView(placeholderView);
                                    instantCollapseThenExpand(placeholderView);
                                } else {
                                    eventParent.addView(placeholderView, i + 1);
                                    instantCollapseThenExpand(placeholderView);
                                }
                            }
                        }
                    }
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if (v == originalParent) {
                    collapse(draggingView);
                }

                v.setBackgroundDrawable(null);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                if (owner == v) {
                    view.setVisibility(View.VISIBLE);
                    break;
                }
                owner.removeView(view);
                LinearLayout container = (LinearLayout) v;
                container.addView(view);
                draggingView.setVisibility(View.VISIBLE);
                draggingView.getLayoutParams().height = WRAP_CONTENT;
                v.setBackgroundDrawable(null);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundDrawable(null);
            default:
                draggingView.getLayoutParams().height = WRAP_CONTENT;
                break;
        }

        return true;
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation collapseAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {

                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        collapseAnim.setDuration(400);
        v.startAnimation(collapseAnim);
    }

    private static void expand(final View v) {
        Animation expandAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime < 1) {
                    v.getLayoutParams().height = (int) (initialItemHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        expandAnim.setDuration(400);
        v.startAnimation(expandAnim);
    }

    private static void instantCollapseThenExpand(final View v) {
        v.getLayoutParams().height = 0;
        v.requestLayout();
        expand(v);
    }

    public static boolean isPointInsideView(float x, float y, View view){
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if(( x > viewX && x < (viewX + view.getWidth())) &&
                ( y > viewY && y < (viewY + view.getHeight()))){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAtUpperHalf(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        if (y > (viewY + (0.5 * view.getHeight()))) {
            return false;
        } else {
            return true;
        }
    }
}
