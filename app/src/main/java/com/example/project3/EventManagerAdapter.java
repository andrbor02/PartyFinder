package com.example.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventManagerAdapter extends RecyclerView.Adapter<EventManagerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<String> eventList;
    private final OnEventClickListener onClickListener;

    EventManagerAdapter(Context context, List<String> eventList, OnEventClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.eventList = eventList;
        this.inflater = LayoutInflater.from(context);
    }

    interface OnEventClickListener{
        void onEventClick(String event, int position);
    }

    @Override
    public EventManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_manager_item, parent, false);
        return new EventManagerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventManagerAdapter.ViewHolder holder, int position) {
        String event = eventList.get(position);
        holder.eventName.setText(event);

        holder.eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onEventClick(event, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView eventName;
        ViewHolder(View view){
            super(view);
            eventName = view.findViewById(R.id.event_manager_item_name);
        }
    }
}