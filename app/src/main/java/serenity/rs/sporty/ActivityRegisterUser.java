package serenity.rs.sporty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRegisterUser extends AppCompatActivity {

    private EditText etUsername, etPassword, etLink;
    private TextView tvMessage;
    private Button bRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLink     = (EditText) findViewById(R.id.etLink);
        tvMessage  = (TextView) findViewById(R.id.tvMessage);
        bRegister  = (Button) findViewById(R.id.bRegister);

        dbHelper = new DBHelper(this);
    }

    public void registerUser(View v)
    {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String link     = etLink.getText().toString();


        boolean isRegisteredWithNoError = dbHelper.createUser(username, password, link);

        if (isRegisteredWithNoError) {
            tvMessage.setText("Good news! We've made an account for you!");
        } else {
            tvMessage.setText("Ooops! Something went wrong with the creation of your account.");
        }

    }


}
