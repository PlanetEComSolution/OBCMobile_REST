package com.planet.obcmobiles.NEW_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Utility;

public class Complain_Segetions_Activity  extends AppCompatActivity implements Animation.AnimationListener {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain_segetions);
        Animation_image();
        ImageView back = (ImageView) findViewById(R.id.back);
        Button web1 = (Button) findViewById(R.id.web1);
        Button web2 = (Button) findViewById(R.id.web2);
        web1.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utility.COMPLAINT_ONLINE_FORM_URL));
    startActivity(browserIntent);
        }
        });
        web2.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Utility.QUERY_SUGGESTION_URL));
    startActivity(browserIntent);
        }
        });
        back.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        finish();
        }
        });
        }

private void Animation_image() {
        Animation animation_blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation_blink.setAnimationListener(this);
        ImageButton button_anim = (ImageButton) findViewById(R.id.imageButton2);
        button_anim.startAnimation(animation_blink);
        double r = 5 / 400.0;
        double a = 1 + r;
        a = Math.pow(a, 3);
        double l = 10000 * a - 1;
        double m = Math.pow(1 + r, -1 / 3);
        double n = l / m;
        }

@Override
public void onAnimationStart(Animation animation) {
        }
@Override
public void onAnimationEnd(Animation animation) {
        }
@Override
public void onAnimationRepeat(Animation animation) {
        }


        }
