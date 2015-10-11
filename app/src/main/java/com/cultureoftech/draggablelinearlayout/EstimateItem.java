package com.cultureoftech.draggablelinearlayout;

/**
 * Created by bgogetap on 10/10/15.
 */
public class EstimateItem {

    private int groupId;
    private String title;

    String content;

    EstimateItem(int groupId, String title, String content) {
        this.groupId = groupId;
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
