package com.hupp.parsecrudandroid;

/**
 * Created by Hupp Technologies on 16/9/15.
 */
public class DataObject {

    private String id;
    private String name;

    DataObject(){
    }

    DataObject (String mName){
        name = mName;
    }

    DataObject (String objID , String mName){
        id = objID;
        name = mName;
    }

    public String getId() {
        return id;
    }

    public void setId(String S_Id) {
        this.id = S_Id;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
