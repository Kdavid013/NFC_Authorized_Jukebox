package com.example.testdetection;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterOsszesZene extends BaseAdapter {
    List<String> items;

    public CustomAdapterOsszesZene(List<String> items) {
        super();
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(view.getContext());
        textView.setText(items.get(i));
        return textView;
    }
}
