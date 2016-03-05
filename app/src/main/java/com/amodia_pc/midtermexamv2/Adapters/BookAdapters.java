package com.amodia_pc.midtermexamv2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amodia_pc.midtermexamv2.Entities.Books;
import com.amodia_pc.midtermexamv2.R;

import java.util.List;

/**
 * Created by AMODIA-PC on 3/5/2016.
 */
public class BookAdapters extends ArrayAdapter<Books> {
    private Context context;
    private int layoutId;
    private List<Books> bookList;

    public BookAdapters(Context cont, int res, List<Books> bList) {
        super(cont, res, bList);

        context = cont;
        layoutId = res;
        bookList = bList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBookTitle = (TextView) convertView.findViewById(R.id.bookName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Books book = bookList.get(position);

        if (book != null) {
            if (viewHolder.tvBookTitle != null) {
                viewHolder.tvBookTitle.setText(book.getTitle());
                if (book.isRead()) {
                    viewHolder.tvBookTitle.setTextColor(Color.parseColor("#B0171F"));
                    viewHolder.tvBookTitle.setPaintFlags(
                            viewHolder.tvBookTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.tvBookTitle.setTextColor(Color.DKGRAY);
                    viewHolder.tvBookTitle.setPaintFlags(
                            viewHolder.tvBookTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView tvBookTitle;


    }
}
