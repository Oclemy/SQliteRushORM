package com.tutorials.hp.sqliterushorm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tutorials.hp.sqliterushorm.mDB.Spacecraft;
import com.tutorials.hp.sqliterushorm.mListView.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    EditText nameEditText,propellantEditTxt;
    Button saveBtn,retrieveBtn;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
    CustomAdapter adapter;
    final Boolean forUpdate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv= (ListView) findViewById(R.id.lv);
        adapter=new CustomAdapter(this,spacecrafts);

        this.retrieve();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(false);
            }
        });
    }

    private void displayDialog(Boolean forUpdate)
    {
        Dialog d=new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        propellantEditTxt= (EditText) d.findViewById(R.id.propellantEditTxt);

        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);

        if(!forUpdate)
        {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spacecraft s=new Spacecraft();
                    save(s);
                    nameEditText.setText("");
                    propellantEditTxt.setText("");
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retrieve();
                }
            });
        }else {

            //SET SELECTED TEXT
            nameEditText.setText(adapter.getSelectedSpacecraft().getName());
            propellantEditTxt.setText(adapter.getSelectedSpacecraft().getPropellant());


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spacecraft oldSpacecraft=adapter.getSelectedSpacecraft();
                    Spacecraft s=new RushSearch().whereId(oldSpacecraft.getId()).findSingle(Spacecraft.class);

                    save(s);


                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  retrieve();

                }
            });
        }

        d.show();

    }

    private void retrieve()
    {
        List<Spacecraft> spacecrafts=new RushSearch().find(Spacecraft.class);
        if(spacecrafts.size()>0)
        {
            adapter=new CustomAdapter(MainActivity.this,spacecrafts);
            lv.setAdapter(adapter);
        }

    }
    private void save(Spacecraft s)
    {
        s.setName(nameEditText.getText().toString());
        s.setPropellant(propellantEditTxt.getText().toString());
        s.save();
        retrieve();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title=item.getTitle();
        if(title=="NEW")
        {
            displayDialog(!forUpdate);

        }else  if(title=="EDIT")
        {
            displayDialog(forUpdate);

        }else  if(title=="DELETE")
        {
            Spacecraft oldSpacecraft=adapter.getSelectedSpacecraft();
            Spacecraft s=new RushSearch().whereId(oldSpacecraft.getId()).findSingle(Spacecraft.class);
            s.delete();

            //TO REFRESH
            //retrieve();

        }
        return super.onContextItemSelected(item);
    }
}
