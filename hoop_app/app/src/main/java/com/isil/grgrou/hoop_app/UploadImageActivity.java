package com.isil.grgrou.hoop_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.isil.grgrou.hoop_app.util.Util;

public class UploadImageActivity extends AppCompatActivity {

    RelativeLayout rootLayout, viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        rootLayout = findViewById(R.id.rootLayout);
        viewContainer = findViewById(R.id.viewContainer);

        YoYo.with(Techniques.FadeIn).duration(700).playOn(rootLayout);
        YoYo.with(Techniques.SlideInUp).duration(700).playOn(viewContainer);

        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(5)
                .playOn(findViewById(R.id.viewContainer));

        Util.toast(getApplicationContext(), "POP SOCKET");
    }

}
