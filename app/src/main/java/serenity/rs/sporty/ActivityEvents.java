package serenity.rs.sporty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityEvents extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<Event> eventsList;
    private EventAdapter eventAdapter;
    private ListView lvEvents;
    private String eventsToDisplay;
    private Button bCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvEvents = (ListView) findViewById(R.id.lvEvents);
        dbHelper = new DBHelper(this);
        eventsList = new ArrayList<Event>();

        // fake data
        dbHelper.createEvent("veceras termin u hali sportova", "miksiii", "football", "22.03.2015.", "21:00", 10, 6, "22224", "24244");
        dbHelper.createEvent("veceras termin u hali sportova", "miksiii", "football", "22.03.2015.", "21:00", 10, 6, "22224", "24244");
        dbHelper.createEvent("odbojka na plazi!", "miksiii", "volleyball", "12.03.2015.", "12:00", 8, 3, "2224", "2424");
        dbHelper.createEvent("basket u centru!", "miksiii", "basketball", "11.05.2015.", "17:00", 6, 2, "1224", "2424");
        dbHelper.createEvent("tenis na betonskom!", "miksiii", "tennis", "22.03.2016.", "18:00", 2, 2, "2224", "2424");

        eventsList = dbHelper.getListOfEvents();

        eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
        lvEvents.setAdapter(eventAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventsToDisplay = extras.getString("selectedTypeOfSport");
        }

        onEventChooseListener();
    }

    private void onEventChooseListener()
    {
        lvEvents.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        Toast.makeText(ActivityEvents.this, "Geo location of it: " + eventsList.get(position).getLongitude() + ", " + eventsList.get(position).getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void createEvent(View v)
    {
        bCreate = (Button) findViewById(R.id.bCreate);

        Intent switchToCreateEventActivity = new Intent("serenity.rs.sporty.ActivityCreateEvent");
        switchToCreateEventActivity.putExtra("typeOfSport", eventsToDisplay); // eventsToDisplay is category of sport (eg. football, basket) predefined
        startActivity(switchToCreateEventActivity);
    }


}
