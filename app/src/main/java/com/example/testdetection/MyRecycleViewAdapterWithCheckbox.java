package com.example.testdetection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MyRecycleViewAdapterWithCheckbox extends RecyclerView.Adapter<MyRecycleViewAdapterWithCheckbox.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ModelClass> mData;

    private ArrayList<String> kivalasztottak = new ArrayList<>();

    public MyRecycleViewAdapterWithCheckbox(LayoutInflater mInflater, List<ModelClass> mData) {
        this.mInflater = mInflater;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyRecycleViewAdapterWithCheckbox.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_layout_with_checkbox,parent,false);
        return new MyRecycleViewAdapterWithCheckbox.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapterWithCheckbox.ViewHolder holder, int position) {
        ModelClass modelClass = mData.get(position);
        holder.myTextView.setText(modelClass.getName());
        holder.checkBox.setChecked(modelClass.isSelected());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String listItemSelected(){
        String result = "";
        StringBuilder listElemekOsszeSzedese = new StringBuilder("&zenek=");
        for (String e :kivalasztottak) {
            listElemekOsszeSzedese.append(e).append(";");
        }
        result = listElemekOsszeSzedese.toString();
        return result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView myTextView;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.text_view_for_list_item);
            checkBox = itemView.findViewById(R.id.list_item_checkbox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked =  ((CheckBox)v).isChecked();

                    if(isChecked){
                        mData.get(getAdapterPosition()).setSelected(true);
                        kivalasztottak.add(mData.get(getAdapterPosition()).getId());
                    }
                    else {
                        mData.get(getAdapterPosition()).setSelected(false);
                        kivalasztottak.remove(mData.get(getAdapterPosition()).getId());
                    }
                }
            });
        }
    }
}
