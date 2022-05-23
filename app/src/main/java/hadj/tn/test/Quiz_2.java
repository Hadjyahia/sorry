package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import hadj.tn.test.fragment.HomeFragment;
import hadj.tn.test.fragment.pfragment;

public class Quiz_2 extends AppCompatActivity {
    Button yes, no;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        close = findViewById(R.id.quit2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_2.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        no= findViewById(R.id.btn2);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(Quiz_2.this);

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.age_restriction,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button ok = customLayout.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        //fragmentTransaction.replace(R.id.container,new pfragment()).commit();
                       //Intent intent = new Intent(Quiz_2.this, HomeActivity.class );
                        //startActivity(intent);

                        getSupportFragmentManager().beginTransaction().add(R.id.container,new pfragment()).commit();
                    }
                });

            }
        });

        yes = findViewById(R.id.btn1);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_2.this,Quiz_3.class );
                startActivity(intent);
            }
        });
    }


}