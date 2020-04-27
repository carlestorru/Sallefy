package salle.android.projects.registertest.restapi.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserRegister;

public interface UserService {
    @GET("users/{login}")
    Call<User> getUserById(@Path("login") String login);
    @GET("users")
    Call<List<User>> getAllUsers(@Header("Authorization") String token);
    @POST("register")
    Call<ResponseBody> registerUser(@Body UserRegister user);
}