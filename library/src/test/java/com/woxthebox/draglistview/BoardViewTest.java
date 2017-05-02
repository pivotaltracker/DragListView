/*
 * Copyright (c) 2017-Present Pivotal Software, Inc. All Rights Reserved.
 */

package com.woxthebox.draglistview;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class BoardViewTest {

    private BoardView subject;
    private DragItemAdapter adapter;
    private boolean itemChangedColumn;

    @Before
    public void setUp() {
        subject = spy(new BoardView(RuntimeEnvironment.application));
        subject.onFinishInflate();
        adapter = mock(DragItemAdapter.class);
        when(adapter.hasStableIds()).thenReturn(true);
    }

    @Test
    public void addColumnList_whenColumnDragEnabled_enabledColumnDrag() {
        DragItemRecyclerView column = subject.addColumnList(adapter, mock(View.class), false, true);
        assertThat(column.isDragEnabled()).isEqualTo(true);
    }

    @Test
    public void addColumnList_whenColumnDragDisabled_disablesColumnDrag() {
        DragItemRecyclerView column = subject.addColumnList(adapter, mock(View.class), false, false);
        assertThat(column.isDragEnabled()).isEqualTo(false);
    }

    @Test
    public void addColumnList_whenColumnDragNotSpecified_setsColumnDragToBoardDragEnabled() {
        boolean dragEnabledOnBoard = subject.isDragEnabled();
        DragItemRecyclerView column = subject.addColumnList(adapter, mock(View.class), false);
        assertThat(column.isDragEnabled()).isEqualTo(dragEnabledOnBoard);
    }

    @Test
    public void updateScrollPosition_whenTargetColumnDragEnabled_changesItemColumn() {
        createColumnsAndDrag(true);

        assertThat(itemChangedColumn).isTrue();
    }

    @Test
    public void updateScrollPosition_whenTargetColumnDragDisabled_doesNotChangeItemColumn() {
        createColumnsAndDrag(false);

        assertThat(itemChangedColumn).isFalse();
    }

    private void createColumnsAndDrag(boolean toColumnDragEnabled) {
        itemChangedColumn = false;
        subject.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) { }

            @Override
            public void onItemChangedColumn(int oldColumn, int newColumn) {
                itemChangedColumn = true;
            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) { }
        });

        when(adapter.removeItem(anyInt())).thenReturn(mock(Object.class));
        DragItemRecyclerView fromColumn = subject.addColumnList(adapter, null, false, true);
        DragItemRecyclerView toColumn = subject.addColumnList(adapter, null, false, toColumnDragEnabled);
        doReturn(toColumn).when(subject).getCurrentRecyclerView(anyFloat());
        View view = mock(View.class);
        when(view.getWidth()).thenReturn(1);
        when(view.getHeight()).thenReturn(1);
        fromColumn.startDrag(view, 1L, 0.2f, 0.4f);

        subject.onAutoScrollColumnBy(1);
    }
}
