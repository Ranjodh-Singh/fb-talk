package com.fbTalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizData {

	
	 private static final String TAG = QuizData.class.getSimpleName();

	  static final int VERSION = 1;
	  /**
	   * name of the database and the table with question and related data.
	   */
	  static final String DATABASE = "rquiz.db";
	  static final String TABLE = "question";

	  public static final String C_ID = "_id";
	  public static final String C_INDEX="index";
	  public static final String C_QUESTTION = "question";
	  public static final String C_OPTIONA = "optiona";
	  public static final String C_OPTIONB = "optionb";
	  public static final String C_OPTIONC = "optionc";
	  public static final String C_OPTIOND = "optiond";
	  public static final String C_ANSWER = "answer";
	  /**
	   * date at which question was created.
	   */
	  public static final String C_CREATED_AT = "created_at";
	  /**
	   * is active 1 if the question added by the user is approved by the administrator.
	   * else 0 for pending approval.
	   */
	  public static final String C_ACTIVE = "Active";
	  
	  
	  //public static final String C_USER = "user";

	  private static final String GET_ALL_ORDER_BY = C_CREATED_AT + " DESC";

	  private static final String[] MAX_CREATED_AT_COLUMNS = { "max("
	      + QuizData.C_CREATED_AT + ")" };

	  private static final String[] DB_TEXT_COLUMNS = {C_INDEX, C_QUESTTION,C_OPTIONA, C_OPTIONB ,C_OPTIONC, C_OPTIOND,C_ANSWER ,C_ACTIVE };

	  // DbHelper implementations
	  class DbHelper extends SQLiteOpenHelper {

	    public DbHelper(Context context) {
	      super(context, DATABASE, null, VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	      Log.i(TAG, "Creating database: " + DATABASE);
	      db = dbHelper.getWritableDatabase();
	      db.execSQL("create table " + TABLE + " (" + C_ID + " int primary key, "
	          + C_CREATED_AT + " int, " + C_INDEX +" int , "+ C_QUESTTION + " text, " + C_OPTIONA + " text, "+ C_OPTIONB+" text,"+C_OPTIONC +" text,"+ C_OPTIOND+" text,"+C_ANSWER +" text,"+ C_ACTIVE+" text)" );
	      
	 
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      db.execSQL("drop table " + TABLE);
	      this.onCreate(db);
	    }
	  }

	  final DbHelper dbHelper;

	  public QuizData(Context context) {
	    this.dbHelper = new DbHelper(context);
	    Log.i(TAG, "Initialized data");
	  }

	  public void close() {
	    this.dbHelper.close();
	  }

	  public void insertOrIgnore(ContentValues values) {
	    Log.d(TAG, "insertOrIgnore on " + values);
	    SQLiteDatabase db = this.dbHelper.getWritableDatabase();
	    try {
	      db.insertWithOnConflict(TABLE, null, values,
	          SQLiteDatabase.CONFLICT_IGNORE);
	    } finally {
	      db.close();
	    }
	  }

	  /**
	   * 
	   * @return Cursor where the columns are going to be id, created_at, user, txt
	   */
	  public Cursor getALLQuestionUpdatesOrderByCreatedAT() {
	    SQLiteDatabase db = this.dbHelper.getReadableDatabase();
	    return db.query(TABLE, null, null, null, null, null, GET_ALL_ORDER_BY);
	  }

	  public long getLatestQuestionCreatedAtTime() {
	    SQLiteDatabase db = this.dbHelper.getReadableDatabase();
	    try {
	      Cursor cursor = db.query(TABLE, MAX_CREATED_AT_COLUMNS, null, null, null,
	          null, null);
	      try {
	        return cursor.moveToNext() ? cursor.getLong(0) : Long.MIN_VALUE;
	      } finally {
	        cursor.close();
	      }
	    } finally {
	      db.close();
	    }
	  }

	  public QuizDBObject getQuestionbyIndex(long index) {
	    SQLiteDatabase db = this.dbHelper.getReadableDatabase();
	    QuizDBObject quizDBObject = new QuizDBObject();
	    try {
	      Cursor cursor = db.query(TABLE, DB_TEXT_COLUMNS, C_INDEX + "=" + index, null,
	          null, null, null);
	      try {
	        if(cursor.moveToNext()){
	        	quizDBObject.setIndex(cursor.getLong(cursor.getColumnIndex(C_INDEX)));
	    		quizDBObject.setQuestion(cursor.getString(cursor.getColumnIndex(C_QUESTTION)));
	    		quizDBObject.setOptionA(cursor.getString(cursor.getColumnIndex(C_OPTIONA)));
	    		quizDBObject.setOptionB(cursor.getString(cursor.getColumnIndex(C_OPTIONB)));
	    		quizDBObject.setOptionC(cursor.getString(cursor.getColumnIndex(C_OPTIONC)));
	    		quizDBObject.setOptionD(cursor.getString(cursor.getColumnIndex(C_OPTIOND)));
	    		quizDBObject.setAnswer(cursor.getString(cursor.getColumnIndex(C_ANSWER)));
	    		quizDBObject.setCreatedAt(cursor.getString(cursor.getColumnIndex(C_CREATED_AT)));
	    		quizDBObject.setIsActive(cursor.getString(cursor.getColumnIndex(C_ACTIVE)));
	    		return quizDBObject;
	        }
	        else return null;
	      } finally {
	        cursor.close();
	      }
	    } finally {
	      db.close();
	    }
	  }
	  
	  /**
	   * Deletes ALL the data
	   */
	  public void delete() {
	    // Open Database
	    SQLiteDatabase db = dbHelper.getWritableDatabase();

	    // Delete the data
	    db.delete(TABLE, null, null);

	    // Close Database
	    db.close();
	  }



	
	
	
}
