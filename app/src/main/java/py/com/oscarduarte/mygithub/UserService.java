package py.com.oscarduarte.mygithub;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by virux on 19/05/16.
 */
public interface UserService {

    @GET("/users/{login}")
    void getUser(@Path("login")String login, Callback<User> user);

    @GET("/users/{login}/followers")
    void getUserFollowers(@Path("login")String login, Callback<List<User>> usuarios);

    void getUserRepository();

}
