package serenity.rs.sporty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityMyEvents extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayList<Event> eventsList;
    private EventAdapter eventAdapter;
    private ListView lvEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        // Add back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvEvents = (ListView) findViewById(R.id.lvEvents);
        dbHelper = new DBHelper(this);
        eventsList = new ArrayList<Event>();
        //eventsList = dbHelper.getListOfEventsCreatedBy(User.getUserInstance().getUsername());

        //eventAdapter = new EventAdapter(getApplicationContext(), eventsList, true);
        //lvEvents.setAdapter(eventAdapter);
    }

}
