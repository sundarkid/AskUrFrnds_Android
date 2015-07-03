package com.trydevs.askyourfriends.askurfrnds.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.trydevs.askyourfriends.askurfrnds.DataSet.Friends;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Info;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Questions;
import com.trydevs.askyourfriends.askurfrnds.DataSet.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBAskUrFrnd {

    public static final int TABLE_QUESTIONS = 0;
    public static final int TABLE_FRIENDS = 1;
    public static final int TABLE_INFO = 2;
    public static final int TABLE_TEMP = 3;
    public static final int TABLE_SUGGESTIONS = 4;
    public static final int TABLE_RESULT = 5;

    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;

    public DBAskUrFrnd(Context context) {
        Log.d("Database", "creating writable instance");
        mySQLiteHelper = new MySQLiteHelper(context);
        database = mySQLiteHelper.getWritableDatabase();

    }

    public void deleteAllTableData() {
        for (int i = 0; i < 5; i++)
            database.delete(getTableName(i), null, null);
    }

    public void insertQuestions(List<Questions> list) {

        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_QUESTIONS) + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Questions current = list.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, Long.toString(current.getQ_id()));
            statement.bindString(3, Long.toString(current.getId()));
            statement.bindString(4, Long.toString(current.getGroup()));
            statement.bindString(5, current.getQuestion());
            statement.bindString(6, current.getOptionA());
            statement.bindString(7, current.getOptionB());
            statement.bindString(8, current.getOptionC());
            statement.bindString(9, current.getOptionD());
            statement.bindString(10, current.getAnswer());

            statement.execute();
        }

        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public void insertQuestionSuggestions(List<Questions> list) {

        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_SUGGESTIONS) + " VALUES (?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Questions current = list.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, current.getQuestion());
            statement.bindString(3, current.getOptionA());
            statement.bindString(4, current.getOptionB());
            statement.bindString(5, current.getOptionC());
            statement.bindString(6, current.getOptionD());
            statement.bindString(7, current.getAnswer());

            statement.execute();
        }

        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public void insertInfo(Info current) {
        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_INFO) + " VALUES (?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();

        statement.bindString(2, Long.toString(current.getUser_id()));
        statement.bindString(3, Long.toString(current.getGroup()));
        statement.bindString(4, current.getName());
        statement.bindString(5, current.getDate());

        statement.execute();

        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertInfoList(List<Info> list) {
        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_INFO) + " VALUES (?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Info current = list.get(i);
            statement.bindString(2, Long.toString(current.getUser_id()));
            statement.bindString(3, Long.toString(current.getGroup()));
            statement.bindString(4, current.getName());
            statement.bindString(5, current.getDate());

            statement.execute();
        }
        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertResults(List<Result> results) {
        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_RESULT) + " VALUES (?,?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < results.size(); i++) {
            Result current = results.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(1, Long.toString(current.getSno()));
            statement.bindString(2, Long.toString(current.getTaker_id()));
            statement.bindString(3, Long.toString(current.getCreator_id()));
            statement.bindString(4, Long.toString(current.getGroup_no()));
            statement.bindString(5, current.getName());
            statement.bindString(6, Integer.toString(current.getMarks()));
            statement.bindString(7, Integer.toString(current.getTotal()));
            statement.bindString(8, current.getDate());

            statement.execute();
        }

        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public List<Questions> readAllQuestions(int table) {
        List<Questions> list = Collections.emptyList();
        String[] columns = {
                MySQLiteHelper.COLUMN_SNO,
                MySQLiteHelper.COLUMN_QUESTION,
                MySQLiteHelper.COLUMN_OPTION_A,
                MySQLiteHelper.COLUMN_OPTION_B,
                MySQLiteHelper.COLUMN_OPTION_C,
                MySQLiteHelper.COLUMN_OPTION_D,
                MySQLiteHelper.COLUMN_ANSWER,
        };

        Cursor cursor = database.query(getTableName(table), columns, null, null, null, null, columns[0] + " DESC ");
        if (cursor != null && cursor.moveToFirst()) {
            list = new ArrayList<>();
            int index_question = cursor.getColumnIndex(MySQLiteHelper.COLUMN_QUESTION);
            int index_optionA = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_A);
            int index_optionB = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_B);
            int index_optionC = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_C);
            int index_optionD = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_D);
            do {
                Questions questions = new Questions();
                questions.setQuestion(cursor.getString(index_question));
                questions.setOptionA(cursor.getString(index_optionA));
                questions.setOptionB(cursor.getString(index_optionB));
                questions.setOptionC(cursor.getString(index_optionC));
                questions.setOptionD(cursor.getString(index_optionD));
                list.add(questions);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public void insertFriendsList(List<Friends> list) {
        database.delete(getTableName(TABLE_FRIENDS), null, null);
        //create a sql prepared statement
        String sql = "INSERT INTO " + getTableName(TABLE_FRIENDS) + " VALUES (?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Friends current = list.get(i);
            statement.clearBindings();
            //for a given column index, simply bind the data to be put inside that index
            statement.bindString(2, Long.toString(current.getId()));
            statement.bindString(3, current.getName());
            statement.bindString(4, current.getInstitution());
            statement.bindString(5, current.getMail());
            statement.bindString(6, current.getPhone());

            statement.execute();
        }

        //set the transaction as successful and end the transaction
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public List<Result> getResultList() {
        List<Result> results;
        String[] columns = {
                MySQLiteHelper.COLUMN_SNO,
                MySQLiteHelper.COLUMN_TAKER_ID,
                MySQLiteHelper.COLUMN_CREATOR_ID,
                MySQLiteHelper.COLUMN_GROUP,
                MySQLiteHelper.COLUMN_NAME,
                MySQLiteHelper.COLUMN_MARKS,
                MySQLiteHelper.COLUMN_TOTAL,
                MySQLiteHelper.COLUMN_DATE
        };
        Cursor cursor = database.query(getTableName(TABLE_RESULT), columns, null, null, null, null, columns[0] + " DESC ");
        if (cursor != null && cursor.moveToFirst()) {
            int index_sno = cursor.getColumnIndex(MySQLiteHelper.COLUMN_SNO);
            int index_takerId = cursor.getColumnIndex(MySQLiteHelper.COLUMN_TAKER_ID);
            int index_creatorId = cursor.getColumnIndex(MySQLiteHelper.COLUMN_CREATOR_ID);
            int index_group = cursor.getColumnIndex(MySQLiteHelper.COLUMN_GROUP);
            int index_name = cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME);
            int index_marks = cursor.getColumnIndex(MySQLiteHelper.COLUMN_MARKS);
            int index_total = cursor.getColumnIndex(MySQLiteHelper.COLUMN_TOTAL);
            int index_date = cursor.getColumnIndex(MySQLiteHelper.COLUMN_DATE);
            results = new ArrayList<>();
            do {
                Result result = new Result();
                result.setSno(cursor.getLong(index_sno));
                result.setTaker_id(cursor.getLong(index_takerId));
                result.setCreator_id(cursor.getLong(index_creatorId));
                result.setGroup_no(cursor.getLong(index_group));
                result.setName(cursor.getString(index_name));
                result.setMarks(cursor.getInt(index_marks));
                result.setTotal(cursor.getInt(index_total));
                result.setDate(cursor.getString(index_date));
                results.add(result);
            } while (cursor.moveToNext());
            cursor.close();
        } else
            results = Collections.emptyList();
        return results;
    }

    public List<Friends> getFriendsList() {
        List<Friends> friendsList = new ArrayList<>();
        String[] columns = {
                MySQLiteHelper.COLUMN_USER_ID,
                MySQLiteHelper.COLUMN_NAME,
                MySQLiteHelper.COLUMN_MAIL,
                MySQLiteHelper.COLUMN_PHONE
        };

        Cursor cursor = database.query(getTableName(TABLE_FRIENDS), columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index_id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID);
            int index_name = cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME);
            int index_mail = cursor.getColumnIndex(MySQLiteHelper.COLUMN_MAIL);
            int index_phone = cursor.getColumnIndex(MySQLiteHelper.COLUMN_PHONE);
            do {
                Friends friends = new Friends();
                friends.setId(cursor.getLong(index_id));
                friends.setName(cursor.getString(index_name));
                friends.setMail(cursor.getString(index_mail));
                friends.setPhone(cursor.getString(index_phone));

                friendsList.add(friends);
            } while (cursor.moveToNext());
            cursor.close();
        } else
            friendsList = Collections.emptyList();
        return friendsList;
    }

    public List<Questions> getQuestions(long group) {
        List<Questions> data = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName(TABLE_QUESTIONS) + " WHERE "
                + MySQLiteHelper.COLUMN_GROUP + " = " + group;
        String[] columns = {
                MySQLiteHelper.COLUMN_SNO,
                MySQLiteHelper.COLUMN_QUESTION_ID,
                MySQLiteHelper.COLUMN_GROUP,
                MySQLiteHelper.COLUMN_QUESTION,
                MySQLiteHelper.COLUMN_OPTION_A,
                MySQLiteHelper.COLUMN_OPTION_B,
                MySQLiteHelper.COLUMN_OPTION_C,
                MySQLiteHelper.COLUMN_OPTION_D,
                MySQLiteHelper.COLUMN_ANSWER,
        };
        Cursor cursor = database.rawQuery(sql, null);
        /*Cursor cursor = database.query(getTableName(TABLE_QUESTIONS),
                columns,
                columns[2] + " = ?",
                new String[]{Long.toString(group)},
                null,
                null,
                null);
                */
        if (cursor != null && cursor.moveToFirst()) {
            int index_id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_QUESTION_ID);
            int index_group = cursor.getColumnIndex(MySQLiteHelper.COLUMN_GROUP);
            int index_question = cursor.getColumnIndex(MySQLiteHelper.COLUMN_QUESTION);
            int index_optionA = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_A);
            int index_optionB = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_B);
            int index_optionC = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_C);
            int index_optionD = cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_D);
            do {
                Questions questions = new Questions();
                questions.setId(cursor.getLong(index_id));
                questions.setGroup(cursor.getLong(index_group));
                questions.setQuestion(cursor.getString(index_question));
                questions.setOptionA(cursor.getString(index_optionA));
                questions.setOptionB(cursor.getString(index_optionB));
                questions.setOptionC(cursor.getString(index_optionC));
                questions.setOptionD(cursor.getString(index_optionD));

                data.add(questions);
            } while (cursor.moveToNext());
            cursor.close();
        } else
            data = Collections.emptyList();
        if (data.size() == 0)
            data = Collections.emptyList();
        return data;
    }

    public List<Info> getInfo() {
        List<Info> l = new ArrayList<>();
        String[] columns = {
                MySQLiteHelper.COLUMN_SNO,
                MySQLiteHelper.COLUMN_USER_ID,
                MySQLiteHelper.COLUMN_GROUP,
                MySQLiteHelper.COLUMN_DATE,
                MySQLiteHelper.COLUMN_NAME
        };

        Cursor cursor = database.query(getTableName(TABLE_INFO), null, null, null, null, null, columns[0] + " DESC ");
        if (cursor != null && cursor.moveToFirst()) {
            int index_sno = cursor.getColumnIndex(MySQLiteHelper.COLUMN_SNO);
            int index_id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID);
            int index_group = cursor.getColumnIndex(MySQLiteHelper.COLUMN_GROUP);
            int index_date = cursor.getColumnIndex(MySQLiteHelper.COLUMN_DATE);
            int index_name = cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME);
            do {
                Info info = new Info();
                info.setSno(cursor.getLong(index_sno));
                info.setUser_id(cursor.getLong(index_id));
                info.setGroup(cursor.getLong(index_group));
                info.setDate(cursor.getString(index_date));
                info.setName(cursor.getString(index_name));
                l.add(info);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            l = Collections.emptyList();
        }
        return l;
    }

    private String getTableName(int table) {
        String table_name = "";
        switch (table) {
            case TABLE_QUESTIONS:
                table_name = MySQLiteHelper.TABLE_QUESTIONS;
                break;
            case TABLE_FRIENDS:
                table_name = MySQLiteHelper.TABLE_FRIENDS;
                break;
            case TABLE_INFO:
                table_name = MySQLiteHelper.TABLE_INFO;
                break;
            case TABLE_TEMP:
                table_name = MySQLiteHelper.TABLE_TEMP;
                break;
            case TABLE_SUGGESTIONS:
                table_name = MySQLiteHelper.TABLE_SUGGESTIONS;
                break;
            case TABLE_RESULT:
                table_name = MySQLiteHelper.TABLE_RESULT;
                break;
        }
        return table_name;
    }

}
