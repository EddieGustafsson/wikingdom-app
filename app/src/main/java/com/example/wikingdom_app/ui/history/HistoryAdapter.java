package com.example.wikingdom_app.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikingdom_app.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<HistoryItem> mHistoryItemArrayList;

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView cardTitle;
        public TextView cardContent;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardContent = itemView.findViewById(R.id.cardContent);
        }
    }

    public HistoryAdapter(ArrayList<HistoryItem> historyItemArrayList){
        mHistoryItemArrayList = historyItemArrayList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder hvh = new HistoryViewHolder(view);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem currentItem = mHistoryItemArrayList.get(position);

        holder.cardTitle.setText(currentItem.getCardTitle());
        holder.cardContent.setText(currentItem.getCardContent());
    }

    @Override
    public int getItemCount() {
        return mHistoryItemArrayList.size();
    }

    public static class HistoryItem {
        private String mCardTitle;
        private String mCardContent;

        public HistoryItem(String cardTitle, String cardContent){
            mCardTitle = cardTitle;
            mCardContent = cardContent;
        }

        public String getCardTitle(){
            return mCardTitle;
        }

        public String getCardContent(){
            return mCardContent;
        }
    }
}
