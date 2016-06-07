package com.silencedut.expandablelayoutsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.silencedut.expandablelayout.ExpandableLayout;
import java.util.HashSet;


/**
 * Created by SilenceDut on 16/6/7.
 */
public class SummonerAdapter extends RecyclerView.Adapter<SummonerAdapter.SummonerHolder>{
    private LayoutInflater mInflater;
    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();
    public SummonerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public SummonerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.inflate(viewType==0?R.layout.item_ezreal:R.layout.item_jinx,parent,false);
        return new SummonerHolder(item);
    }

    @Override
    public void onBindViewHolder(SummonerHolder holder, int position) {
        holder.updateItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class SummonerHolder extends RecyclerView.ViewHolder {
        private ExpandableLayout expandableLayout ;
        private SummonerHolder(final View itemView) {
            super(itemView);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        }

         private void updateItem(final int position) {
             expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
            expandableLayout.setExpand(mExpandedPositionSet.contains(position));

        }
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        }else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }

}
