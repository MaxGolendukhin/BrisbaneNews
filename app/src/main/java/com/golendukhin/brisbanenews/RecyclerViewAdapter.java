package com.golendukhin.brisbanenews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolderNew> {
    private List<New> news;

    public RecyclerViewAdapter(List<New> news) {
        this.news = news;
    }

    @Override
    public RecyclerViewHolderNew onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RecyclerViewHolderNew(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderNew recyclerViewItemHolder, int position) {
        New currentNew = news.get(position);
        recyclerViewItemHolder.dateTextView.setText(currentNew.getWebPublicationDate().toString());
        recyclerViewItemHolder.titleTextView.setText(currentNew.getWebTitle());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class RecyclerViewHolderNew extends RecyclerView.ViewHolder {
        private TextView dateTextView, titleTextView;//было паблик

        private RecyclerViewHolderNew(View view) {//было паблик
            super(view);
            dateTextView = view.findViewById(R.id.date_text_view);
            titleTextView = view.findViewById(R.id.title_text_view);
        }
    }
}