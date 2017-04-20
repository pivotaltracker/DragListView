/*
 * Copyright (c) 2017-Present Pivotal Software, Inc. All Rights Reserved.
 */

package com.woxthebox.draglistview;

import android.view.View;

import com.woxthebox.draglistview.DragItemRecyclerView.DragItemCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class BoardViewTest {

    private BoardView subject;
    private DragItemAdapter adapter;
    private int draggingToPosition;
    private DragItemCallback callback;

    @Before
    public void setUp() {
        subject = spy(new BoardView(RuntimeEnvironment.application));
        subject.onFinishInflate();
        adapter = mock(DragItemAdapter.class);
        when(adapter.hasStableIds()).thenReturn(true);

        callback = new DragItemCallback() {
            @Override
            public boolean canDropItemAtPosition(int dropPosition) {
                return false;
            }

            @Override
            public boolean canDragItemAtPosition(int dragPosition) {
                draggingToPosition = dragPosition;
                return false;
            }
        };

        draggingToPosition = -1;
    }

    @Test
    public void addColumnList_whenDragCallbackIsSpecified_callsBackToConfiguredCallback() {
        int itemId = 234;
        int expectedPosition = 999;
        when(adapter.getPositionForItemId(itemId)).thenReturn(expectedPosition);

        DragItemRecyclerView column = subject.addColumnList(adapter, null, false, callback);
        View view = mock(View.class);
        when(view.getWidth()).thenReturn(1);
        when(view.getHeight()).thenReturn(2);

        column.startDrag(view, itemId, 0.2f, 0.4f);

        assertThat(draggingToPosition).isEqualTo(999);
    }
}
