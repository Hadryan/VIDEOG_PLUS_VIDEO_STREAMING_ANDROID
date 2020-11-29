package com.gtechnologies.videogplus.Http;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Developed by Fojle Rabbi Saikat on 1/9/2017.
 * Owned by Bitmakers Ltd.
 * Contact fojle.rabbi@bitmakers-bd.com
 */
public interface BaseApiInterface {

    @POST("service/public/pinGenerate")
    Call<ResponseBody> generatePin(@Body RequestBody jsonObject);

    @POST("service/public/getSubsriptionData")
    Call<ResponseBody> getSubscription(@Body RequestBody jsonObject);

    @POST("service/public/pinCheck")
    Call<ResponseBody> checkPin(@Body RequestBody jsonObject);


//    @FormUrlEncoded
//    @POST("oauth/token?grant_type=password&client_id=biyeBariServerWeb&client_secret=6af731d7-5a78-412c-975a-e83e7baa1c0f")
//    Call<ResponseBody> getToken(@Field("username") String username, @Field("password") String password);
//
//    @GET("districts")
//    Call<ResponseBody> getAllDistrict(@Header("Authorization") String key, @Query("size") int size, @Query("page") int page);
//
//    @GET("units")
//    Call<ResponseBody> getAllUnit(@Header("Authorization") String key, @Query("size") int size, @Query("page") int page);
//
//    @GET("areas")
//    Call<ResponseBody> getAllArea(@Header("Authorization") String key, @Query("size") int size, @Query("page") int page);
//
//    @GET("settings/SYNC_INTERVAL_HOURS")
//    Call<ResponseBody> getSyncInterval(@Header("Authorization") String key);
//
//    @FormUrlEncoded
//    @POST("user/login")
//    Call<ResponseBody> userLogin(@Header("Authorization") String key, @Field("user") String username, @Field("password") String password);
//
//    @FormUrlEncoded
//    @POST("vendors/login")
//    Call<ResponseBody> vendorLogin(@Header("Authorization") String key, @Field("user") String username, @Field("password") String password);
//
//    @POST("user")
//    Call<ResponseBody> createUser(@Header("Authorization") String key, @Body RequestBody jsonObject);
//
//    @PUT("user/{id}")
//    Call<ResponseBody> updateUserInfo(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @PUT("vendors/{id}")
//    Call<ResponseBody> updateVendorInfo(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @POST("vendors")
//    Call<ResponseBody> createVendor(@Header("Authorization") String key, @Body RequestBody jsonObject);
//
//    @Multipart
//    @POST("vendors/{id}/upload/logo")
//    Call<ResponseBody> uploadVendorLogo(@Header("Authorization") String key, @Path("id") int id, @Part MultipartBody.Part image);
//
//    @Multipart
//    @POST("vendors/{id}/upload/cover")
//    Call<ResponseBody> uploadVendorCover(@Header("Authorization") String key, @Path("id") int id, @Part MultipartBody.Part image);
//
//    @FormUrlEncoded
//    @POST("{user}/{id}/changePassword")
//    Call<ResponseBody> changePassword(@Header("Authorization") String key, @Path("user") String userType, @Path("id") int id, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);
//
//    @POST("support")
//    Call<ResponseBody> sendSupport(@Header("Authorization") String key, @Body RequestBody jsonObject);
//
//    @PATCH("vendors/{id}/updateAreas")
//    Call<ResponseBody> updateVendorArea(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @PATCH("vendors/{id}/updateCategories")
//    Call<ResponseBody> updateVendorService(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @PATCH("vendors/{id}/updateCategoriesBoth")
//    Call<ResponseBody> updateVendorServiceBoth(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @GET("vendors/{id}/items/both")
//    Call<ResponseBody> getAllVendorItem(@Header("Authorization") String key, @Path("id") int id, @Query("size") int size, @Query("page") int page);
//
//    @POST("items")
//    Call<ResponseBody> createItem(@Header("Authorization") String key, @Body RequestBody jsonObject);
//
//    @Multipart
//    @POST("items/{id}/uploadImage")
//    Call<ResponseBody> uploadItemImage(@Header("Authorization") String key, @Path("id") int id, @Part List<MultipartBody.Part> imageS);
//
//    @PUT("items/{id}")
//    Call<ResponseBody> updateItem(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @POST("items/{id}/deleteImages")
//    Call<ResponseBody> deleteImage(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @GET("vendors/{id}/packages/both")
//    Call<ResponseBody> getAllVendorPackage(@Header("Authorization") String key, @Path("id") int id, @Query("size") int size, @Query("page") int page);
//
//    @POST("packages")
//    Call<ResponseBody> createPackage(@Header("Authorization") String key, @Body RequestBody jsonObject);
//
//    @Multipart
//    @POST("packages/{id}/uploadImage")
//    Call<ResponseBody> uploadPackageImage(@Header("Authorization") String key, @Path("id") int id, @Part List<MultipartBody.Part> imageS);
//
//    @PUT("packages/{id}")
//    Call<ResponseBody> updatePackage(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @POST("packages/{id}/deleteImages")
//    Call<ResponseBody> deletePackageImage(@Header("Authorization") String key, @Path("id") int id, @Body RequestBody jsonObject);
//
//    @FormUrlEncoded
//    @POST("{user}/resetPassword")
//    Call<ResponseBody> resetPassword(@Header("Authorization") String key, @Path("user") String userType, @Field("email") String email, @Field("type") String user);
//
//    @GET("settings/appversion")
//    Call<ResponseBody> getAppVersion(@Header("Authorization") String key);
//
//    @GET("doSearch/{value}")
//    Call<ResponseBody> doSearch(@Header("Authorization") String key, @Path("value") String keyword);
//
//    @GET("vendors/{id}")
//    Call<ResponseBody> getVendor(@Header("Authorization") String key, @Path("id") int vendorId);
//
//    @GET("items/{id}")
//    Call<ResponseBody> getItem(@Header("Authorization") String key, @Path("id") int itemId);
//
//    @GET("pushNotification/getAllPublish/{date}")
//    Call<ResponseBody> getPushNotification(@Header("Authorization") String key, @Path("date") long date);
//
//    @GET("categories/parents")
//    Call<ResponseBody> getAllParent(@Header("Authorization") String key);
//
//    @GET("categories/parent/{id}/leaves")
//    Call<ResponseBody> getAllLeaves(@Header("Authorization") String key, @Path("id") long id);
//
//    @GET("user/{uid}/vendorId/{vid}/getSingleRating")
//    Call<ResponseBody> getRating(@Header("Authorization") String key, @Path("uid") long uid, @Path("vid") long vid);
//
//    @PATCH("user/userRatingVendor")
//    Call<ResponseBody> updateRating(@Header("Authorization") String key, @Body RequestBody jsonObject);

}
