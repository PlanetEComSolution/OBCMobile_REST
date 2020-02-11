package com.planet.obcmobiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by planet on 3/31/16.
 */
public class Utility {
    /**/

    public static final String HEADER_WEBVIEW_URL = "https://";

    public static final String HEADER_API = "https://";
    public static final String NAMESPACE = "http://tempuri.org/";
    /*   public static final String URL_OBC_SATHI = HEADER_API + "121.241.255.225/obcnew/Shathi_App.asmx";
      public static final String URL_OBC_SATHI_NEW = HEADER_API + "121.241.255.225/obcnew/Sathi_webServices.asmx";
      public static final String URL_OBC_CMS = HEADER_API + "121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
      public static final String URL_OBC_SITE = HEADER_API + "121.241.255.225/obcnew/site/OBCComplaintsApp.asmx";
      public static final String URL_OBC_SATHI_1 = HEADER_API + "121.241.255.225/obcnew/site/OBC_OnlineApp.asmx";
      public static final String URL_OBC_SATHI_LOCATOR = HEADER_API + "121.241.255.225/obcnew/site/OBCLocatorApp.asmx";
   */
    /**/

    public static final String URL_APP_COMPLAINT = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_Complain_Online.aspx";
    // public static final String URL_UNICEL = HEADER_WEBVIEW_URL + "www.unicel.in/servxml/XML_parse_API.php?data=";// api to send otp
    public static final String URL_OLS_DETAILS = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/getolsleaddetails_mobile.aspx";
    public static final String URL_OLS_DETAILS_2 = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/getolsleaddetails.aspx";
    public static final String COMPLAINT_ONLINE_FORM_URL = HEADER_WEBVIEW_URL + "www.obcindia.co.in/module/complaint-online-form";
    public static final String QUERY_SUGGESTION_URL = HEADER_WEBVIEW_URL + "www.obcindia.co.in/content/queries-suggestion";
    public static final String URL_App_Housing_Loan = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_Housing_Loan.aspx";
    public static final String URL_App_Vehicle_Loan = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_Vehicle_Loan.aspx";
    public static final String URL_App_otherretailloan_Loans = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_otherretailloan_Loans.aspx";
    public static final String URL_App_Educations_Loan = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_Educations_Loan.aspx";
    public static final String URL_App_MSME_Loan = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_MSME_Loan.aspx";
    public static final String URL_SUGGESSTIONS = HEADER_WEBVIEW_URL + "121.241.255.225/obcnew/site/App_queries_suggestions.aspx";
    //http://121.241.255.225/obcnew/site/OBCLocatorApp.asmx
    //https://www.obcindia.co.in/module/complaint-online-form
    // https://www.unicel.in/servxml/XML_parse_API.php?data=
    // https://121.241.255.225/obcnew/Shathi_App.asmx
    // http://121.241.255.225/obcnew/site/OBCComplaintsApp.asmx
    public static String KEY_ID = "KEY_ID";
    public static String KEY_HINDI = "KEY_HINDI";
    public static String KEY_ENGLISH = "KEY_ENGLISH";
    public static String KEY_DATE = "KEY_DATE";
    private static String dtFormat = "yyyy-MM-dd HH:mm";

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static int random_number_notificationId() {
        Random r = new Random();
        int i1 = r.nextInt(500000 - 10) + 10;
        return i1;
    }
/*
    public static void setRandumNo(Context context, String RandumNo) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("RandumNo", RandumNo);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRandumNo(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString("RandumNo", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
*/

    public static String getCurrentDateTime() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dtFormat);
        String dateToStr = format.format(today);
        return dateToStr;
    }

    public static void FailedOTPCounterReset(int failedOTPCount, Context context) {


        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("failedOTPCount", String.valueOf(failedOTPCount));
            editor.putString("failedOTPTime", getCurrentDateTime());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void IncreaseFailedOTPCounter(Context context) {
        int failedOtpCount = 0;

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String a = preferences.getString("failedOTPCount", "");
            failedOtpCount = Integer.parseInt(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        failedOtpCount = failedOtpCount + 1;

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("failedOTPCount", String.valueOf(failedOtpCount));
            editor.putString("failedOTPTime", getCurrentDateTime());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static boolean CheckFailedOTPLimitExceed(Context context) {
        int failedOtpCount = 0;
        String failedOtpTime = "";
        boolean FailedOTPLimitExceed;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String a = preferences.getString("failedOTPCount", "");
            failedOtpCount = Integer.parseInt(a);
            failedOtpTime = preferences.getString("failedOTPTime", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* if (failedOtpCount == 3) {
            int hrs = TimeDifference(getCurrentDateTime(), failedOtpTime);
            if (hrs <= 1) {
                FailedOTPLimitExceed = true;
            }
        }
        */

        int hrs = TimeDifference(getCurrentDateTime(), failedOtpTime);
        if (hrs < 1) {
            if (failedOtpCount == 3) FailedOTPLimitExceed = true;
            else FailedOTPLimitExceed = false;
        } else {
            FailedOTPLimitExceed = false;
            FailedOTPCounterReset(0, context);
        }


        return FailedOTPLimitExceed;

    }

    /***************/
    public static void LoanOTPSent(boolean isLoanOTPSent, Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoanOTPSent", isLoanOTPSent);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean CheckLoanOTPSentAlready(Context context) {
        boolean otpalreadysent = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            otpalreadysent = preferences.getBoolean("isLoanOTPSent", false);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return otpalreadysent;
    }

    public static void SaveLoanAppliedTime(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("LoanAppliedTime", getCurrentDateTime());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ApplyingLoanWitin5Minutes(Context context) {

        String PreviousAppliedLoanTime = "";
        boolean ApplyingBefore5Minutes = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences.contains("LoanAppliedTime")) {
                PreviousAppliedLoanTime = preferences.getString("LoanAppliedTime", "");
                int minutes = TimeDifferenceInMinutes(getCurrentDateTime(), PreviousAppliedLoanTime);
                if (minutes < 5) {
                    ApplyingBefore5Minutes = true;
                } else {
                    ApplyingBefore5Minutes = false;
                }
            } else {
                ApplyingBefore5Minutes = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ApplyingBefore5Minutes;
    }

    /*******************/

    public static void MediclaimOTPSent(boolean isLoanOTPSent, Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isMediclaimOTPSent", isLoanOTPSent);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static boolean CheckMediclaimOTPSentAlready(Context context) {
        boolean otpalreadysent = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            otpalreadysent = preferences.getBoolean("isMediclaimOTPSent", false);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return otpalreadysent;
    }
    public static void SaveMediclaimAppliedTime(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("MediclaimAppliedTime", getCurrentDateTime());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean ApplyingMediclaimWithin5Minutes(Context context) {

        String PreviousAppliedLoanTime = "";
        boolean ApplyingBefore5Minutes = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (preferences.contains("MediclaimAppliedTime")) {
                PreviousAppliedLoanTime = preferences.getString("MediclaimAppliedTime", "");
                int minutes = TimeDifferenceInMinutes(getCurrentDateTime(), PreviousAppliedLoanTime);
                if (minutes < 5) {
                    ApplyingBefore5Minutes = true;
                } else {
                    ApplyingBefore5Minutes = false;
                }
            } else {
                ApplyingBefore5Minutes = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ApplyingBefore5Minutes;
    }

    /***************/


    public static Date StringToDate(String strDate) {

        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(dtFormat);
        try {
            date = format.parse(strDate);
            // System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int TimeDifference(String strCurrentDate, String strOldDate) {
        int hours = 0;
        try {
            Date CurrentDate = StringToDate(strCurrentDate);
            Date OldDate = StringToDate(strOldDate);
            //  long diff = CurrentDate.getTime() - OldDate.getTime();
            long diff = OldDate.getTime() - CurrentDate.getTime();
            //    long diffInHours = TimeUnit.MILLISECONDS.toHours(diff);
            // hours = (int) (diff / (60 * 60 * 1000));

            long mills = Math.abs(diff);

            hours = (int) (mills / (1000 * 60 * 60));
            int Mins = (int) (mills / (1000 * 60)) % 60;
            long Secs = (int) (mills / 1000) % 60;

        } catch (Exception e) {
            e.getMessage();
        }
        return hours;
    }

    public static int TimeDifferenceInMinutes(String strCurrentDate, String strOldDate) {
        int hours = 0;
        int Mins = 0;
        try {
            Date CurrentDate = StringToDate(strCurrentDate);
            Date OldDate = StringToDate(strOldDate);
            //  long diff = CurrentDate.getTime() - OldDate.getTime();
            long diff = OldDate.getTime() - CurrentDate.getTime();
            //    long diffInHours = TimeUnit.MILLISECONDS.toHours(diff);
            // hours = (int) (diff / (60 * 60 * 1000));

            long mills = Math.abs(diff);

            hours = (int) (mills / (1000 * 60 * 60));
            Mins = (int) (mills / (1000 * 60)) % 60;
            long Secs = (int) (mills / 1000) % 60;

        } catch (Exception e) {
            e.getMessage();
        }
        return Mins;
    }

    public static String getVersion(String htmlCode) {
        String version = "";
        try {

            String text = "<div class=\"BgcNfc\">Current Version</div><span class=\"htlgb\"><div class=\"IQ1z0d\"><span class=\"htlgb\">";
            int i = htmlCode.indexOf(text);//firsrt index of <
            int tt = (text).length();
            int k = tt + i;//>
            int j = htmlCode.indexOf("<", k);////<
            version = htmlCode.substring(k, j).trim();
        } catch (Exception e) {
            e.getMessage();
        }
        return version;
    }

}

