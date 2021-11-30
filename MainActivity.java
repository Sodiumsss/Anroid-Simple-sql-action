package com.example.mysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    sqlad mysql;


    private Cursor myCursor;
    private ListView myListView;


    TextView thename;
    TextView theage;
    TextView theheight;
    Button add;
    Button edit;
    Button delete;
    Button search;

    int id;
    String name;
    String age;
    String height;
    void getinf()
    {
        name= thename.getText().toString();
        age= theage.getText().toString();
        height= theheight.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        thename=findViewById(R.id.editname);
        theage=findViewById(R.id.editage);
        theheight=findViewById(R.id.editheight);
        add=findViewById(R.id.add);
        edit=findViewById(R.id.edit);
        delete=findViewById(R.id.delete);
        search=findViewById(R.id.search);


        myListView=findViewById(R.id.myview);

        mysql=new sqlad(this);

        myCursor=mysql.select();


        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.myview,myCursor,
                new String[]{"_id","name","age","height"},new int[]{R.id.id,R.id.name,R.id.age,R.id.height},0);

        myListView.setAdapter(adapter);


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCursor.moveToPosition(i);
                id=myCursor.getInt(0);
                thename.setText(myCursor.getString(1));
                theage.setText(myCursor.getString(2));
                theheight.setText(myCursor.getString(3));

            }
        });



        add.setOnClickListener(view->
        {
            getinf();

            mysql.insert(name,age,height);

            myCursor.requery();
            myListView.invalidateViews();


        });
        edit.setOnClickListener(view->
        {
            getinf();
            mysql.update(id,name,age,height);
            myCursor.requery();
            myListView.invalidateViews();
        });
        delete.setOnClickListener(view->
        {
            mysql.delete(id);
            myCursor.requery();
            myListView.invalidateViews();
        });
        search.setOnClickListener(view->
        {
            String searchage=theage.getText().toString();

            Cursor find=mysql.find(searchage);
            String message="";
            find.moveToFirst();
            for (int i=0;i<find.getCount();i++)
            {
                String tmp=find.getString(2);
                int p=Integer.valueOf(tmp);

                if (p>=Integer.valueOf(searchage))
                {
                    message+=find.getString(0)+" "+find.getString(1)+" "+find.getString(2)+" "+find.getString(3)+"\n";
                }



                find.moveToNext();

            }


            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示:");
            builder.setMessage(message);
            builder.show();





        });


    }

}