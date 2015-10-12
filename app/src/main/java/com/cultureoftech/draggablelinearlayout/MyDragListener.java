package com.cultureoftech.draggablelinearlayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by bgogetap on 10/11/15.
 */
public class MyDragListener implements View.OnDragListener {

    public static int initialItemHeight = 0;
    public static View placeholderView;
    public static List<View> animatingViews = new ArrayList<>();
    private ViewGroup originalViewGroup = null;
    private AnimatingItem animatingItem;

    private final Context context;
    private Drawable enterShape, normalShape;

    private class AnimatingItem {
        final View view;
        ObjectAnimator animator;

        AnimatingItem(View view) {
            this.view = view;
        }
    }

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
        if (originalViewGroup == null) {
            originalViewGroup = (ViewGroup) draggingView.getParent();
        }
        ViewGroup eventParent = (ViewGroup) v;
        final int draggingViewPosition = eventParent.indexOfChild(draggingView);
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (draggingView.getParent() == null) {
                    if (y < eventParent.getHeight() / 2) {
                        eventParent.addView(draggingView, 0);
                    } else {
                        eventParent.addView(draggingView);
                    }
                }
                if (v == originalParent) {
                    int abovePosition = draggingViewPosition - 1;
                    int belowPosition = draggingViewPosition + 1;
                    View aboveView = null, belowView = null;
                    if (abovePosition < eventParent.getChildCount() - 1) {
                        aboveView = eventParent.getChildAt(abovePosition);
                    }
                    Log.d("Drag App", "draggingViewPosition: " + draggingViewPosition);
                    Log.d("Drag App", "belowViewPosition: " + belowPosition);
                    if (belowPosition < originalParent.getChildCount() - 1) {
                        belowView = originalParent.getChildAt(belowPosition);
                    }
                    boolean isNull = belowView == null;
                    Log.d("Drag App", "below view null: " + isNull);

                    boolean viewAbove = (aboveView != null) &&
                            (event.getY() > aboveView.getY() + (aboveView.getHeight() / 2)) &&
                            (event.getY() < aboveView.getY() + aboveView.getHeight());
                    boolean viewBelow = (belowView != null) &&
                            (event.getY() > belowView.getY() + (belowView.getHeight() / 2)) &&
                            (event.getY() < belowView.getY() + belowView.getHeight());

                    if (viewAbove || viewBelow) {
                        final View swapView = viewAbove ? aboveView : belowView;
                        int swapPosition = viewAbove ? abovePosition : belowPosition;
                        final float swapViewStartY = swapView.getY();
                        eventParent.removeView(swapView);
                        eventParent.removeView(draggingView);
                        eventParent.addView(draggingView, swapPosition);
                        eventParent.addView(swapView, draggingViewPosition);

//                        if (animatingItem == null || animatingItem.animator == null) {
//                            animatingItem = new AnimatingItem(swapView);
//                            final ViewTreeObserver switchObserver = swapView.getViewTreeObserver();
//                            switchObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                                @Override
//                                public boolean onPreDraw() {
//                                    switchObserver.removeOnPreDrawListener(this);
//
//                                    final ObjectAnimator swapAnimator = ObjectAnimator.ofFloat(swapView, "y",
//                                            swapViewStartY, swapView.getTop())
//                                            .setDuration(400);
//                                    swapAnimator.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationStart(Animator animation) {
//
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            animatingItem.animator = null;
//                                        }
//                                    });
//                                    animatingItem.animator = swapAnimator;
//                                    swapAnimator.start();
//
//                                    return true;
//                                }
//                            });
//                        }

                    }
                } else {
                    if (placeholderView == null) {
                        placeholderView = new View(eventParent.getContext());
                    }
                    if (eventParent.getChildCount() == 0) {

                    }
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if (v == originalParent) {
                    originalParent.removeView(draggingView);
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
                if (!event.getResult()) {
                    if (draggingView.getParent() != null) {
                        ((ViewGroup) draggingView.getParent()).removeView(draggingView);
                    }
                    draggingView.setVisibility(View.VISIBLE);
                    originalViewGroup.addView(draggingView);
                    originalViewGroup = null;
                }
            default:
                draggingView.getLayoutParams().height = WRAP_CONTENT;
                break;
        }

        return true;
    }

    private static void animateBottomMargin(final View view) {
        Animation topMarginAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.bottomMargin = (int) (initialItemHeight * interpolatedTime);
                view.setLayoutParams(params);
                view.requestLayout();
                Log.d("Bottom Margin", params.bottomMargin + "");
            }
        };
        topMarginAnim.setDuration(400);
        view.startAnimation(topMarginAnim);
    }

    private static void animateTopMargin(final View view) {
        Animation topMarginAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = (int) (initialItemHeight * interpolatedTime);
                view.setLayoutParams(params);
                view.requestLayout();
            }
        };
        topMarginAnim.setDuration(400);
        view.startAnimation(topMarginAnim);
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

    public static boolean isPointInsideView(float x, float y, View view) {
        int viewX = (int) view.getX();
        int viewY = (int) view.getY();

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAtUpperHalf(float x, float y, View view) {
        int viewX = (int) view.getX();
        int viewY = (int) view.getY();

        if (y > viewY && y < viewY + (view.getHeight() / 2)) {
            return true;
        }

        return false;
    }
}
