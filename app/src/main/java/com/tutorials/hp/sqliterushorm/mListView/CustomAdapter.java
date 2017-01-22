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
 * Created by Oclemmy on 5/5/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    List<Spacecraft> spacecrafts;
    LayoutInflater inflater;
    Spacecraft spacecraft;

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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


    public Spacecraft getSelectedSpacecraft()
    {
        return spacecraft;
    }

}








