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

    private DBHelper dbHelper;
    private ArrayAdapter<String> arrayAdapter;
    private String sportFilter, choosenEventDate, choosenEventTime;
    private String[] peopleLimitOnEachEvent;

    private Button bDate, bTime, bCreate;
    private EditText etTitle, etLongitude, etLatitude;
    private TextView tvMessage;
    private Spinner sRequiredPlayers;

    private int dateYear, dateMonth, dateDay,
                timeHour, timeMinute, timeSecond;

    static final int DIALOG_DATEPICKER_ID = 0,
                     DIALOG_TIMEPICKER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initActivityComponents();
        setActivityComponents();
        getExtrasFromPreviousActivity();
        onOpenDateListener();
        onOpenTimeListener();
    }

    private void setActivityComponents()
    {
        Calendar calendar = Calendar.getInstance();
        dateYear   = calendar.get(Calendar.YEAR);
        dateMonth  = calendar.get(Calendar.MONTH);
        dateDay    = calendar.get(Calendar.DAY_OF_MONTH);
        timeHour   = calendar.get(Calendar.HOUR);
        timeMinute = calendar.get(Calendar.MINUTE);
        timeSecond = calendar.get(Calendar.SECOND);

        setSpinnerView();
    }

    private void setSpinnerView()
    {
        peopleLimitOnEachEvent = getResources().getStringArray(R.array.number_of_people);
        sRequiredPlayers = (Spinner) findViewById(R.id.sRequiredPlayers);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, peopleLimitOnEachEvent);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRequiredPlayers.setAdapter(arrayAdapter);
    }

    private void initActivityComponents()
    {
        etTitle     = (EditText) findViewById(R.id.etTitle);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        etLatitude  = (EditText) findViewById(R.id.etLatitude);
        tvMessage   = (TextView) findViewById(R.id.tvMessage);
        dbHelper = new DBHelper(this);
    }

    private void getExtrasFromPreviousActivity()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sportFilter = extras.getString("sportFilter");
        }
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
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
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
        String choosenReqPlayers = sRequiredPlayers.getSelectedItem().toString();

        boolean isEventCreated = dbHelper.createEvent(
                etTitle.getText().toString(),
                User.getUserInstance().getUsername(),
                sportFilter,
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
}
