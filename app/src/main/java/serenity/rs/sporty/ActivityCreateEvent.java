package serenity.rs.sporty;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ActivityCreateEvent extends AppCompatActivity {

    private EditText etTitle, etLongitude, etLatitude;
    private TextView tvMessage;
    private Button bDate, bTime, bCreate;
    private Spinner sRequiredPlayers;
    private ArrayAdapter<String> arrayAdapter;
    private String[] numOfMaxPeople;
    private String choosenTypeOfSport, choosenEventDate, choosenEventTime;
    private DBHelper dbHelper;

    private int dateYear, dateMonth, dateDay,
            timeHour, timeMinute, timeSecond;

    static final int DIALOG_DATEPICKER_ID = 0,
                     DIALOG_TIMEPICKER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);

        Calendar calendar = Calendar.getInstance();
        dateYear   = calendar.get(Calendar.YEAR);
        dateMonth  = calendar.get(Calendar.MONTH);
        dateDay    = calendar.get(Calendar.DAY_OF_MONTH);
        timeHour   = calendar.get(Calendar.HOUR);
        timeMinute = calendar.get(Calendar.MINUTE);
        timeSecond = calendar.get(Calendar.SECOND);

        numOfMaxPeople = getResources().getStringArray(R.array.number_of_people);

        sRequiredPlayers = (Spinner) findViewById(R.id.sRequiredPlayers);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numOfMaxPeople);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRequiredPlayers.setAdapter(arrayAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            choosenTypeOfSport = extras.getString("typeOfSport");
        }

        onOpenDateListener();
        onOpenTimeListener();
    }

    private void onOpenDateListener()
    {
        bDate = (Button) findViewById(R.id.bDate);

        bDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_DATEPICKER_ID);
                    }
                }
        );
    }

    private void onOpenTimeListener()
    {
        bTime = (Button) findViewById(R.id.bTime);

        bTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_TIMEPICKER_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DIALOG_DATEPICKER_ID:
                return new DatePickerDialog(this, onDatePickListener, dateYear, dateMonth, dateDay);
            case DIALOG_TIMEPICKER_ID:
                return new TimePickerDialog(this, onTimePickListener, timeHour, timeMinute, true); // true is 24hourview
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener onDatePickListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            choosenEventDate = day + "." + month + "." + year;
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimePickListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            choosenEventTime = hour + ": " + minute;
        }
    };

    public void createEvent(View v)
    {
        etTitle     = (EditText) findViewById(R.id.etTitle);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude  = (EditText) findViewById(R.id.etLatitude);
        tvMessage   = (TextView) findViewById(R.id.tvMessage);
        String choosenReqPlayers = sRequiredPlayers.getSelectedItem().toString();

        boolean isEventCreated = dbHelper.createEvent(
                etTitle.getText().toString(),
                User.getUserInstance().getUsername(),
                choosenTypeOfSport,
                choosenEventDate,
                choosenEventTime,
                Integer.parseInt(choosenReqPlayers),
                1, // joined people is 1 at the beginning
                etLongitude.getText().toString(),
                etLatitude.getText().toString()
        );

        if (isEventCreated) {

            etTitle.setText("");
            etLatitude.setText("");
            etLongitude.setText("");

            tvMessage.setText("Your event is now created.");
        } else {
            tvMessage.setText("Ooops! Problems with creating an event.");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences navigator = getSharedPreferences("navigatorDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = navigator.edit();

        editor.putString("typeOfEvent", choosenTypeOfSport);
        editor.commit();
    }
}
