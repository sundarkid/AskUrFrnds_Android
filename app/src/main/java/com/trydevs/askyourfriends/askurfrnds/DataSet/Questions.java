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
            if (object.has("q_no"))
                questions.setId(object.getLong("q_no"));
            if (object.has("group_no"))
                questions.setGroup(object.getLong("group_no"));
            if (object.has("question"))
                questions.setQuestion(object.getString("question"));
            if (object.has("optionA"))
                questions.setOptionA(object.getString("optionA"));
            if (object.has("optionB"))
                questions.setOptionB(object.getString("optionB"));
            if (object.has("optionC"))
                questions.setOptionC(object.getString("optionC"));
            if (object.has("optionD"))
                questions.setOptionD(object.getString("optionD"));
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
            object.put("question", getQuestion());
            object.put("optionA", getOptionA());
            object.put("optionB", getOptionB());
            object.put("optionC", getOptionC());
            object.put("optionD", getOptionD());
            object.put("answer", getAnswer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject getJSONObjectAnswers() {
        JSONObject object = new JSONObject();
        try {
            object.put("id_no", getId());
            object.put("answer", getAnswer());
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
