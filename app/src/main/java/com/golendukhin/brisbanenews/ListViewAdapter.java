package com.golendukhin.brisbanenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class ListViewAdapter extends ArrayAdapter<BrisbaneNew> {
    private Context context;
    private List<BrisbaneNew> brisbaneNews;

    ListViewAdapter(Context context, List<BrisbaneNew> brisbaneNews) {
        super(context, 0, brisbaneNews);
        this.context = context;
        this.brisbaneNews = brisbaneNews;
    }

    /**
     * @param position used to fetch data from brisbaneNews list
     * @return rows to populate list view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_row, parent, false);
        }
        BrisbaneNew brisbaneNew = brisbaneNews.get(position);

        TextView titleTextView = convertView.findViewById(R.id.title_text_view);
        TextView sectionNameTextView = convertView.findViewById(R.id.section_new_text_view);
        TextView authorTextView = convertView.findViewById(R.id.author_text_view);
        TextView dateTextView = convertView.findViewById(R.id.date_text_view);

        titleTextView.setText(brisbaneNew.getWebTitle());
        sectionNameTextView.setText(brisbaneNew.getSectionName());
        authorTextView.setText(context.getText(R.string.author) + brisbaneNew.getAuthor());
        dateTextView.setText(brisbaneNew.getWebPublicationDate());

        return convertView;
    }
}