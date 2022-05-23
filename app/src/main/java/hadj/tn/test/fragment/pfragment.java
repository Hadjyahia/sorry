package hadj.tn.test.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hadj.tn.test.Model.User;
import hadj.tn.test.QuizActivity;
import hadj.tn.test.Quiz_2;
import hadj.tn.test.Quiz_2Fragment;
import hadj.tn.test.R;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class pfragment extends Fragment {

    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String user  = preferences.getString("USERNAME",null); //second parameter default value.
        String retrievedToken  = preferences.getString("TOKEN",null);
        token = "Bearer "+retrievedToken;

        Button test = view.findViewById(R.id.testEligibility);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                 fragmentTransaction.replace(R.id.container,new Quiz_2Fragment()).commit();
              //  Intent intent = new Intent(getContext(), Quiz_2Fragment.class);
                //startActivity(intent);



            }

        });

        // get info user



            RetrofitClient retrofitClient = new RetrofitClient();
            API userApi = retrofitClient.getRetrofit().create(API.class);

            Call<User> call = userApi.findUser(token,user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User getuser = response.body();

                    assert getuser != null;
                    String personName = getuser.getUsername();
                    String personEmail = getuser.getEmail();
                    String personImage = getuser.getImage();
                    String personBloodGroup = getuser.getTypeSang();


                    if(personImage!=null){
                        byte[] bytes = Base64.decode(personImage,Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        ImageView ImageView = view.findViewById(R.id.profilePhoto);
                        ImageView.setImageBitmap(bitmap);
                    }

                    ImageView imageViewBG = view.findViewById(R.id.bloodGroup);
                    switch (personBloodGroup){
                        case "A_plus" : imageViewBG.setImageResource(R.drawable.aplusss); break;
                        case "A_moins" : imageViewBG.setImageResource(R.drawable.amoins); break;

                        case "O_plus" : imageViewBG.setImageResource(R.drawable.oplus); break;
                        case "O_moins" : imageViewBG.setImageResource(R.drawable.omoins); break;

                        case "B_plus" : imageViewBG.setImageResource(R.drawable.bplus); break;
                        case "B_moins" : imageViewBG.setImageResource(R.drawable.bmoins); break;

                        case "AB_plus" : imageViewBG.setImageResource(R.drawable.abplus); break;
                        case "AB_moins" : imageViewBG.setImageResource(R.drawable.abmoins); break;

                        default:  imageViewBG.setImageResource(R.drawable.profile); break;
                    }

                    TextView textViewUsername = view.findViewById(R.id.usernameProfile);
                    TextView textViewEmail = view.findViewById(R.id.emailProfile);

                    textViewUsername.setText(personName);
                    textViewEmail.setText(personEmail);

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Log error here since request failed
                }
            });
        }
    }

