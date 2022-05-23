package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CheckEmailActivity extends AppCompatActivity {

    ImageView backToSignUp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        backToSignUp = findViewById(R.id.backToSignUp);
        backToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckEmailActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}