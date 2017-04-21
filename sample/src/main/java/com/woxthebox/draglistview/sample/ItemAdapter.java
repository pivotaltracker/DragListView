/*
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woxthebox.draglistview.sample;

import android.graphics.Color;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

class ItemAdapter extends DragItemAdapter<Story, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;

    ItemAdapter(ArrayList<Story> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(VH holder, int position) {
//        long itemId = getItemId(position);
//        holder.mItemId = itemId;
//        holder.itemView.setVisibility(mDragItemId == itemId ? View.INVISIBLE : View.VISIBLE);
//        holder.setDragStartCallback(mDragStartCallback);
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Story item = mItemList.get(position);
//        if (item.dropHighlight) {
//            holder.itemView.setVisibility(GONE);
//            holder.wholeItem.setVisibility(GONE);
//            holder.cardView.setVisibility(GONE);
//        } else {
//            holder.itemView.setVisibility(VISIBLE);
//            holder.wholeItem.setVisibility(VISIBLE);
//            holder.cardView.setVisibility(VISIBLE);
            holder.mText.setText(item.name);
            holder.itemView.setTag(item);
//        }

        Log.d("ItemAdapter", String.format("onBindViewHolder, item name: %s story: %s", item.name, item.dropHighlight));
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).id;
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView mText;
        CardView cardView;
        View highlightLineTop;
        View highlightLineBottom;
        View wholeItem;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            mText = (TextView) itemView.findViewById(R.id.text);
            cardView = (CardView) itemView.findViewById(R.id.card);
//            highlightLineTop = itemView.findViewById(R.id.highlight_line_top);
//            highlightLineBottom = itemView.findViewById(R.id.highlight_line_bottom);
            wholeItem = itemView.findViewById(R.id.item_layout);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
