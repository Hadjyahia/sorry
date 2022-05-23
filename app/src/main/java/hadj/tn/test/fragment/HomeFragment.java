package hadj.tn.test.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.dhaval2404.imagepicker.ImagePicker;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import hadj.tn.test.Model.Post;
import hadj.tn.test.Model.User;
import hadj.tn.test.R;

import hadj.tn.test.Sign_InActivity;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    List<Bitmap> images = new ArrayList<>();
    List<Bitmap> imageProfile = new ArrayList<>();
    List<String> names = new ArrayList<>();// {"Aicha Gebsi","Tayssir Massoudi","Amal Gharbi","Mouhemed Ben Salah","Omar Maghrbi","Lotfi chakroun","Mouhamed ben massoud","Lina maria","Salma Selmi"};
    List<String> pubcontenu = new ArrayList<>();//{"Puis-je donner après avoir fait un tatouage ou un piercing ?","Puis-je donner si je suis diabétique ?","Puis-je donner tout de suite après une opération chirurgicale ou dentaire ? ","Puis-je donner si je prends un traitement médical ? ","Puis-je donner si j’ai eu la dengue ?","Puis-je aller au don de sang après une vaccination contre le coronavirus","Puis-je aller au don de sang même si je souffre d’un refroidissement ?","Après avoir eu la grippe, combien de temps dois-je attendre avant de pouvoir retourner au don de sang ?","Combien de sang contient l’organisme humain ?","En combien de temps l’organisme remplace-t-il le sang prélevé ?"};
    CircleImageView image;
    EditText contenu;
    ImageView add, publier;
    long id;
    Post post = new Post() ;  List<Post> getPosts = new ArrayList<>();
    User user;

    String token ;
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null); //second parameter default value.
        token = "Bearer "+retrievedToken;
        /********** Video playback ************/

        VideoView videoView = view.findViewById(R.id.videoweview);
        ImageView imageView = view.findViewById(R.id.img_start_vid);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoPath = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.video;
                Uri uri = Uri.parse(videoPath);
                videoView.setVideoURI(uri);
                imageView.setVisibility(View.INVISIBLE);
            }
        });

        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        image=  view.findViewById(R.id.profilePub);
        add= view.findViewById(R.id.addImage);
        publier= view.findViewById(R.id.publish);

        /************** Adding media to the publication ************/

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with((Activity) getContext())
                        .crop()
                        .start();
            }});

        /************************* retrieve actual user's picture and id *******************/

        if(getArguments() != null) {

            String name = getArguments().getString("User");
            TextView textViewUsername = view.findViewById(R.id.usernameTextView);
            textViewUsername.setText(name);
            Call<User> call = userApi.findUser(token,name);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    // assert user != null;
                    // id = user.getId();
                    post.setUserId(user);
                    // System.out.println("id:"+post.getUserId());

                   if (user.getImage() != null) {

                        byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        image.setImageBitmap(bitmap);

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Log error here since request failed
                }
            });
        }

        /**************** creating the publication ******************/


        publier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contenu= view.findViewById(R.id.contenu);
                String con =  contenu.getText().toString();
                post.setContenu(con);

                Call<ResponseBody> call = userApi.createPost(token,post);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200){
                           Toast.makeText(getContext(), "Post created successfully", Toast.LENGTH_LONG).show();
                           getPubs(view);
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Couldn't create post", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


        /*********** displaying list of publications **************************/


        getPubs(view);

    }

    public void getPubs(View view) {

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);
        final Bitmap[] bitmap_profile = new Bitmap[1];
        if(getArguments() != null) {

            String name = getArguments().getString("User");
            TextView textViewUsername = view.findViewById(R.id.usernameTextView);
            textViewUsername.setText(name);
            Call<User> call = userApi.findUser(token,name);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    // assert user != null;
                    // id = user.getId();
                    post.setUserId(user);
                    // System.out.println("id:"+post.getUserId());

                    if (user.getImage() != null) {

                        byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
                        bitmap_profile[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        image.setImageBitmap(bitmap_profile[0]);

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Log error here since request failed
                }
            });
        }


        RecyclerView recyclerView = view.findViewById(R.id.listPub);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Post>> call = userApi.findAllPubs(token);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                int i =0;

                if(response.body() !=null){
                    System.out.println(response.body());
                    getPosts.addAll(response.body());
                    // System.out.println(getPosts);
                    while (i< getPosts.size()){
                        User user = getPosts.get(i).getUserId();
                        names.add(user.getUsername());
                        System.out.println(user.getUsername());

                        String pict = user.getImage();

                        byte [] encodeByte = Base64.decode(pict,Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        images.add(bitmap);
                        imageProfile.add(bitmap_profile[0]);
                        System.out.println("oumayma"+images);



                        pubcontenu.add(getPosts.get(i).getContenu());
                        i++;


                    }

                    recyclerView.setAdapter(new ListPubAdapter(names,images,imageProfile,pubcontenu));



                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}