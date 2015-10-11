package com.cultureoftech.draggablelinearlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.group_container) com.cultureoftech.draggablelinearlayout.DragLinearLayout groupContainer;
    @Bind(R.id.scroll_view) ScrollView scrollView;

    List<GroupItem> groupItems;
    int[] groupIds = new int[]{1,2,3,4,5};
    String[] groupTitles = new String[]{"Hello", "Goodbye", "How are ya", "Pick Me", "Last"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        generateGroups();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void generateGroups() {
        groupItems = new ArrayList<>();
        for (int i = 0; i < groupIds.length; i++) {
            groupItems.add(new GroupItem(groupIds[i], groupTitles[i], "$546"));
        }
        Random random = new Random();
        for (GroupItem group : groupItems) {
            for (int i = 0; i < random.nextInt(5) + 1; i++) {
                int randomId = random.nextInt(4) + 1;
                group.estimateItems.add(new EstimateItem(randomId, groupTitles[randomId], "weeeee"));
            }
        }
        for (int i = 0; i < groupItems.size(); i++) {
            GroupItemView groupItemView = new GroupItemView(this);
            groupItemView.setGroupItem(groupItems.get(i));
            groupContainer.addView(groupItemView);
        }
//        for (int i = 0; i < groupContainer.getChildCount(); i++) {
//            GroupItemView child = (GroupItemView) groupContainer.getChildAt(i);
//            groupContainer.setViewDraggable(child, child.dragHandle);
//        }
//        groupContainer.setContainerScrollView(scrollView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
