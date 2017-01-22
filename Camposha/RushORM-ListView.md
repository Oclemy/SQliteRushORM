# Android SQLite RushORM ListView CRUD


How to INSERT SELECT UPDATE and DELETE SQLite database data to a ListView.

## Overview
- Insert to SQLite
- Retrieve from SQLite
- Bind to ListView
- Display Options in a ContextMenu
- You select whether to add new,edit or delete data via contextmenu when you right click a cardview

- Project Structure

![](/Camposha/demos/Project-Structure.PNG)

## Build.Gradle
First we are going to fetch our RushORM as a dependency in build.gradle :
```groovy
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:design:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'
    compile 'co.uk.rushorm:rushandroid:1.2.0'

}

```

Then in the build.gradle(Project leve) add maven as our repository as below :
```groovy
allprojects {
    repositories {
        maven {
            url "http://maven.rushorm.com"
        }
        jcenter()
    }
}
```

## Android Manifest
- First Register our MainApplication class.
- Then add the meta datas.
- You must add the database package

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.tutorials.hp.sqliterushorm">

    <!--REGISTER MAIN APP BY ADDING NAME-->
    <application
        android:name=".MainApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name"
            android:theme="@style/AppTheme.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
<!--META DATAS FOR OUR DATABASE-->
        <!--THIS FIRST LINE IS COMPULSORY.ADD YOUR DATABASE PACKAGE-->
        <meta-data android:name="Rush_classes_package" android:value="com.tutorials.hp.sqliterushorm.mDB" />
        <!-- Updating this will cause a database upgrade -->
        <meta-data android:name="Rush_db_version" android:value="2" />

        <!-- Database name -->
        <meta-data android:name="Rush_db_name" android:value="SpacecraftsDB.db" />

        <!-- Setting this to true will cause a migration to happen every launch,
        this is very handy during development although could cause data loss -->
        <meta-data android:name="Rush_debug" android:value="false" />

        <!-- Setting this to true mean that tables will only be created of classes that
        extend RushObject and are annotated with @RushTableAnnotation -->
        <meta-data android:name="Rush_requires_table_annotation" android:value="false" />

        <!-- Turning on logging can be done by settings this value to true -->
        <meta-data android:name="Rush_log" android:value="false" />

    </application>

</manifest>
```


# CLASSES

## MainApplication
- We extend the application class.
- We initialize our RushORM here and setup  our configurations.
- Make sure you register this class in AndroidManifest.xml

```java
package com.tutorials.hp.sqliterushorm;

import android.app.Application;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * Created by Oclemy on 12/8/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInitializeConfig config=new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);
    }
}

```

## Spacecraft
```java
package com.tutorials.hp.sqliterushorm.mDB;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Oclemy on 12/8/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Spacecraft extends RushObject {

    private String name,propellant;

    public Spacecraft() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropellant() {
        return propellant;
    }

    public void setPropellant(String propellant) {
        this.propellant = propellant;
    }
}


```

## LongClick Listener interface
```java
package com.tutorials.hp.sqliterushorm.mListView;

/**
 * Created by Oclemmy on 5/5/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public interface MyLongClickListener {

    void onItemLongClick();

}

```

ViewHolder class
```java
package com.tutorials.hp.sqliterushorm.mListView;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.tutorials.hp.sqliterushorm.R;


/**
 * Created by Oclemmy on 5/5/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class MyViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener {

    TextView nameTxt,propTxt;
    MyLongClickListener longClickListener;

    public MyViewHolder(View v) {
        nameTxt= (TextView) v.findViewById(R.id.nameTxt);
        propTxt= (TextView) v.findViewById(R.id.propellantTxt);

        v.setOnLongClickListener(this);
        v.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onItemLongClick();
        return false;
    }

    public void setLongClickListener(MyLongClickListener longClickListener)
    {
        this.longClickListener=longClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Action : ");
        menu.add(0, 0, 0, "NEW");
        menu.add(0,1,0,"EDIT");
        menu.add(0,2,0,"DELETE");


    }
}

```

## CustomAdapter class
```java
package com.tutorials.hp.sqliterushorm.mListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.tutorials.hp.sqliterushorm.R;
import com.tutorials.hp.sqliterushorm.mDB.Spacecraft;

import java.util.ArrayList;
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









```


## MainActivity
Finally here is our MainActivity class :
```java
package com.tutorials.hp.sqliterushorm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import co.uk.rushorm.core.RushCallback;
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
##### 
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

        }
        return super.onContextItemSelected(item);
    }
}

```

## Demos




- Input Dialog Save

![](/Camposha/demos/RushORM-Save.PNG)

- Edit SQLite data

![](/Camposha/demos/RushORM-Edit.PNG)

- SQLite ListView

![](/Camposha/demos/RushORM-ListView.PNG)

## How to Run.
Clone the project into your android studio or download and extract the project and import to your android studio.

## More
Visit http://camposha.info for more examples like these.

Author :
Oclemy,cheers. http://camposha.info
