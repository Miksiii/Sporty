package serenity.rs.sporty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Milan on 8/17/2016.
 */
public class EventAdapter extends BaseAdapter implements View.OnCreateContextMenuListener
{
    private Context ctx;
    private ArrayList<Event> eventsList;
    private DBHelper dbHelper;

    // view holders
    private TextView  tvAuthor, tvDescription, tvTitle, tvGoing;
    private Button    bLocation, bJoin;
    private ImageView ivIconDelete;
    private ListView  lvEvents;

    public EventAdapter(Context ctx, ArrayList<Event> eventsList)
    {
        this.ctx = ctx;
        this.eventsList = eventsList;
    }

    @Override
    public int getCount()
    {
        return eventsList.size();
    }

    @Override
    public Event getItem(int indexPosition)
    {
        return eventsList.get(indexPosition);
    }

    @Override
    public long getItemId(int indexPosition)
    {
        return indexPosition;
    }

    private void onEventJoinListener(final int indexPosition, final ViewGroup parent)
    {
        bJoin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Event tmpEvent = getItem(indexPosition);

                        // only if not exist user can join
                        if(!User.getUserInstance().getCheckedinEventIDs().contains(tmpEvent.getId())) {

                            int peopleGoing = tmpEvent.getJoinedPeople();
                            ++peopleGoing;

                            boolean isUpdatedEvent = dbHelper.updateEvent(tmpEvent.getId(), peopleGoing);

                            if (isUpdatedEvent)
                            {
                                tmpEvent.setJoinedPeople(peopleGoing);
                                User.getUserInstance().checkinForEvent(tmpEvent.getId());
                                Toast.makeText(parent.getContext(), "You are now part of this event.", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(parent.getContext(), "You are already checked in here.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void onEventDestroyListener(final int indexPosition, final ViewGroup parent)
    {
        ivIconDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Event tmpEvent = getItem(indexPosition);

                        int eventDestroyedEntry = dbHelper.destroyEventWithId(String.valueOf(tmpEvent.getId()));

                        if (eventDestroyedEntry > 0)
                        {
                            Toast.makeText(
                                    parent.getContext(),
                                    "Event '" + tmpEvent.getTitle() + "' is now deleted.",
                                    Toast.LENGTH_SHORT).show();
                            eventsList.remove(indexPosition);
                            notifyDataSetChanged();
                        } else
                        {
                            Toast.makeText(parent.getContext(), "Ooops! Problem with deleting event..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void onEventLocationListener(final int indexPosition, final ViewGroup parent)
    {
        bLocation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Event tmpEvent = getItem(indexPosition);

                        Intent switchToGoogleMapActivity = new Intent(parent.getContext(), ActivityGoogleMap.class);
                        switchToGoogleMapActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        switchToGoogleMapActivity.putExtra("longitude", tmpEvent.getLongitude());
                        switchToGoogleMapActivity.putExtra("latitude", tmpEvent.getLatitude());
                        switchToGoogleMapActivity.putExtra("title", tmpEvent.getTitle());
                        parent.getContext().startActivity(switchToGoogleMapActivity);
                    }
                }
        );
    }

    @Override
    public View getView(final int indexPosition, View convertView, final ViewGroup parent) {

        View v = View.inflate(ctx, R.layout.pattern_events, null);
        dbHelper = new DBHelper(ctx);

        initViewHolders(v);
        setViewHoldersValues(v, indexPosition);
        setViewHoldersAdminExtraValues(v, indexPosition);
        onEventJoinListener(indexPosition, parent);
        onEventDestroyListener(indexPosition, parent);
        onEventLocationListener(indexPosition, parent);
        tvAuthor.setOnCreateContextMenuListener(this);

        return v;
    }

    private void initViewHolders(View v)
    {
        tvAuthor      = (TextView) v.findViewById(R.id.tvAuthor);
        tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        tvTitle       = (TextView) v.findViewById(R.id.tvTitle);
        tvGoing       = (TextView) v.findViewById(R.id.tvGoing);
        bLocation     = (Button) v.findViewById(R.id.bLocation);
        bJoin         = (Button) v.findViewById(R.id.bJoin);
        ivIconDelete  = (ImageView) v.findViewById(R.id.ivIconDelete);
        lvEvents      = (ListView) v.findViewById(R.id.lvEvents);
    }

    private void setViewHoldersValues(View v, int indexPosition)
    {
        Event tmpEvent = eventsList.get(indexPosition);

        tvAuthor.setText("@" + tmpEvent.getAuthor());
        tvDescription.setText("wants to play " + tmpEvent.getType() +
                              " on " + tmpEvent.getDate() +
                              " at " + tmpEvent.getTime() + "h.");
        tvTitle.setText("\"" + tmpEvent.getTitle() + "\"");
        tvGoing.setText("Going " + tmpEvent.getJoinedPeople() + "/" + tmpEvent.getRequiredPeople());
    }

    private void setViewHoldersAdminExtraValues(View v, int indexPosition) 
    {
        Event tmpEvent = eventsList.get(indexPosition);
        
        if (tmpEvent.getAuthor().equals(User.getUserInstance().getUsername())) {
            ivIconDelete.setVisibility(View.VISIBLE);
            bJoin.setEnabled(false);
        } else {
            ivIconDelete.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method is implemented and overrided by ActivityEvents activity.
     * @param contextMenu
     * @param view
     * @param contextMenuInfo
     */
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {}
}
