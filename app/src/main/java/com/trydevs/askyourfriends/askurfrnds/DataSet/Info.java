package com.trydevs.askyourfriends.askurfrnds.DataSet;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Info implements Parcelable {
    public static final Creator<Info> CREATOR = new Creator<Info>() {
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
    long sno, user_id, group;
    String date, name;

    public Info() {
        sno = 0;
        group = 0;
        user_id = 0;
        name = "";
        date = "";
    }

    protected Info(Parcel in) {
        this.sno = in.readLong();
        this.user_id = in.readLong();
        this.group = in.readLong();
        this.date = in.readString();
        this.name = in.readString();
    }

    public static Info getInfoFromJSONObject(JSONObject object) {
        Info info = new Info();
        try {
            if (object.has(UrlLinksNames.getJsonUserId()))
                info.setUser_id(object.getLong(UrlLinksNames.getJsonUserId()));
            if (object.has(UrlLinksNames.getJsonName()))
                info.setName(object.getString(UrlLinksNames.getJsonName()));
            if (object.has(UrlLinksNames.getJsonGroupId()))
                info.setGroup(object.getLong(UrlLinksNames.getJsonGroupId()));
            if (object.has(UrlLinksNames.getJsonDate()))
                info.setDate(object.getString(UrlLinksNames.getJsonDate()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JSONObject getJSONData() {
        JSONObject object = new JSONObject();
        try {
            object.put(UrlLinksNames.getJsonDate(), getDate());
            object.put(UrlLinksNames.getJsonGroupId(), getGroup());
            object.put(UrlLinksNames.getJsonUserId(), getUser_id());
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
        dest.writeLong(this.user_id);
        dest.writeLong(this.group);
        dest.writeString(this.date);
        dest.writeString(this.name);
    }
}
