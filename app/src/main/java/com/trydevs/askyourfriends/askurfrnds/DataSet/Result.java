package com.trydevs.askyourfriends.askurfrnds.DataSet;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sundareswaran on 28-06-2015.
 */
public class Result implements Parcelable {
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
    long sno, group_no, taker_id, creator_id;
    int marks, total;
    String date, name;

    public Result() {
        sno = 0;
        group_no = 0;
        taker_id = 0;
        creator_id = 0;
        marks = 0;
        total = 0;
        date = "";
        name = "";
    }

    protected Result(Parcel in) {
        this.sno = in.readLong();
        this.group_no = in.readLong();
        this.taker_id = in.readLong();
        this.creator_id = in.readLong();
        this.marks = in.readInt();
        this.total = in.readInt();
        this.date = in.readString();
        this.name = in.readString();
    }

    public static Result getResultFromJSON(JSONObject object) {
        Result result = new Result();
        try {
            if (object.has(UrlLinksNames.getJsonSno()))
                result.setSno(object.getLong(UrlLinksNames.getJsonSno()));
            if (object.has(UrlLinksNames.getJsonName()))
                result.setName(object.getString(UrlLinksNames.getJsonName()));
            if (object.has(UrlLinksNames.getJsonTakerId()))
                result.setTaker_id(object.getLong(UrlLinksNames.getJsonTakerId()));
            if (object.has(UrlLinksNames.getJsonCreatorId()))
                result.setCreator_id(object.getLong(UrlLinksNames.getJsonCreatorId()));
            if (object.has(UrlLinksNames.getJsonGroupId()))
                result.setGroup_no(object.getLong(UrlLinksNames.getJsonGroupId()));
            if (object.has(UrlLinksNames.getJsonMarks()))
                result.setMarks(object.getInt(UrlLinksNames.getJsonMarks()));
            if (object.has(UrlLinksNames.getJsonTotal()))
                result.setTotal(object.getInt(UrlLinksNames.getJsonTotal()));
            if (object.has(UrlLinksNames.getJsonDate()))
                result.setDate(object.getString(UrlLinksNames.getJsonDate()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public long getGroup_no() {
        return group_no;
    }

    public void setGroup_no(long group_no) {
        this.group_no = group_no;
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public long getTaker_id() {
        return taker_id;
    }

    public void setTaker_id(long taker_id) {
        this.taker_id = taker_id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sno);
        dest.writeLong(this.group_no);
        dest.writeLong(this.taker_id);
        dest.writeLong(this.creator_id);
        dest.writeInt(this.marks);
        dest.writeInt(this.total);
        dest.writeString(this.date);
        dest.writeString(this.name);
    }
}
