package com.trydevs.askyourfriends.askurfrnds.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sundareswaran on 06-06-2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_SUGGESTIONS = "suggestions";
    public static final String TABLE_FRIENDS = "friends";
    public static final String TABLE_INFO = "info";
    public static final String TABLE_TEMP = "temp";
    public static final String COLUMN_SNO = "sno";
    public static final String COLUMN_GROUP = "group_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION_A = "optionA";
    public static final String COLUMN_OPTION_B = "optionB";
    public static final String COLUMN_OPTION_C = "optionC";
    public static final String COLUMN_OPTION_D = "optionD";
    public static final String COLUMN_ANSWER = "answer";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_QUESTION_ID = "question_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_MAIL = "mail";
    private static final String DB_NAME = "AskUrFrnd_db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_TEMP = "CREATE TABLE " + TABLE_TEMP + " (" +
            COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_GROUP + " INTEGER, " +
            COLUMN_QUESTION + " TEXT, " +
            COLUMN_OPTION_A + " TEXT, " +
            COLUMN_OPTION_B + " TEXT, " +
            COLUMN_OPTION_C + " TEXT, " +
            COLUMN_OPTION_D + " TEXT, " +
            COLUMN_ANSWER + " TEXT" +
            ");";
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + " ( " +
            COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QUESTION_ID + " INTEGER, " +
            COLUMN_USER_ID + " INTEGER, " +
            COLUMN_GROUP + " INTEGER, " +
            COLUMN_QUESTION + " TEXT, " +
            COLUMN_OPTION_A + " TEXT, " +
            COLUMN_OPTION_B + " TEXT, " +
            COLUMN_OPTION_C + " TEXT, " +
            COLUMN_OPTION_D + " TEXT, " +
            COLUMN_ANSWER + " TEXT" +
            ");";
    private static final String CREATE_TABLE_SUGGESTIONS = "CREATE TABLE " + TABLE_SUGGESTIONS + " ( " +
            COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QUESTION + " TEXT, " +
            COLUMN_OPTION_A + " TEXT, " +
            COLUMN_OPTION_B + " TEXT, " +
            COLUMN_OPTION_C + " TEXT, " +
            COLUMN_OPTION_D + " TEXT, " +
            COLUMN_ANSWER + " TEXT" +
            ");";
    private static final String CREATE_TABLE_INFO = "CREATE TABLE " + TABLE_INFO + " (" +
            COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_ID + " INTEGER, " +
            COLUMN_GROUP + " INTEGER, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DATE + " TEXT" +
            ");";
    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE " + TABLE_FRIENDS + " (" +
            COLUMN_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_ID + " INTEGER, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MAIL + " TEXT, " +
            COLUMN_PHONE + " TEXT" +
            ");";
    private Context context;


    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("database ", "create");
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_FRIENDS);
            Log.d("Table created", TABLE_FRIENDS);
            sqLiteDatabase.execSQL(CREATE_TABLE_INFO);
            Log.d("Table created", TABLE_INFO);
            sqLiteDatabase.execSQL(CREATE_TABLE_QUESTIONS);
            Log.d("Table created", TABLE_QUESTIONS);
            sqLiteDatabase.execSQL(CREATE_TABLE_SUGGESTIONS);
            Log.d("Table created", TABLE_QUESTIONS);
            sqLiteDatabase.execSQL(CREATE_TABLE_TEMP);
            Log.d("Table created", TABLE_TEMP);
        } catch (SQLiteException exception) {
            Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
            Log.d("sql create exception", exception.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            Log.d("Database Helper", "upgrade table box office executed");
            sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_FRIENDS + " IF EXISTS;");
            sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_INFO + " IF EXISTS;");
            sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_QUESTIONS + " IF EXISTS;");
            sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_TEMP + " IF EXISTS;");
            onCreate(sqLiteDatabase);
        } catch (SQLiteException exception) {
            Toast.makeText(context, exception + "", Toast.LENGTH_SHORT).show();
        }
    }
}
