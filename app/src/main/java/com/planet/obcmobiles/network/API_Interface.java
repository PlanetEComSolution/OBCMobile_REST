package com.planet.obcmobiles.network;

import com.planet.obcmobiles.Model.BranchResponse;
import com.planet.obcmobiles.Model.CityResponse;
import com.planet.obcmobiles.Model.ComplaintStatusResponse;
import com.planet.obcmobiles.Model.ContactUsHeader_Response;
import com.planet.obcmobiles.Model.ContactUs_Response;
import com.planet.obcmobiles.Model.CreateOTPResponse;
import com.planet.obcmobiles.Model.Deposit_Response;
import com.planet.obcmobiles.Model.FetchExcel_Response;
import com.planet.obcmobiles.Model.InsertRecord_Response;
import com.planet.obcmobiles.Model.LoanLevel1_Response;
import com.planet.obcmobiles.Model.LoanLevel2_Response;
import com.planet.obcmobiles.Model.LoanLevel3_Response;
import com.planet.obcmobiles.Model.LoanLevel0_Response;
import com.planet.obcmobiles.Model.LoanStatusResponse;
import com.planet.obcmobiles.Model.NearestBranchesByPin_Response;
import com.planet.obcmobiles.Model.NearestBranches_Response;
import com.planet.obcmobiles.Model.OTP_Reponse;
import com.planet.obcmobiles.Model.Product2_Response;
import com.planet.obcmobiles.Model.ProductResponse;
import com.planet.obcmobiles.Model.StateResponse;
import com.planet.obcmobiles.Model.UUIDInsertResponse;
import com.planet.obcmobiles.Model.WhatsNewImage_Response;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface API_Interface {

    /*
    @GET("ChangePassword")
    Call<ResponseBody> changePassword(@Query("lname") String username, @Query("email") String email, @Query("newpwd") String newpassword);
    */
     // when not using RxJava use Call
     /* @POST("LoginOBC")
    Call<Login_Model> Login_KYC(@Body HashMap<String, String> body);*/
     // when using RxJava use Observable


    @POST("GetdatafromSub2Content")
    Observable<List<ProductResponse>> FetchProduct(@Body HashMap<String, String> body);

    @POST("GetState")
    Observable<List<StateResponse>> FetchState(@Body HashMap<String, String> body);

    @POST("GetCity")
    Observable<List<CityResponse>> FetchCity(@Body HashMap<String, String> body);

    @POST("GetBranchName")
    Observable<List<BranchResponse>> FetchBranch(@Body HashMap<String, String> body);

    @POST("LoanStatus")
    Observable<List<LoanStatusResponse>> FetchLoanStatus(@Body HashMap<String, String> body);

    @POST("ComplaintStatus")
    Observable<List<ComplaintStatusResponse>> FetchComplaintStatus(@Body HashMap<String, String> body);

    @POST("UUIDInsert")
    Observable<List<UUIDInsertResponse>> UUIDInsert(@Body HashMap<String, String> body);

    @POST("GetDataSubHeading")
    Observable<List<Product2_Response>> FetchProduct2(@Body HashMap<String, String> body);

    @POST("FatchExcelRecord")
    Observable<List<FetchExcel_Response>> FatchExcelRecord(@Body HashMap<String, String> body);

    @POST("insertrecord")
    Observable<InsertRecord_Response> InsertRecord(@Body HashMap<String, String> body);

    @POST("ContactUs")
    Observable<List<ContactUs_Response>> ContactUs(@Body HashMap<String, String> body);

    @POST("ContactUsHeader")
    Observable<List<ContactUsHeader_Response>> ContactUsHeader(@Body HashMap<String, String> body);

    @POST("WhatsNewImage")
    Observable<List<WhatsNewImage_Response>> WhatsNewImage(@Body HashMap<String, String> body);

    @POST("Deposit")
    Observable<List<Deposit_Response>> Deposit(@Body HashMap<String, String> body);

    @POST("LoanLevel0")
    Observable<List<LoanLevel0_Response>> LoanLevel0(@Body HashMap<String, String> body);

    @POST("LoanLevel1")
    Observable<List<LoanLevel1_Response>> LoanLevel1(@Body HashMap<String, String> body);

    @POST("LoanLevel2")
    Observable<List<LoanLevel2_Response>> LoanLevel2(@Body HashMap<String, String> body);

    @POST("LoanLevel3")
    Observable<List<LoanLevel3_Response>> LoanLevel3(@Body HashMap<String, String> body);

    @POST("NearestBranches")
    Observable<List<NearestBranches_Response>> NearestBranches(@Body HashMap<String, String> body);

    @POST("FindbyPincode")
    Observable<List<NearestBranchesByPin_Response>> FindbyPincode(@Body HashMap<String, String> body);

    @POST("Generate_otp")
    Observable<OTP_Reponse> Generate_otp(@Body HashMap<String, String> body);


}
