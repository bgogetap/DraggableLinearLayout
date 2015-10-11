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
public class EstimateItemView extends LinearLayout {

    @Bind(R.id.tv_title) TextView title;
    @Bind(R.id.tv_content) TextView content;
    @Bind(R.id.drag_handle) View dragHandle;

    EstimateItem estimate;

    public EstimateItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.estimate_item, this, true);
        ButterKnife.bind(this);
        setPadding(20,20,20,20);
    }

    void setEstimate(EstimateItem estimate) {
        this.estimate = estimate;
        title.setText(estimate.getTitle());
        content.setText(estimate.getContent());
    }
}
