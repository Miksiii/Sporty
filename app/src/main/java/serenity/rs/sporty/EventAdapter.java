package serenity.rs.sporty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Milan on 8/17/2016.
 */
public class EventAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Event> eventsList;
    private TextView tvAuthor, tvDescription, tvTitle, tvGoing;
    private Button bLocation, bJoin;
    private ImageView ivIconDelete;
    private ListView lvEvents;
    private boolean isMyEvents;
    private DBHelper dbHelper;

    public EventAdapter(Context ctx, ArrayList<Event> eventsList, boolean isMyEvents)
    {
        this.ctx = ctx;
        this.eventsList = eventsList;
        this.isMyEvents = isMyEvents;
    }

    @Override
    public int getCount()
    {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(ctx, R.layout.pattern_events, null);

        tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
        tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvGoing = (TextView) v.findViewById(R.id.tvGoing);
        bLocation = (Button) v.findViewById(R.id.bLocation);
        bJoin = (Button) v.findViewById(R.id.bJoin);
        ivIconDelete = (ImageView) v.findViewById(R.id.ivIconDelete);
        lvEvents = (ListView) v.findViewById(R.id.lvEvents);

        if (eventsList.get(i).getAuthor().equals(User.getUserInstance().getUsername())) {
            ivIconDelete.setVisibility(View.VISIBLE);
            bJoin.setEnabled(false);
        } else {
            ivIconDelete.setVisibility(View.INVISIBLE);
        }


        tvAuthor.setText("@" + eventsList.get(i).getAuthor());
        tvDescription.setText("wants to play " + eventsList.get(i).getType() + " on " + eventsList.get(i).getDate() + " at " + eventsList.get(i).getTime() + "h.");
        tvTitle.setText("\"" + eventsList.get(i).getTitle() + "\"");
        tvGoing.setText("Going " + eventsList.get(i).getJoinedPeople() + "/" + eventsList.get(i).getRequiredPeople());



        return v;
    }


}
