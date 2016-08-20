package serenity.rs.sporty;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityEvents extends AppCompatActivity
{
    private DBHelper dbHelper;
    private ArrayList<Event> eventsList;
    private EventAdapter eventAdapter;

    private ListView lvEvents;
    private ImageView bCreate;

    private String sportFilter;
    private Event eventFromContextMenu;
    private User userFromContextMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getExtrasFromPreviousActivity();
        initActivityComponents();
        registerForContextMenu(lvEvents);
        onEventChooseListener();
    }

    public void getExtrasFromPreviousActivity()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sportFilter = extras.getString("sportFilter");
            SportFilter.getSportFilterInstance().setSportFilter(sportFilter);
        }
    }

    public void initActivityComponents()
    {
        bCreate = (ImageView) findViewById(R.id.bCreate);
        lvEvents = (ListView) findViewById(R.id.lvEvents);
        dbHelper = new DBHelper(this);
        eventsList = new ArrayList<Event>();
        eventsList = dbHelper.getListOfEvents(sportFilter);
        eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
        lvEvents.setAdapter(eventAdapter);
    }

    public void onEventChooseListener()
    {
        lvEvents.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String value = adapterView.getItemAtPosition(position).toString();
                        Toast.makeText(ActivityEvents.this, id + "GET ITEM: " + value, Toast.LENGTH_SHORT).show();
                    };
                }
        );

    }

    public void createEvent(View v)
    {
        Intent switchToCreateEventActivity = new Intent("serenity.rs.sporty.ActivityCreateEvent");
        switchToCreateEventActivity.putExtra("sportFilter", sportFilter);
        startActivity(switchToCreateEventActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_events_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.my_events:
                eventsList = dbHelper.getListOfEventsCreatedBy(User.getUserInstance().getUsername(), sportFilter);
                eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
                lvEvents.setAdapter(eventAdapter); // note: could notify instead
                return true;

            case R.id.all_events:
                eventsList = dbHelper.getListOfEvents(sportFilter);
                eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
                lvEvents.setAdapter(eventAdapter);
                return true;

            case R.id.other_events:
                Intent  switchToSportsChooserActivity = new Intent("serenity.rs.sporty.ActivitySportChooser");
                startActivity(switchToSportsChooserActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method invokes when user click back on ActivityCreateEvent activity.
     * We get the sport filter from the previously created sportFilterInstance
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        eventsList = dbHelper.getListOfEvents(SportFilter.getSportFilterInstance().getSportFilter());
        eventAdapter = new EventAdapter(getApplicationContext(), eventsList);
        lvEvents.setAdapter(eventAdapter); // could be notify?
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
    {
        getMenuInflater().inflate(R.menu.menu_context_contact_user, contextMenu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)contextMenuInfo;
        eventFromContextMenu = eventsList.get(info.position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        userFromContextMenu = dbHelper.getUserWithCredentials(eventFromContextMenu.getAuthor());

        switch (item.getItemId())
        {
            case R.id.opt_visit_url:
                Intent openUsersSocialProfile = new Intent("android.intent.action.VIEW");

                if (!userFromContextMenu.getLink().startsWith("http"))  {
                    userFromContextMenu.setLink("http://www." + userFromContextMenu.getLink());
                }

                openUsersSocialProfile.setData(Uri.parse(userFromContextMenu.getLink()));
                startActivity(openUsersSocialProfile);
                return true;

            case R.id.opt_make_call:
                Intent makeCall = new Intent(android.content.Intent.ACTION_DIAL);
                startActivity(makeCall);
                return true;

            case R.id.opt_send_sms:
                Intent sendSMS = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.fromParts("sms", "", null)
                );
                startActivity(sendSMS);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

}
