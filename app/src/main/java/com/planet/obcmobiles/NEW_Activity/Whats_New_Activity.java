package com.planet.obcmobiles.NEW_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.planet.obcmobiles.Expadaiable;
import com.planet.obcmobiles.R;

public class Whats_New_Activity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats__new_);
        Animation_image();
        ImageView back = (ImageView) findViewById(R.id.back);
        Button _rewardz = (Button) findViewById(R.id._rewardz);
        Button investment = (Button) findViewById(R.id.investment);
        _rewardz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Whats_New_Activity.this, Expand_Loan_Activity.class);
                intent.putExtra("position_main", "85");
                intent.putExtra("Name", "Oriental Bank Rewardz");
                intent.putExtra("position", "2");
                intent.putExtra("name", "Digital Banking");
                startActivity(intent);
            }
        });
        investment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Whats_New_Activity.this, Expand_Loan_Activity.class);
                intent.putExtra("position_main", "74");
                intent.putExtra("Name", "Mutual Fund");
                intent.putExtra("position", "1");
                intent.putExtra("hide", "2");
                intent.putExtra("name", "Branch Business");
                startActivity(intent);
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
