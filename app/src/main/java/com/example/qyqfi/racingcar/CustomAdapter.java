package com.example.qyqfi.racingcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    Spacecraft spacecraft;

    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.highscore_model, parent, false);
        }

        // Bind Data
        MyViewHolder holder = new MyViewHolder(convertView);
        holder.nameTxt.setText(spacecrafts.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, spacecrafts.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {
                spacecraft = (Spacecraft) getItem(position);
            }
        });

        return convertView;
    }

    // Expose Name and ID
    public int getSelectedItemID() {
        return spacecraft.getId();
    }

    public String getSelectedItemName() {
        return spacecraft.getName();
    }
}
