package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import hadj.tn.test.Model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Sign_InActivity extends AppCompatActivity {

    EditText editTextPassword, editTextEmail;
    TextView signup, forgotPass;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signup);

        login();
        toForgotPass();
        toSignUp();
    }

    private void toSignUp() {

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_InActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toForgotPass() {

        forgotPass = (TextView) findViewById(R.id.forgotPass);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_InActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(View -> {

            String password = String.valueOf(editTextPassword.getText());
            String username = String.valueOf(editTextEmail.getText());

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            userApi.checkUser(user)
                    .enqueue(new Callback<ResponseBody>() {
                                 @Override
                                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                     if ((username.length() == 0) && (password.length() == 0))
                                         Toast.makeText(Sign_InActivity.this, "you have to fill in the fields", Toast.LENGTH_LONG).show();

                                     else if (username.length() == 0)
                                         Toast.makeText(Sign_InActivity.this, "Email cannot be empty", Toast.LENGTH_LONG).show();

                                     else if (password.length() == 0)
                                         Toast.makeText(Sign_InActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
                                     else if (response.code()==400) {

                                         Toast.makeText(Sign_InActivity.this, "Account user not confirmed", Toast.LENGTH_LONG).show();
                                     }  else if(response.code()==500) {
                                         Toast.makeText(Sign_InActivity.this,"wrong username or password" , Toast.LENGTH_LONG).show();

                                     }else if (response.code()==200){

                                         String token = null;
                                         try {
                                             String jwt = response.body().string();
                                             JSONObject obj = new JSONObject(jwt);
                                             token = obj.getString("token");
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }catch (IOException e) {
                                             e.printStackTrace();
                                         }


                                         SharedPreferences preferences = getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
                                         preferences.edit().putString("TOKEN",token).apply();
                                         preferences.edit().putString("USERNAME",username).apply();
                                         Toast.makeText(Sign_InActivity.this,"logged in successfully" , Toast.LENGTH_LONG).show();

                                         // start act home
                                         Intent intent = new Intent(Sign_InActivity.this, HomeActivity.class);
                                         intent.putExtra("username",username);
                                         startActivity(intent);


                                     }
                                 }
                                 @Override
                                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                                     Toast.makeText(Sign_InActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                                 }
                             }
                    );
        });
    }


}


