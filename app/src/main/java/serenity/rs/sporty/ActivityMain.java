package serenity.rs.sporty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    private TextView tvUsername, tvPassword, tvMessage;
    private Button bLogin;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvMessage  = (TextView) findViewById(R.id.tvMessage);
        bLogin     = (Button) findViewById(R.id.bLogin);

    }

    public void login(View v) {
        boolean isFinished = dbHelper.createUser("miksiii", "snele123", "http://www.facebook.com/hkjviprahaos");

        if(isFinished) {
            Toast.makeText(ActivityMain.this, "Data inserted into users", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityMain.this, "Problems with inserting", Toast.LENGTH_SHORT).show();
        }
    }
}
