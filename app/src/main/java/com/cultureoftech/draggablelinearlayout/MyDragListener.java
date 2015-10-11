package com.cultureoftech.draggablelinearlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
        ViewGroup originalParent = (ViewGroup) draggingView.getParent();
        ViewGroup eventParent = (ViewGroup) v;
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (v == originalParent) {
                    draggingView.getLayoutParams().height = WRAP_CONTENT;
                    draggingView.requestLayout();
                }
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                // TODO Check for child views vs. dragged item position and animate
                int x = (int)event.getX();
                int y = (int)event.getY();
                if (eventParent.getChildCount() == 0) {
                    View view = new View(eventParent.getContext());
                    eventParent.addView(view);
                    view.getLayoutParams().height = 100;
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

    }
}
