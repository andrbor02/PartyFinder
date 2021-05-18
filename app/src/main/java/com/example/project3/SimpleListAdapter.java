package com.example.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ItemOfList> simpleList;

    SimpleListAdapter(Context context, List<ItemOfList> simpleList) {
        this.simpleList = simpleList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public SimpleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.simple_list_item, parent, false);
        return new SimpleListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleListAdapter.ViewHolder holder, int position) {
        ItemOfList itemOfList = simpleList.get(position);
        holder.itemName.setText(itemOfList.getName());
        holder.itemValue.setText(itemOfList.getValue());
    }

    @Override
    public int getItemCount() {
        return simpleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView itemName, itemValue;
        ViewHolder(View view){
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemValue = view.findViewById(R.id.item_value);
        }
    }
}