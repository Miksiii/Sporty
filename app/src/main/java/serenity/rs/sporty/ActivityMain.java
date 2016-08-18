package serenity.rs.sporty;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    private TextView tvUsername, tvPassword, tvMessage;
    private Button bLogin, bRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvMessage  = (TextView) findViewById(R.id.tvMessage);
        bLogin     = (Button) findViewById(R.id.bLogin);
        bRegister  = (Button) findViewById(R.id.bRegister);
    }

    public void login(View v)
    {
        User userAttemptedToLogin = dbHelper.getUserWithCredentials(tvUsername.getText().toString(), tvPassword.getText().toString());

        if (userAttemptedToLogin == null) {
            tvMessage.setText("Incorrrect username or password.");
            tvUsername.setText("");
            tvPassword.setText("");

        } else {

            User.getUserInstance().setId(userAttemptedToLogin.getId());
            User.getUserInstance().setUsername(userAttemptedToLogin.getUsername());
            User.getUserInstance().setPassword(userAttemptedToLogin.getPassword());
            User.getUserInstance().setLink(userAttemptedToLogin.getLink());

            Intent switchLoginToChooseSportActivity = new Intent("serenity.rs.sporty.ActivitySportChooser");
            startActivity(switchLoginToChooseSportActivity);
        }
    }

    public void goToRegisterActivity(View v)
    {
        Intent switchToRegisterActivity = new Intent("serenity.rs.sporty.ActivityRegisterUser");
        startActivity(switchToRegisterActivity);
    }
}
