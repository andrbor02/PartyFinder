package com.example.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<String> personList;

    PersonListAdapter(Context context, List<String> personList) {
        this.personList = personList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PersonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.person_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonListAdapter.ViewHolder holder, int position) {
        String personName = personList.get(position);
        holder.personNumber.setText(String.valueOf(position + 1));
        holder.personName.setText(personName);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView personNumber, personName;
        ViewHolder(View view){
            super(view);
            personNumber = view.findViewById(R.id.person_number);
            personName = view.findViewById(R.id.person_name);
        }
    }
}