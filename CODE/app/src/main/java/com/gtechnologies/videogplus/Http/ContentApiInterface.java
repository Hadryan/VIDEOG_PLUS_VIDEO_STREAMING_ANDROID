package com.gtechnologies.videogplus.Http;

import com.gtechnologies.videogplus.Model.ContentResponse;
import com.gtechnologies.videogplus.Model.Subscription;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Developed by Fojle Rabbi Saikat on 1/9/2017.
 * Owned by Bitmakers Ltd.
 * Contact fojle.rabbi@bitmakers-bd.com
 */
public interface ContentApiInterface {

    @POST("content/banner")
    Call<ContentResponse> getBanner(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken);

    @GET("content/song")
    Call<ContentResponse> getVideoSong(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Query("page") int pageNumber, @Query("size") int size);

    @GET("content/drama")
    Call<ContentResponse> getDrama(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Query("page") int pageNumber, @Query("size") int size);

    @GET("content/movie")
    Call<ContentResponse> getMovie(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Query("page") int pageNumber, @Query("size") int size);

    @POST("content/hit")
    Call<ContentResponse> getHit(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Query("page") int pageNumber, @Query("size") int size);

    @POST("content/highlight")
    Call<ContentResponse> getHighlight(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Query("page") int pageNumber, @Query("size") int size);

    @POST("content/suggestion/{id}")
    Call<ContentResponse> getSuggestedVideo(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Path("id") int videoId);

    @POST("log")
    Call<ResponseBody> postLog(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Body RequestBody requestBody);

    @POST("viewstatus/{contentId}")
    Call<Subscription> getStatus(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Path("contentId") int contentId, @Body RequestBody jsonObject);

    @POST("push/{contentId}")
    Call<Subscription> pushOtp(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Path("contentId") int contentId, @Body RequestBody jsonObject);

    @POST("charge/{contentId}/{pin}")
    Call<Subscription> chargeOtp(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Path("contentId") int contentId, @Path("pin") int pin, @Body RequestBody jsonObject);

    @POST("unsubscribe/{contentId}")
    Call<Subscription> unsubscribe(@Header("Authorization") String key, @Header("MSISDN") String msisdn, @Header("RGTOKEN") String firebaseToken, @Path("contentId") int contentId, @Body RequestBody jsonObject);

//    @POST("my_msisdn")
//    Call<ResponseBody> getMSISDN(@Header("Authorization") String key);
//
//    @POST("service/public/getSubsriptionData")
//    Call<ResponseBody> getSubscription(@Body RequestBody jsonObject);
//
//    @POST("service/public/pinCheck")
//    Call<ResponseBody> checkPin(@Body RequestBody jsonObject);

//    @POST("hits")
//    Call<List<HitsModel>> getHitsList(@Header("Authorization") String key);
//
//    @POST("selected")
//    Call<List<SelectedModel>> getSelectedList(@Header("Authorization") String key);
//
//    @POST("mylist")
//    Call<List<MyListModel>> getMyList(@Header("Authorization") String key);
//
//    @POST("highlight")
//    Call<List<ExclusiveModel>> getHighlightList(@Header("Authorization") String key);
//
//    @POST("recent")
//    Call<List<NewAlbumModel>> getRecentList(@Header("Authorization") String key);
//
//    @POST("list_genre")
//    Call<List<GenreModel>> getAllCategory(@Header("Authorization") String key);
//
//    @POST("{like}")
//    Call<LikeModel> getLikeStatus(@Header("Authorization") String key, @Path("like") String like);
//
//    @POST("{album}")
//    Call<List<AlbumModel>> getAlbums(@Header("Authorization") String key, @Path("album") String album);
//
//    @POST("{artist}")
//    Call<List<ArtistModel>> getArtists(@Header("Authorization") String key, @Path("artist") String album);
//
//    @POST("{album}")
//    Call<List<AlbumModel>> getAlbumByCategory(@Header("Authorization") String key, @Path("album") String album);
//
//    @POST("{album}")
//    Call<List<AlbumModel>> getAlbumByArtist(@Header("Authorization") String key, @Path("album") String album);
//
//    @POST("{album}")
//    Call<List<SongModel>> getSongListByAlbum(@Header("Authorization") String key, @Path("album") String album);
//
//    @POST("{search}")
//    Call<List<SongModel>> getTrackListBySearch(@Header("Authorization") String key, @Path("search") String album);
//
//    @POST("{search}")
//    Call<List<SearchAlbumModel>> getAlbumListBySearch(@Header("Authorization") String key, @Path("search") String album);
//
//    @POST("{search}")
//    Call<List<SearchArtistModel>> getArtistListBySearch(@Header("Authorization") String key, @Path("search") String album);
//
//    @POST("{suggestion}")
//    Call<List<SuggestionModel>> getSuggestionList(@Header("Authorization") String key, @Path("suggestion") String suggestion);
//
//    @POST("{log}")
//    Call<Log> logPlayer(@Header("Authorization") String key, @Path("log") String suggestion);
//
//    @POST("new")
//    Call<List<AlbumModel>> newAlbum(@Header("Authorization") String key);
//
//    @POST("{album}")
//    Call<List<SongModel>> getAlbum(@Header("Authorization") String key, @Path("album") String albumName);

//    @POST("recent")
//    Call<List<GenreModel>> getAllGenre(@Header("Authorization") String key, @Header("language") String language);
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
