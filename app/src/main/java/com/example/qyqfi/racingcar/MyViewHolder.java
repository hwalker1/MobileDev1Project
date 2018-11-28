package com.example.qyqfi.racingcar;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener {
    TextView nameTxt;
    MyLongClickListener longClickListener;

    public MyViewHolder(View convertView) {
        nameTxt = (TextView) convertView.findViewById(R.id.nameTxt);

        convertView.setOnLongClickListener(this);
        convertView.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onItemLongClick();
        return false;
    }

    public void setLongClickListener(MyLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Action: ");
        menu.add(0, 0, 0, "NEW");
        menu.add(0, 1, 0, "EDIT");
        menu.add(0, 2, 0, "DELETE");


    }
}
