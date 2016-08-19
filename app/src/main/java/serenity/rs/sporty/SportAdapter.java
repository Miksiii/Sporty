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
public class SportAdapter extends BaseAdapter
{
    private Context ctx;
    private ArrayList<Sport> sportsList;

    // view holders for Sport attributes
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
    public Object getItem(int indexPosition)
    {
        return sportsList.get(indexPosition);
    }

    @Override
    public long getItemId(int indexPosition)
    {
        return indexPosition;
    }

    @Override
    public View getView(int indexPosition, View view, ViewGroup viewGroup) {

        View v = View.inflate(ctx, R.layout.pattern_sport_chooser, null);

        initViewHolders(v);
        setViewHoldersValues(v, indexPosition);

        return v;
    }

    public void initViewHolders(View v)
    {
        tvTitle       = (TextView) v.findViewById(R.id.tvAuthor);
        tvDescription = (TextView) v.findViewById(R.id.tvDescription);
    }

    public void setViewHoldersValues(View v, int indexPosition)
    {
        Sport tmpSport = sportsList.get(indexPosition);

        tvTitle.setText(tmpSport.getTitle());
        tvDescription.setText(tmpSport.getDescription());
    }

}
