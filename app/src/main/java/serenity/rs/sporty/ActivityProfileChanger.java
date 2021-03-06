package serenity.rs.sporty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityProfileChanger extends AppCompatActivity
{
    private DBHelper dbHelper;

    private EditText etUsername, etPassword, etLink;
    private TextView tvMessage;
    private Button bUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_changer);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initActivityComponents();
        setActivityComponentsValues();
    }

    private void initActivityComponents()
    {
        dbHelper   =  new DBHelper(this);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLink     = (EditText) findViewById(R.id.etLink);
        bUpdate    = (Button) findViewById(R.id.bUpdate);
        tvMessage  = (TextView) findViewById(R.id.tvMessage);
    }

    private void setActivityComponentsValues()
    {
        etUsername.setText(User.getUserInstance().getUsername());
        etPassword.setText(User.getUserInstance().getPassword());
        etLink.setText(User.getUserInstance().getLink());
    }

    public void updateUser(View v)
    {
        boolean isUpdated = dbHelper.updateUser(
                User.getUserInstance().getId(),
                etUsername.getText().toString(),
                etPassword.getText().toString(),
                etLink.getText().toString()
            );

        if (isUpdated) {
            tvMessage.setText("You're updated now.");
        } else {
            tvMessage.setText("Ooops! Something went wrong with updating your info.");
        }
    }

}
