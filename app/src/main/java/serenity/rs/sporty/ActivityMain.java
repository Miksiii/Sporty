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

    private DBHelper dbHelper;

    private TextView tvUsername, tvPassword, tvMessage;
    private Button bLogin, bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivityComponents();
    }

    private void initActivityComponents()
    {
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvMessage  = (TextView) findViewById(R.id.tvMessage);
        bLogin     = (Button) findViewById(R.id.bLogin);
        bRegister  = (Button) findViewById(R.id.bRegister);
        dbHelper   = new DBHelper(this);
    }

    public void login(View v)
    {
        User targetedUser = dbHelper.getUserWithCredentials(tvUsername.getText().toString(), tvPassword.getText().toString());

        if (targetedUser == null)
        {
            tvMessage.setText("Incorrrect username or password.");
            tvUsername.setText("");
            tvPassword.setText("");
        }
        else
        {
            User.getUserInstance().setId(targetedUser.getId());
            User.getUserInstance().setUsername(targetedUser.getUsername());
            User.getUserInstance().setPassword(targetedUser.getPassword());
            User.getUserInstance().setLink(targetedUser.getLink());

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
