package com.tutorials.hp.sqliterushorm.mListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.tutorials.hp.sqliterushorm.R;
import com.tutorials.hp.sqliterushorm.mDB.Spacecraft;
import java.util.List;


/**
 * Created by Oclemmy for ProgrammingWizards TV Channel and http://www.camposha.info.
 - Our adapter class.
 - Derives from android.widget.BaseAdapter.
 - Here we: inflate our model xml layout to viewitems and recycle it, bind data to these viewitems.
 - The data we bind is passed to us via constructor.
 - Apart from the data being passed us, we are also passed a Context object that will help us getSystemService that we  need for our
 inflation of model layout.
 - Being that we derive from BaseAdapter, we override getCount() which returns total count of our data, getItem() which returns each
 data object,getItemId() which returns the object's id, and getView() to return us its view()
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    List<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    Spacecraft spacecraft;

    /*
    Constructor
     */
    public CustomAdapter(Context c, List<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    Return inflated view object.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //INFLATE VIEW
        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.model,parent,false);
        }

        //BIND DATA
        MyViewHolder holder=new MyViewHolder(convertView);
        holder.nameTxt.setText(spacecrafts.get(position).getName());
        holder.propTxt.setText(spacecrafts.get(position).getPropellant());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, spacecrafts.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {
                spacecraft= (Spacecraft) getItem(position);
            }
        });

        return convertView;
    }

    /*
    Spacecraft selected
     */
    public Spacecraft getSelectedSpacecraft()
    {
        return spacecraft;
    }

}








