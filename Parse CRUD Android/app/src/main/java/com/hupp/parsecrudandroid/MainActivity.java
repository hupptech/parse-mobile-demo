package com.hupp.parsecrudandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ImageButton btn_Add;
    EditText edt_Add;
    List<DataObject> myDataset = new ArrayList<DataObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_main);

        btn_Add=(ImageButton)findViewById(R.id.btn_Add);
        edt_Add = (EditText)findViewById(R.id.edt_Add);

        // This method creates new record in parse

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject student = new ParseObject("Student");
                student.put("name", edt_Add.getText().toString());
                try {
                    student.save();
                    edt_Add.setText("");
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                new getAllRecords().execute();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new getAllRecords().execute();

    }

    public class getAllRecords extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // Set progressdialog message
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Student");
            query.orderByDescending("_created_at");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
    }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myDataset.clear();
            for (ParseObject num : ob) {
                DataObject d = new DataObject();
                String objID = num.getObjectId();
                String name = num.getString("name");
                d.setId(objID);
                d.setName(name);
                myDataset.add(d);
           }
            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }

}
