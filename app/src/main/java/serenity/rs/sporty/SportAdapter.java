package serenity.rs.sporty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Milan on 8/16/2016.
 */
public class SportAdapter extends BaseAdapter{

    private Context ctx;
    private ArrayList<Sport> sportsList;
    private TextView tvTitle;
    private TextView tvDescription;

    public SportAdapter(Context ctx, ArrayList<Sport> sportsList)
    {
        this.ctx = ctx;
        this.sportsList = sportsList;
    }

    @Override
    public int getCount()
    {
        return sportsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return sportsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(ctx, R.layout.pattern_sport_chooser, null);

        tvTitle = (TextView) v.findViewById(R.id.tvAuthor);
        tvDescription = (TextView) v.findViewById(R.id.tvDescription);

        tvTitle.setText(sportsList.get(i).getTitle());
        tvDescription.setText(sportsList.get(i).getDescription());

        return v;
    }
}
