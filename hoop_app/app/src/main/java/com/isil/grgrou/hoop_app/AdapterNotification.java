package com.isil.grgrou.hoop_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isil.grgrou.hoop_app.entity.Notification;

import java.util.ArrayList;

public class AdapterNotification extends BaseAdapter {

    private Context context;
    private ArrayList<Notification> items;

    public AdapterNotification(Context context, ArrayList<Notification> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtMessage;
        ImageView imgProfile;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_notification, parent, false);

        txtMessage = itemView.findViewById(R.id.txtUsername);

        txtMessage.setText(items.get(position).getMsg());

        return itemView;
    }
}
