package com.hupp.parsecrudandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Hupp Technologies on 15/9/15.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    MyAdapter(){

    }

    private List<DataObject> mDataobject;
    int flag = 0;
    ParseObject query=new ParseObject("Student");
    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Student");

    public MyAdapter(List<DataObject> dataObjects){
        mDataobject = dataObjects;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {

        holder.tv_name.setText(mDataobject.get(position).getname());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    // This method deletes particular record on press of delete button. Here also object id is used to targeted the selected row
                    query.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException arg0) {
                            // TODO Auto-generated method stub
                            //for deleting perticular row
                            ParseObject.createWithoutData("Student", mDataobject.get(position).getId()).deleteEventually();
                            query.deleteInBackground();
                            //for remove from recyclerView
                            mDataobject.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,mDataobject.size());
                        }
                    });
                }
                catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        holder.btn_Edt.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(flag == 1) {
                try {
                    notifyItemChanged(position);
                    mDataobject.get(position).setName(holder.edt_name.getText().toString());
                    notifyDataSetChanged();
                    // This method updates the record on parse. It uses object id to update the correct record
                    parseQuery.getInBackground(mDataobject.get(position).getId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.put("name", holder.edt_name.getText().toString());
                                object.saveInBackground();
                            }
                        }
                    });

                    holder.tv_name.setVisibility(View.VISIBLE);
                    holder.edt_name.setVisibility(View.INVISIBLE);
                    holder.btn_Edt.setImageResource(R.drawable.ic_mode_edit_black_48dp);
                    flag = 0;
                }
                catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                holder.tv_name.setVisibility(View.INVISIBLE);
                holder.edt_name.setVisibility(View.VISIBLE);
                holder.btn_Edt.setImageResource(R.drawable.ic_done_black_24dp);
                String name = holder.tv_name.getText().toString();
                holder.edt_name.setText(name);
                flag = 1;
               }
              }
            });
    }

    @Override
    public int getItemCount() {
        return mDataobject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_name;
        public ImageButton btn_Edt,btn_delete;
        public EditText edt_name;

        public ViewHolder(View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            btn_Edt = (ImageButton)v.findViewById(R.id.btn_edt_textname);
            edt_name = (EditText)v.findViewById(R.id.edt_name);
            btn_delete = (ImageButton) v.findViewById(R.id.btn_delete);
        }
    }
}
