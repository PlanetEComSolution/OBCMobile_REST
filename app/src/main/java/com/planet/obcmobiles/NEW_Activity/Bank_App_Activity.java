package com.planet.obcmobiles.NEW_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.planet.obcmobiles.R;

/**
 * Created by Admin on 11/9/2017.
 */

public class Bank_App_Activity extends AppCompatActivity  implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_app);
        Animation_image();
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView m_pay = (ImageView)findViewById(R.id.m_pay);
      //  ImageView upi = (ImageView)findViewById(R.id.upi);
      //  ImageView butua = (ImageView)findViewById(R.id.butua);
        ImageView shayak = (ImageView)findViewById(R.id.shayak);
        ImageView cpv = (ImageView)findViewById(R.id.cpv);
        ImageView bharat_qr = (ImageView)findViewById(R.id.bharat_qr);
        ImageView bhim_pay = (ImageView)findViewById(R.id.bhim_pay);
        ImageView aadhar_pay = (ImageView)findViewById(R.id.aadhar_pay);
        ImageView bank_rewardz = (ImageView)findViewById(R.id.bank_rewardz);

        aadhar_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "com.tcs.merchant.cags.obc";
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tcs.merchant.cags.obc" + appPackageName)));
                        }
                    }
                } catch (Exception e) {
                }
            }
        });

        bank_rewardz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appPackageName = "com.obcrewardz";
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.obcrewardz" + appPackageName)));
                        }
                    }
                } catch (Exception e) {
                }

            }
        });



        bhim_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String appPackageName = "com.mgs.obcbank";
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mgs.obcbank" + appPackageName)));
                        }
                    }
                } catch (Exception e) {
                }

            }
        });



        cpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "planet_obcapp.com.obc_kyvapp";
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=planet_obcapp.com.obc_kyvapp&hl=en" + appPackageName)));
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        shayak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = "com.mgs.ObcRecovery";
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mgs.ObcRecovery&hl=en" + appPackageName)));
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
//        butua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String appPackageName = "com.obc.orientalbatuaa";
//                try {
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
//                    if (intent != null) {
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    } else {
//                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        } catch (android.content.ActivityNotFoundException anfe) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.obc.orientalbatuaa&hl=en" + appPackageName)));
//                        }
//                    }
//                }catch (Exception e){}
//            }
//        });
//        upi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String appPackageName = "com.mgs.obcbank";
//                try {
//                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
//                    if (intent != null) {
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    } else {
//
//                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        } catch (android.content.ActivityNotFoundException anfe) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mgs.obcbank&hl=en" + appPackageName)));
//                        }
//                    }
//                }catch (Exception e){}
//            }
//        });
        m_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName ="com.fss.mobilepay.obc"; // getPackageName() from Context or Activity object
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fss.mobilepay.obc&hl=en" + appPackageName)));
                        }
                    }
                }catch (Exception e){

                }
            }
        });
        bharat_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName ="com.euronet.bharatqr"; // getPackageName() from Context or Activity object
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackageName);
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.euronet.bharatqr&hl=en" + appPackageName)));
                        }
                    }
                }catch (Exception e){

                }
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
        ImageButton button_anim = (ImageButton) findViewById(R.id.imageButton);
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
