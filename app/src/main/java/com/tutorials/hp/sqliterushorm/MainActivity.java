package com.tutorials.hp.sqliterushorm;

/*
- IMPORTS
 */
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

/*

MAIN ACTIVITY CLASS
Methods: onCreate(),displayDialog(),onContextItemSelected()
1. Our MainActivity.Launcher Activity.
2. Inflates ContentMain.xml to show our ListView with sqlite data.
3. Displays our material input dialog when FAB button is clicked.

 */
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

    /*
    - Display Input Dialog.
    - We can show dialog either for insert or update, so we pass a boolean to identify why we are showing it.
     */
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

            //UPDATE SQLITE DATA USING RUSHORM.
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

        //SHOW OUR DIALOG.
        d.show();

    }

    /*
    - Retrieve SQLite data using the RushSearch().find() method.
    - This returns a list of spacecrafts.
    - We then bind it to our listview.
     */
    private void retrieve()
    {
        List<Spacecraft> spacecrafts=new RushSearch().find(Spacecraft.class);
        if(spacecrafts.size()>0)
        {
            adapter=new CustomAdapter(MainActivity.this,spacecrafts);
            lv.setAdapter(adapter);
        }

    }

    /*
    - Save Spacecraft to sqlite database by calling save() method.
     */
    private void save(Spacecraft s)
    {
        s.setName(nameEditText.getText().toString());
        s.setPropellant(propellantEditTxt.getText().toString());
        s.save();
        retrieve();

    }

    /*
    - We will be showing a cntextmenu when listview item is clicked.
    - Hence we override the onContextItemSelected() method, passing in a menuitem.
    - We then identify the clicked contextmenu item, be it new, edit or delete.
     */
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
