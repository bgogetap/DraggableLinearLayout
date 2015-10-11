package com.cultureoftech.draggablelinearlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bgogetap on 10/10/15.
 */
public class GroupItem {

    List<EstimateItem> estimateItems;
    private String title;
    private String total;
    private int id;

    GroupItem(int id, String title, String total) {
        this.id = id;
        this.title = title;
        this.total = total;
        estimateItems = new ArrayList<>();
    }

    public List<EstimateItem> getEstimateItems() {
        return estimateItems;
    }

    public void setEstimateItems(List<EstimateItem> estimateItems) {
        this.estimateItems = estimateItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
