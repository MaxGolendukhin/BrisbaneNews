package com.golendukhin.brisbanenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

class ListViewAdapter extends ArrayAdapter<New> {
    private Context context;
    private List<New> news;

    ListViewAdapter(Context context, List<New> news) {
        super(context, 0, news);
        this.context = context;
        this.news = news;
    }

    /**
     * @param position used to fetch data from news list
     * @return rows to populate list view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_row, parent, false);
        }
        New mNew = news.get(position);

        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        TextView authorTextView = listItemView.findViewById(R.id.author_text_view);
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);

        titleTextView.setText(mNew.getWebTitle());
        authorTextView.setText(context.getText(R.string.author) + mNew.getAuthor());
        dateTextView.setText(mNew.getWebPublicationDate());

        return listItemView;
    }
}