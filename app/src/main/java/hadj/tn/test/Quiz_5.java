package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Quiz_5 extends AppCompatActivity {
    Button yes, no;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);
        close = findViewById(R.id.quit5);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_5.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        no= findViewById(R.id.btn2222);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_5.this,Quiz_6.class );
                startActivity(intent);

            }
        });

        yes = findViewById(R.id.btn1111);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(Quiz_5.this);

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.age_restriction,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
}