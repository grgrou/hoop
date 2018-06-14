package com.isil.grgrou.hoop_app.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.isil.grgrou.hoop_app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Alumno-DG on 30/04/2018.
 */

public class Util {

    static public void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    static public void animate(Techniques techniques, View view, int duration) {
        YoYo.with(techniques)
                .duration(duration)
                .playOn(view);
    }

    static public void setImageWithURL(String url, ImageView image) {
        Picasso.get().load(url).into(image);
    }

}
