package serenity.rs.sporty;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivitySportChooser extends AppCompatActivity
{
    private DBHelper dbHelper;
    private ArrayList<Sport> sportsList;
    private SportAdapter sportAdapter;
    private ListView lvSportList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_chooser);
        initActivityComponents();
        onEventSelectListener();
    }

    private void initActivityComponents()
    {
        lvSportList  = (ListView) findViewById(R.id.lvSportList);
        dbHelper     = new DBHelper(this);
        sportsList   = new ArrayList<Sport>();
        sportsList   = dbHelper.getListOfSports();
        sportAdapter = new SportAdapter(getApplicationContext(), sportsList);
        lvSportList.setAdapter(sportAdapter);
    }

    private void onEventSelectListener()
    {
        lvSportList.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        Intent switchToEventsActivity = new Intent(ActivitySportChooser.this, ActivityEvents.class);
                        String sportFilter = sportsList.get(position).getTitle();
                        switchToEventsActivity.putExtra("sportFilter", sportFilter);
                        startActivity(switchToEventsActivity);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_sport_chooser_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.link_edit_profile:
                Intent switchToProfileChangerActivity = new Intent("serenity.rs.sporty.ActivityProfileChanger");
                startActivity(switchToProfileChangerActivity);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
