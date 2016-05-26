package py.com.oscarduarte.mygithub;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {


    ImageView avatarImageView;


    TextView nameTextView;


    TextView loginTextView;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextView = (TextView)findViewById(R.id.textViewName);
        loginTextView = (TextView)findViewById(R.id.textViewLogin);
        avatarImageView = (ImageView)findViewById(R.id.imageViewAvatar);
        listView = (ListView)findViewById(R.id.listViewUsers);

        RestAdapter restAdapter = new RestAdapter.Builder()
                                                .setEndpoint(getString(R.string.end_point))
                                                .build();


        UserService userService = restAdapter.create(UserService.class);

        userService.getUser("leodufer", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.i("USERS", user.toString());
                nameTextView.setText(user.getName());
                loginTextView.setText("@"+user.getLogin());

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.WHITE)
                        .borderWidthDp(3)
                        .cornerRadiusDp(80)
                        .oval(true)
                        .build();

                Picasso.with(getApplicationContext())
                        .load(user.getAvatar_url())
                        .fit()
                        .transform(transformation)
                        .into(avatarImageView);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("ERROR", error.getMessage());
            }
        });


        userService.getUserFollowers("leodufer", new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                Log.i("USERS",users.toString());
                UsersAdapter adapter = new UsersAdapter(users,MainActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
