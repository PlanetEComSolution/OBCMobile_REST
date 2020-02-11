package com.planet.obcmobiles.Sqlite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

import static com.planet.obcmobiles.Utility.KEY_DATE;
import static com.planet.obcmobiles.Utility.KEY_ENGLISH;
import static com.planet.obcmobiles.Utility.KEY_HINDI;
import static com.planet.obcmobiles.Utility.KEY_ID;


public class GetData {

    Databaseutill db;

    //	Webservicerequest encrypt;
    Context cnt;
    static int mobile_count = 0;

    public GetData(Databaseutill data, Context cv) {

        db = data;

        //	encrypt = new Webservicerequest();

        cnt = cv;
    }



public void setUsernameAndPassword(String userName, String password){

    try{
        db.createDatabase();
        db.openDataBase();
        String qqqq =  "create table if not exists saveUserNameAndPassword(userName varchar(50),password varchar(50))";

        String result= db.execQuery(qqqq);
        if(result.equalsIgnoreCase("1")) {

            String query = "insert into saveUserNameAndPassword (userName,password) values ('"
                    + userName
                    + "','"
                    + password + "')";

            String res = db.execQuery(query);
        }
        db.close();

    }catch (Exception e){}

}
public void Delete_record(){
    try{
        db.createDatabase();
        db.openDataBase();
     int a =   db.deleteRecordFromDB("saveSync_Data",null,null);
      int s=0;

    }catch (Exception e){
e.getMessage();
    }}

    public void Delete_record_CRG(){
        try{
            db.createDatabase();
            db.openDataBase();
            int a =   db.deleteRecordFromDB("USERRecordCRG",null,null);
            int s=0;

        }catch (Exception e){
            e.getMessage();
        }}

    public void Delete_record_CRGOPEN(){
        try{
            db.createDatabase();
            db.openDataBase();
            int a =   db.deleteRecordFromDB("saveSync_Data",null,null);
            int s=0;

        }catch (Exception e){
            e.getMessage();
        }}

    public void setUSERRecord(String SubHeadingId, String SubHeadingName) {
        try {
            db.createDatabase();
            // Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
          // db.deleteRecordFromDB("USERRecord",null,null);


            // read from local tables

            String query1 = "create table if not exists USERRecord (SubHeadingId varchar(50),SubHeadingName varchar(50)))";

            String result= db.execQuery(query1);
            if(result.equalsIgnoreCase("1")) {

                String query = "insert into USERRecord (date_visit,unique_no) values ('"+ SubHeadingId


                        + "','"
                        + SubHeadingName+ "')";

                String res = db.execQuery(query);
            }
            db.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
//    public ArrayList<HashMap<String,String>> getUSERRecord() {
//        String str = "";
//
//        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
//        try {
//
//            db.openDataBase();
//            // read from local tables
//            String query = "select * from USERRecord";
//            Cursor cur = db.selectData(query);
//
//            if (cur != null) {
//
//                cur.moveToFirst();
//
//                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
//                    HashMap<String,String> hashMap=new HashMap<>();
//                    /*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
//                        str = cur.getString(colCount);
//                    }*/
//                    hashMap.put(KEY_DATE_VISIT,cur.getString(0));
//                    hashMap.put(KEY_ID,cur.getString(1));
//                    hashMap.put(KEY_VISITER_NAME,cur.getString(2));
//                    hashMap.put(KEY_PF_NO,cur.getString(3));
//                    hashMap.put(KEY_SALUTATION,cur.getString(4));
//                    hashMap.put(KEY_PROPRIETOR_NAME,cur.getString(5));
//                    hashMap.put(KEY_AADHAR_NO,cur.getString(6));
//                    hashMap.put(KEY_FIRM_NAME,cur.getString(7));
//                    hashMap.put(KEY_CURRENT_DATE,cur.getString(8));
//                    hashMap.put(KEY_ACCOUNT_NO,cur.getString(9));
//                    hashMap.put(KEY_ADDRESS,cur.getString(10));
//                    hashMap.put(KEY_BUSI_ACTIVITY,cur.getString(11));
//                    hashMap.put(KEY_MONTH_DATE,cur.getString(12));
//                    hashMap.put(KEY_CONTECT_NO,cur.getString(13));
//                    hashMap.put(KEY_REMARKS,cur.getString(14));
//                    hashMap.put(KEY_PROPRIETOR,cur.getString(15));
//                    hashMap.put(KEY_FIRM,cur.getString(16));
//                    hashMap.put(KEY_USER_ID,cur.getString(17));
//                    hashMap.put(KEY_LONGI,cur.getString(18));
//                    hashMap.put(KEY_LATTI,cur.getString(19));
//                    hashMap.put(KEY_ENCODE_IMAGES,cur.getString(20));
//                    hashMap.put(KEY_PROPREITOR_LOCATION,cur.getString(21));
//                    hashMap.put(KEY_VISIT_NO,cur.getString(22));
//                    hashMap.put(KEY_CHQ_UNIQE,cur.getString(23));
//                    hashMap.put(KEY_CREATE_DATE,cur.getString(24));
//                    hashMap.put(KEY_IMAGE_SEC,cur.getString(25));
//
//
//                    listData.add(hashMap);
//                    cur.moveToNext();
//                }
//                cur.close();
//            }
//            db.close();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.getMessage();
//        }
//        return listData;
//    }


    public void setUSERCRGOPEN(String date_time, String date_revisit, String remarks,
                               String status_lead, String PF_Number
                                ) {
        try {
            db.createDatabase();
            // Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // db.deleteRecordFromDB("USERRecord",null,null);


            // read from local tables

            String query1 = "create table if not exists USERCRGOPEN (date_time varchar(50),date_revisit varchar(50),remarks varchar(50),status_lead varchar(50),PF_Number varchar(50))";

            String result= db.execQuery(query1);
            if(result.equalsIgnoreCase("1")) {

                String query = "insert into USERCRGOPEN (date_time,date_revisit,remarks,status_lead,PF_Number) values ('"+date_time
                        + "','"
                        + date_revisit
                        + "','"
                        + remarks
                        + "','"
                        + status_lead
                        + "','"
                        + PF_Number
                        + "')";

                String res = db.execQuery(query);
            }
            db.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public ArrayList<HashMap<String,String>> getUSERCRGOPEN() {
        String str = "";

        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
        try {

            db.openDataBase();
            // read from local tables
            String query = "select * from USERCRGOPEN";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    HashMap<String,String> hashMap=new HashMap<>();
                    /*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        str = cur.getString(colCount);
                    }*/

//                    hashMap.put(KEY_DATE,cur.getString(0));
//                    hashMap.put(KEY_DATEOPENREVISIT,cur.getString(1));
//                    hashMap.put(KEY_REMARKOPEN,cur.getString(2));
//                    hashMap.put(KEY_STATUSLEADOPEN,cur.getString(3));
//                    hashMap.put(KEY_OPENPF,cur.getString(4));

                    listData.add(hashMap);
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return listData;
    }





    public void setUSERRecordCRG(String ID_unique, String date_time, String customer_name, String con_person, String con_details,
                                 String lead_type, String status_lead, String date_revisit,
                                 String remarks, String date_close, String date_conversion,
                                 String sol_id, String no_of_acc, String account_no,
                                 String image_one,
                                 String image_two, String lati,
                                 String longi, String location_nmae, String type_lead_two, String unique_no, String date_time_re) {
        try {
            db.createDatabase();
            // Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // db.deleteRecordFromDB("USERRecord",null,null);


            // read from local tables

            String query2 = "create table if not exists USERRecordCRG (ID Integer PRIMARY KEY AUTOINCREMENT,ID_unique varchar(50),date_time varchar(50),customer_name varchar(50), con_person varchar(50), con_details varchar(50), lead_type varchar(50),status_lead varchar(50)" +
                    ",date_revisit varchar(50),remarks varchar(50),date_close varchar(50),date_conversion varchar(50),sol_id varchar(50),no_of_acc varchar(50),account_no varchar(50),image_one varchar(50),image_two varchar(50),lati varchar(50),longi varchar(50),location_nmae varchar(50),type_lead_two varchar(50),unique_no varchar(50),date_time_re varchar(50))";

            String result= db.execQuery(query2);
            if(result.equalsIgnoreCase("1")) {

                String query3 = "insert into USERRecordCRG (ID_unique,date_time,customer_name,con_person,con_details,lead_type,status_lead,date_revisit,remarks,date_close,date_conversion,sol_id,no_of_acc,account_no,image_one,image_two,lati,longi,location_nmae,type_lead_two,unique_no,date_time_re ) values ('"+ ID_unique
                        +  "','"
                        + date_time
                        + "','"
                        + customer_name
                        + "','"
                        + con_person
                        + "','"
                        + con_details
                        + "','"
                        + lead_type
                        + "','"
                        + status_lead
                        + "','"
                        + date_revisit
                        + "','"
                        + remarks
                        + "','"
                        + date_close
                        + "','"
                        + date_conversion
                        + "','"
                        + sol_id
                        + "','"
                        + no_of_acc
                        + "','"
                        + account_no
                        + "','"
                        + image_one
                        + "','"
                        + image_two
                        + "','"
                        + lati
                        + "','"
                        + longi
                        + "','"
                        + location_nmae
                        + "','"
                        + type_lead_two
                        + "','"
                        + unique_no
                        + "','"
                        + date_time_re+ "')";

                String res = db.execQuery(query3);
            }
            db.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
//    public ArrayList<HashMap<String,String>> getUSERRecordCRG() {
//        String str = "";
//
//        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
//        try {
//
//            db.openDataBase();
//            // read from local tables
//            String query = "select * from USERRecordCRG";
//            Cursor cur = db.selectData(query);
//
//            if (cur != null) {
//
//                cur.moveToFirst();
//
//                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
//                    HashMap<String,String> hashMap=new HashMap<>();
//                    /*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
//                        str = cur.getString(colCount);
//                    }*/
//
//                    hashMap.put(KEY_IDE,cur.getString(0));
//                    hashMap.put(KEY_UNIIDE,cur.getString(1));
//                    hashMap.put(KEY_DATE_TIME,cur.getString(2));
//                    hashMap.put(KEY_CUSTOMER_NAME,cur.getString(3));
//                    hashMap.put(KEY_CON_PERSON,cur.getString(4));
//                    hashMap.put(KEY_CON_DETAILS,cur.getString(5));
//                    hashMap.put(KEY_LEAD_TYPE,cur.getString(6));
//                    hashMap.put(KEY_STATUS_LEAD,cur.getString(7));
//                    hashMap.put(KEY_DATE_REVISIT,cur.getString(8));
//                    hashMap.put(KEY_REMARK,cur.getString(9));
//                    hashMap.put(KEY_DATE_CLOSE,cur.getString(10));
//                    hashMap.put(KEY_DATE_CONVERSION,cur.getString(11));
//                    hashMap.put(KEY_SOLL_ID,cur.getString(12));
//                    hashMap.put(KEY_NO_OF_ACCOUNT,cur.getString(13));
//                    hashMap.put(KEY_ACCOUNT,cur.getString(14));
//                    hashMap.put(KEY_IMAGE_ONE,cur.getString(15));
//                    hashMap.put(KEY_IMAGE_TWO,cur.getString(16));
//                    hashMap.put(KEY_LATI,cur.getString(17));
//                    hashMap.put(KEY_LONGII,cur.getString(18));
//                    hashMap.put(KEY_LOCATION,cur.getString(19));
//                    hashMap.put(KEY_LEADTYPETWO,cur.getString(20));
//                    hashMap.put(KEY_UNIQENO,cur.getString(21));
//                    hashMap.put(KEY_RETIME,cur.getString(22));
//
//
//                    listData.add(hashMap);
//                    cur.moveToNext();
//                }
//                cur.close();
//            }
//            db.close();
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.getMessage();
//        }
//        return listData;
//    }


    public void updatCRG(String date_time, String customer_name, String con_person, String con_details,
                         String lead_type, String status_lead, String date_revisit,
                         String remarks, String date_close, String date_conversion,
                         String sol_id, String no_of_acc, String account_no,
                         String image_one,
                         String image_two, String lati,
                         String longi, String location_nmae, String type_lead_two, String unique_no, String date_time_re) {
        try {
            db.createDatabase();
            // Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // db.deleteRecordFromDB("USERRecord",null,null);


            // read from local tables
//
//            String query2 = "create table if not exists updatCRG (date_time varchar(50),customer_name varchar(50), con_person varchar(50), con_details varchar(50), lead_type varchar(50),status_lead varchar(50)" +
//                    ",date_revisit varchar(50),remarks varchar(50),date_close varchar(50),date_conversion varchar(50),sol_id varchar(50),no_of_acc varchar(50),account_no varchar(50),image_one varchar(50),image_two varchar(50),lati varchar(50),longi varchar(50),location_nmae varchar(50))";

           // String result= db.execQuery(query2);
           // if(result.equalsIgnoreCase("1")) {

                String query3 = "UPDATE  USERRecordCRG (ID Integer PRIMARY KEY AUTOINCREMENT,date_time,customer_name,con_person,con_details,lead_type,status_lead,date_revisit,remarks,date_close,date_conversion,sol_id,no_of_acc,account_no,image_one,image_two,lati,longi,location_nmae,type_lead_two,unique_no,date_time_re) values ('"+ date_time
                        + "','"
                        + customer_name
                        + "','"
                        + con_person
                        + "','"
                        + con_details
                        + "','"
                        + lead_type
                        + "','"
                        + status_lead
                        + "','"
                        + date_revisit
                        + "','"
                        + remarks
                        + "','"
                        + date_close
                        + "','"
                        + date_conversion
                        + "','"
                        + sol_id
                        + "','"
                        + no_of_acc
                        + "','"
                        + account_no
                        + "','"
                        + image_one
                        + "','"
                        + image_two
                        + "','"
                        + lati
                        + "','"
                        + longi
                        + "','"
                        + location_nmae
                        + "','"
                        +type_lead_two
                        + "','"
                        +unique_no
                        + "','"
                        +date_time_re+ "')";

                String res = db.execQuery(query3);
           // }
            db.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }







    public void setSync_Data(String id, String EnglishWord , String HindiWord,
                               String createdDate) {
        try {
            db.createDatabase();
            // Databaseutill db=Databaseutill.getDBAdapterInstance(cnt);
            db.openDataBase();
            // read from local tables


            String query1 = "create table if not exists saveSync_Data (id varchar(50), EnglishWord varchar(50), HindiWord varchar(50), createdDate varchar(50))";

           String result= db.execQuery(query1);
            if(result.equalsIgnoreCase("1")) {

                String query = "insert into saveSync_Data (id,EnglishWord,HindiWord,createdDate) values ('"
                        + id
                        + "','"
                        + EnglishWord
                        + "','"
                        + HindiWord
                        + "','"
                        + createdDate+ "')";

                String res = db.execQuery(query);
            }
            db.close();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public String Search(String English_Word) {
        String str = "";

        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
        try {

            db.openDataBase();
            // read from local tables
            String query = "select HindiWord from saveSync_Data where EnglishWord="+"'"+English_Word+"'"+ " COLLATE NOCASE";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();
                str=cur.getString(0);
                /*for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    HashMap<String,String> hashMap=new HashMap<>();
                    *//*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        str = cur.getString(colCount);
                    }*//*
                    hashMap.put(KEY_ID,cur.getString(0));
                    hashMap.put(KEY_HINDI,cur.getString(1));
                    hashMap.put(KEY_ENGLISH,cur.getString(2));
                    hashMap.put(KEY_DATE,cur.getString(3));
                    listData.add(hashMap);
                    cur.moveToNext();
                }*/
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return str;
    }


    public ArrayList<HashMap<String,String>> getSync_Data() {
        String str = "";

        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
        try {

            db.openDataBase();
            // read from local tables
            String query = "select * from saveSync_Data";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    HashMap<String,String> hashMap=new HashMap<>();
                    /*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        str = cur.getString(colCount);
                    }*/
                    hashMap.put(KEY_ID,cur.getString(0));
                    hashMap.put(KEY_HINDI,cur.getString(1));
                    hashMap.put(KEY_ENGLISH,cur.getString(2));
                    hashMap.put(KEY_DATE,cur.getString(3));
                    listData.add(hashMap);
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return listData;
    }





    public int GetSize()
{
    String str = "";
    int count=0;
    ArrayList<HashMap<String,String>> listData=new ArrayList<>();
    try {

        db.openDataBase();
        // read from local tables

        String query = "select * from saveVisitRecord";
        Cursor cur = db.selectData(query);

        if (cur != null) {

            cur.moveToFirst();

            for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                count=iCount;
                cur.moveToNext();
            }
            cur.close();
        }
        db.close();
    } catch (Exception e) {
        // TODO: handle exception
        e.getMessage();
    }
    return count;
}
    public int GetSize_CRG()
    {
        String str = "";
        int count=0;
        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
        try {

            db.openDataBase();
            // read from local tables


            String query = "select * from USERRecordCRG";
            Cursor cur = db.selectData(query);

            count = cur.getCount();
//
//            if (cur != null) {
//
//                cur.moveToFirst();
//
//                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
//                    count=iCount;
//                    cur.moveToNext();
//
//                }

               //  count=count+1;

                cur.close();
          //  }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return count;
    }
    public ArrayList<HashMap<String,String>> getUserPasswordRecord() {
        String str = "";

        ArrayList<HashMap<String,String>> listData=new ArrayList<>();
        try {

            db.openDataBase();
            // read from local tables
            String query = "select * from saveUserNameAndPassword";
            Cursor cur = db.selectData(query);

            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++) {
                    HashMap<String,String> hashMap=new HashMap<>();
                    /*for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {
                        str = cur.getString(colCount);
                    }*/
//                    hashMap.put(KEY_USER_NMAE,cur.getString(0));
//                    hashMap.put(KEY_PASSWORD,cur.getString(1));

                    listData.add(hashMap);
                    cur.moveToNext();
                }
                cur.close();
            }
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return listData;
    }





    public ArrayList<HashMap<String, String>> getProduct() {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            // Databaseutill db= Databaseutill.getDBAdapterInstance(context);
            db.openDataBase();
            // read from local tables
            //String query = "select PRODUCT_NAME,VALUE from mas_product";
            String query = "select SID,PRODUCT_NAME,VALUE from mas_product";
            Cursor cur = db.selectData(query);
            int a = cur.getCount();
            int a1 = cur.getColumnCount();// 2
            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++)

                {

                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount += cur
                            .getColumnCount()) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("sid", cur.getString(colCount));
                        map.put("product_name", cur.getString(colCount + 1));
                        map.put("value", cur.getString(colCount + 2));

                        data.add(map);
                    }

                    cur.moveToNext();
                }

                cur.close();
            }
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return data;
    }

    public ArrayList<String> getDealer_details() {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<String> returnval = new ArrayList<String>();
        try {
            // Databaseutill db= Databaseutill.getDBAdapterInstance(context);
            db.openDataBase();
            // read from local tables
            String query = "select DEALER_NAME,MOBILE_1,EMAIL_ID,SID from mas_dealer";
            Cursor cur = db.selectData(query);
            int a = cur.getCount();
            int a1 = cur.getColumnCount();// 2
            if (cur != null) {

                cur.moveToFirst();

                for (int iCount = 0; iCount < cur.getCount(); iCount++)

                {

                    for (int colCount = 0; colCount < cur.getColumnCount(); colCount++) {

                        returnval.add(cur.getString(colCount));
                    }

                    cur.moveToNext();
                }

                cur.close();
            }
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        }
        return returnval;
    }}
