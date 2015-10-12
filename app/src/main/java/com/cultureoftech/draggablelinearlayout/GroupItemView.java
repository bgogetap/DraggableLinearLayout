package com.cultureoftech.draggablelinearlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bgogetap on 10/10/15.
 */
public class GroupItemView extends LinearLayout {

    @Bind(R.id.child_container) LinearLayout childContainer;
    @Bind(R.id.group_title) TextView groupTitle;
    @Bind(R.id.group_total) TextView groupTotal;
    @Bind(R.id.drag_handle) View dragHandle;

    GroupItem groupItem;

    public GroupItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.group_item, this, true);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);
    }

    void setGroupItem(GroupItem groupItem) {
        this.groupItem = groupItem;
        groupTitle.setText(groupItem.getTitle());
        groupTotal.setText(groupItem.getTotal());
        addChildren();
    }

    private void addChildren() {
        for (EstimateItem estimate : groupItem.estimateItems) {
            EstimateItemView estimateView = new EstimateItemView(getContext());
            estimateView.setEstimate(estimate);
            childContainer.addView(estimateView);
        }
        childContainer.setOnDragListener(new MyDragListener(getContext()));

//        for (int i = 0; i < childContainer.getChildCount(); i++) {
//            childContainer.setViewDraggable(childContainer.getChildAt(i), ((EstimateItemView)childContainer.getChildAt(i)).dragHandle);
//        }
    }


}
