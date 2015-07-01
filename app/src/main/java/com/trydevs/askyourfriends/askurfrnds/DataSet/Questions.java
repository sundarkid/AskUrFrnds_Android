package com.trydevs.askyourfriends.askurfrnds.DataSet;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Questions implements Parcelable {
    public static final Parcelable.Creator<Questions> CREATOR = new Parcelable.Creator<Questions>() {
        public Questions createFromParcel(Parcel source) {
            return new Questions(source);
        }

        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
    int answer_Id;
    long id, q_id;
    long group;
    String question;
    String[] options;

    public Questions() {
        options = new String[4];
        q_id = 0;
        question = "";
        options[0] = "";
        options[1] = "";
        options[2] = "";
        options[3] = "";
        id = 0;
        group = 0;
        answer_Id = 0;
    }

    private Questions(Parcel in) {
        this.answer_Id = in.readInt();
        this.id = in.readLong();
        this.group = in.readLong();
        this.question = in.readString();
        this.options = in.createStringArray();
    }

    public static Questions getQuestionsFromJson(JSONObject object) {
        Questions questions = new Questions();
        try {
            if (object.has(UrlLinksNames.getJsonQuestionId()))
                questions.setQ_id(object.getLong(UrlLinksNames.getJsonQuestionId()));
            if (object.has(UrlLinksNames.getJsonGroupId()))
                questions.setGroup(object.getLong(UrlLinksNames.getJsonGroupId()));
            if (object.has(UrlLinksNames.getJsonQuestion()))
                questions.setQuestion(object.getString(UrlLinksNames.getJsonQuestion()));
            if (object.has(UrlLinksNames.getJsonOptionA()))
                questions.setOptionA(object.getString(UrlLinksNames.getJsonOptionA()));
            if (object.has(UrlLinksNames.getJsonOptionB()))
                questions.setOptionB(object.getString(UrlLinksNames.getJsonOptionB()));
            if (object.has(UrlLinksNames.getJsonOptionC()))
                questions.setOptionC(object.getString(UrlLinksNames.getJsonOptionC()));
            if (object.has(UrlLinksNames.getJsonOptionD()))
                questions.setOptionD(object.getString(UrlLinksNames.getJsonOptionD()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOptionA() {
        return options[0];
    }

    public void setOptionA(String optionA) {
        this.options[0] = optionA;
    }

    public String getOptionB() {
        return options[1];
    }

    public void setOptionB(String optionB) {
        this.options[1] = optionB;
    }

    public String getOptionC() {
        return options[2];
    }

    public void setOptionC(String optionC) {
        this.options[2] = optionC;
    }

    public String getOptionD() {
        return options[3];
    }

    public void setOptionD(String optionD) {
        this.options[3] = optionD;
    }

    public long getQ_id() {
        return q_id;
    }

    public void setQ_id(long q_id) {
        this.q_id = q_id;
    }

    public String getAnswer() {
        return options[getAnswer_Id()];
    }

    public void setAnswer(int option) {
        setAnswer_Id(option);
    }

    public int getAnswer_Id() {
        return answer_Id;
    }

    public void setAnswer_Id(int answer_Id) {
        this.answer_Id = answer_Id;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public JSONObject getJSONObjectQuestion() {
        JSONObject object = new JSONObject();
        try {
            object.put(UrlLinksNames.getJsonQuestionId(), getQ_id());
            object.put(UrlLinksNames.getJsonQuestion(), getQuestion());
            object.put(UrlLinksNames.getJsonOptionA(), getOptionA());
            object.put(UrlLinksNames.getJsonOptionB(), getOptionB());
            object.put(UrlLinksNames.getJsonOptionC(), getOptionC());
            object.put(UrlLinksNames.getJsonOptionD(), getOptionD());
            object.put(UrlLinksNames.getJsonAnswer(), getAnswer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject getJSONObjectAnswers() {
        JSONObject object = new JSONObject();
        try {
            object.put(UrlLinksNames.getJsonQuestionId(), getId());
            object.put(UrlLinksNames.getJsonAnswer(), getAnswer());
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
        dest.writeInt(this.answer_Id);
        dest.writeLong(this.id);
        dest.writeLong(this.group);
        dest.writeString(this.question);
        dest.writeStringArray(this.options);
    }
}
