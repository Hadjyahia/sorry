package hadj.tn.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VerifActivity extends AppCompatActivity {
    ImageView iv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);
        btn = (Button) findViewById(R.id.verifyy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifActivity.this, NewPasswordActivity.class);
                startActivity(intent);
            }
        });
        iv = (ImageView) findViewById(R.id.backToForgo);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }
}