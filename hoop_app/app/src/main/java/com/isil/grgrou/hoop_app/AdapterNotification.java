package com.isil.grgrou.hoop_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.isil.grgrou.hoop_app.entity.Notification;
import com.isil.grgrou.hoop_app.util.Util;

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
        ImageView imgUser;
        final ImageButton btnFollow;
        // Agregar boton de follow mas tarde

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_list_users, parent, false);

        txtMessage = itemView.findViewById(R.id.txtUsername);
        imgUser = itemView.findViewById(R.id.imgUser);
        btnFollow = itemView.findViewById(R.id.btnFollow);

        txtMessage.setText(items.get(position).getMsg());
        Util.setImageWithURL(items.get(position).getUrl(), imgUser);
        btnFollow.setVisibility(View.GONE);

        return itemView;
    }
}
