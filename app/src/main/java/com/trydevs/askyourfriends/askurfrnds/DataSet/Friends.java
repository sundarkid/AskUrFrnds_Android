package com.trydevs.askyourfriends.askurfrnds.DataSet;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


public class Friends implements Parcelable {
    public static final Creator<Friends> CREATOR = new Creator<Friends>() {
        public Friends createFromParcel(Parcel source) {
            return new Friends(source);
        }

        public Friends[] newArray(int size) {
            return new Friends[size];
        }
    };
    long sno, id;
    String name, mail, phone, request_id;

    public Friends() {
        sno = 0;
        id = 0;
        name = "";
        mail = "";
        phone = "";
        request_id = "";
    }

    protected Friends(Parcel in) {
        this.sno = in.readLong();
        this.id = in.readLong();
        this.name = in.readString();
        this.mail = in.readString();
        this.phone = in.readString();
        this.request_id = in.readString();
    }

    public static Friends getFriendsFromJSONObject(JSONObject object) {
        Friends f = new Friends();
        try {
            if (object.has("name"))
                f.setName(object.getString("name"));
            if (object.has("user_id"))
                f.setId(object.getLong("user_id"));
            if (object.has("mail"))
                f.setMail(object.getString("mail"));
            if (object.has("phone"))
                f.setPhone(object.getString("phone"));
            if (object.has("sno"))
                f.setSno(object.getLong("sno"));
            if (object.has("request_id"))
                f.setRequest_id(object.getString("request_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return f;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public JSONObject getJSONObjectID() {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject getJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", getId());
            object.put("name", getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sno);
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mail);
        dest.writeString(this.phone);
        dest.writeString(this.request_id);
    }
}
