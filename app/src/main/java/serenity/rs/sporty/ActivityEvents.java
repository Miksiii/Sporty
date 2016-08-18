package serenity.rs.sporty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventsToDisplay = extras.getString("selectedTypeOfSport");
            UtilSportChooser.getUtilSportChooserInstance().setCurrentlyActiveSport(eventsToDisplay);
        }

        lvEvents = (ListView) findViewById(R.id.lvEvents);
        dbHelper = new DBHelper(this);
        eventsList = new ArrayList<Event>();
        eventsList = dbHelper.getListOfEvents(eventsToDisplay);

        eventAdapter = new EventAdapter(getApplicationContext(), eventsList, false);
        lvEvents.setAdapter(eventAdapter);

        onEventChooseListener();


    }

    public void onEventChooseListener()
    {
        lvEvents.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String value = adapterView.getItemAtPosition(position).toString();
                        Toast.makeText(ActivityEvents.this, "GET ITEM: " + value, Toast.LENGTH_SHORT).show();
                    };
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_events_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.my_events:
                eventsList = dbHelper.getListOfEventsCreatedBy(User.getUserInstance().getUsername(), eventsToDisplay);
                eventAdapter = new EventAdapter(getApplicationContext(), eventsList, false);
                lvEvents.setAdapter(eventAdapter);
                return true;
            case R.id.all_events:
                eventsList = dbHelper.getListOfEvents(eventsToDisplay);
                eventAdapter = new EventAdapter(getApplicationContext(), eventsList, false);
                lvEvents.setAdapter(eventAdapter);
                return true;
            case R.id.other_events:
                Intent  switchToSportsChooserActivity = new Intent("serenity.rs.sporty.ActivitySportChooser");
                startActivity(switchToSportsChooserActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void toaster(Context ctx, int clickedEventId)
    {
        Toast.makeText(ctx, "clicked event id " + clickedEventId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        eventsList = dbHelper.getListOfEvents(UtilSportChooser.getUtilSportChooserInstance().getCurrentlyActiveSport());
        eventAdapter = new EventAdapter(getApplicationContext(), eventsList, false);
        lvEvents.setAdapter(eventAdapter);
    }
}
