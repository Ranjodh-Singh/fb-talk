package com.fbTalk;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.preference.PreferenceManager;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class QuizApplication extends Application implements
OnSharedPreferenceChangeListener {
	private static final String TAG = QuizApplication.class.getSimpleName();
	public static final String LOCATION_PROVIDER_NONE = "NONE";
	  public static final long INTERVAL_NEVER = 0;
	  /**
	   * quiz length decreased to 10 for testing purpose.
	   */
	  public static final long QUIZ_LENGTH = 10;
	  public static final long INITIAL_VALUE = 0;
	  private SharedPreferences prefs;
	  private QuizData quizData;
	  private Mongo m;
	  private String uriHost;
	  private Cursor cursor;
	  /**
	   * keeps the track of the current question being displayed i. e. question number.
	   */
	  private int questionIndex;
	  /**
	   * correct answer indexes are stored in a list , number of correct answers can be get by using counting the length of list.
	   */
	  private List<Long> correctQuestionsList;
	  /**
	   * wrong answer indexes are stored in a list , number of wrong answers can be get by using counting the length of list.
	   */
	  private List<Long> wrongQuestionsList;
	  /**
	   * skipped questions indexes are stored in a list , number of skipped questions can be get by using counting the length of list.
	   */
	  private  List<Long> skippedQuestionList;
	  /**
	   *  getter for fetching the list of correct answers of the questions.
	   */
	  public List<Long> getCorrectQuestionsList() {
		return correctQuestionsList;
	}
	  /**
	   *  getter for fetching the list of wrong answers of the questions.
	   */
	

	public List<Long> getWrongQuestionsList() {
		return wrongQuestionsList;
	}

	 /**
	   *  getter for fetching the list of skipped questions.
	   */

	public List<Long> getSkippedQuestionList() {
		return skippedQuestionList;
	}

	
	
	  public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	/**
	 * to increament the question number index;
	 */
	public void increamentQuestionIndex(){
		this.questionIndex++;
	}
	/**
	 * to decrease the question number index;
	 */
	public void decrementQuestionIndex(){
		this.questionIndex--;
	}
	/**
	 * to get the question Index currently being displayed.
	 * @return questionIndex as int.
	 */
	public int getQuestionIndex(){
		return this.questionIndex;
	}
	/**
	 * to the get the QuizData object which is responsible to do the databases related operations.
	 * @return QuizData
	 */
	 public QuizData getQuizData(){
		  return this.quizData;
	  }
	 /**
	  * to initialize the application Object and call certain methods.
	  */
	public void onCreate() {
		    super.onCreate();
		    this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		    this.prefs.registerOnSharedPreferenceChangeListener(this);
		    this.quizData = new QuizData(this);
		    correctQuestionsList = new ArrayList<Long>();
		    wrongQuestionsList = new ArrayList<Long>();
		    skippedQuestionList = new ArrayList<Long>();
		    Log.i(TAG, "Application started");
		  }
	

	  public synchronized int fetchQuizQuestions(){
		  List <QuizDBObject> quizDBObjectList = null;
		  int count=0;
		  long totalDocuments=0;
		   
		  Log.d(TAG, "Fetching Quiz Questions");
		  try {
			  
			  HttpGet mRequest = new HttpGet("https://api.mongolab.com/api/1/databases/rquiz/collections/question?apiKey=50385b5ae4b0f6b46150462a");    
			  Log.d(TAG, "Fetching Quiz Questions Request ::::"+mRequest);   
			  DefaultHttpClient client = new DefaultHttpClient();
			  //In case you need cookies, you can store them with PersistenCookieStorage
			  

			  try {
			      HttpResponse response = client.execute(mRequest);
			      Log.d(TAG, "Fetching Quiz Questions response ::::"+response);
			      InputStream source = response.getEntity().getContent();
			      Log.d(TAG, "Fetching Quiz Questions source ::::"+source);
			      Reader reader = new InputStreamReader(source);
			      
			      StringBuilder builder = new StringBuilder();
			        char[] buffer = new char[reader.toString().length()];
			        int read;
			        while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
			            builder.append(buffer, 0, read);
			        }
			        
			      Log.d(TAG, "Fetching Quiz Questions reader ::::"+builder.toString());
			      //GSON is one of the best alternatives for JSON parsing
			  Gson gson = new Gson();
			  Type typeOfCollectionOfQuizDBObject = new TypeToken<Collection<QuizDBObject>>(){}.getType();
			  quizDBObjectList = gson.fromJson(/*reader.toString()*/builder.toString(), typeOfCollectionOfQuizDBObject);
			  Log.d(TAG, "Fetching Quiz Questions user ::::"+quizDBObjectList);
			      //At this point you can do whatever you need with your parsed object.

			  } catch (IOException e) {
			  mRequest.abort();
			  }
			  
				/* m = new Mongo();
				 uriHost="mongodb://ds037447.mongolab.com:37447";
				 MongoURI uri= new MongoURI(uriHost);
				 m=uri.connect();
				 m.setWriteConcern(WriteConcern.SAFE);
				 DB db = m.getDB( QuizData.DATABASE );
				 Log.d(TAG, "getting the database :::"+db);
				 try {
					if(db.authenticate("ranjodh", "ranjodh".toCharArray())){
						Log.d(TAG, "authenticating the user for database") ;
					 }
					 else{
						 Log.d(TAG, "unable to authenticate the user ");
					 }
				} catch (Exception e) {
					Log.d(TAG, "Exception while authenticating the user");
					
				}
				 
				 BasicDBObject query = new BasicDBObject();
				 DBCollection coll =db.getCollection(QuizData.TABLE);
				 totalDocuments = coll.getCount();
				 
				 Log.d(TAG,"get the count of the collections "+totalDocuments);
				 if(totalDocuments < QUIZ_LENGTH){
					Log.d(TAG, "insufficient Data in the database");
					return 0;
				 }
				 query.put("i", new BasicDBObject("$gt", totalDocuments-QUIZ_LENGTH).append("$lte" ,totalDocuments));
				 
				 DBCursor cursor= coll.find(query);*/
				 ContentValues values = new ContentValues();
					try {
						for (QuizDBObject quizObject: quizDBObjectList ) {
							/*DBObject tableDBObject = cursor.next();*/	
							values.put(QuizData.C_INDEX,/*(String)tableDBObject.get(QuizData.C_INDEX)*/quizObject.getIndex());
							values.put(QuizData.C_QUESTTION,/*(String)tableDBObject.get(QuizData.C_QUESTTION)*/quizObject.getQuestion());
							values.put(QuizData.C_OPTIONA,/*(String)tableDBObject.get(QuizData.C_OPTIONA)*/quizObject.getOptionA());
							values.put(QuizData.C_OPTIONB,/*(String)tableDBObject.get(QuizData.C_OPTIONB)*/quizObject.getOptionB());
							values.put(QuizData.C_OPTIONC,/*(String)tableDBObject.get(QuizData.C_OPTIONC)*/quizObject.getOptionC());
							values.put(QuizData.C_OPTIOND,/*(String)tableDBObject.get(QuizData.C_OPTIOND)*/quizObject.getOptionD());
							values.put(QuizData.C_ANSWER,/*(String)tableDBObject.get(QuizData.C_ANSWER)*/quizObject.getAnswer());
							values.put(QuizData.C_CREATED_AT,/*((Date)tableDBObject.get(QuizData.C_CREATED_AT)).getTime()*/quizObject.getCreatedAt());
							values.put(QuizData.C_ACTIVE,/*(String)tableDBObject.get(QuizData.C_ACTIVE)*/quizObject.getIsActive());
							Log.d(TAG, "Got updates for " + quizObject + ". Saving");
							this.getQuizData().insertOrIgnore(values);
							Log.d(TAG, "cursor found values and count is : "+count);
							count++;
						}
					} finally {
		
						cursor.close();
					}
				 
				 Log.d(TAG, count > 0 ? "Got " + count + " status updates"
				          : "No new status updates");
				      return count;
		  }
		  catch (Exception e) {
				Toast.makeText(this,R.string.unknownHostMsg,Toast.LENGTH_LONG).show();
				Log.d(TAG, e.getMessage());
				return 0;
			}
	  }
	  
	 /* public List<QuizDBObject> getQuizDbObjectList(Cursor c ){
	    	List<QuizDBObject> quizDBObjectList = new ArrayList<QuizDBObject>();
	    	c.moveToFirst();
	    	for (c.moveToFirst();!c.isAfterLast();c.moveToNext() ) {
	    		QuizDBObject quizDBObject = new QuizDBObject();
	    		quizDBObject.setIndex(c.getLong(0));
	    		quizDBObject.setQuestion(c.getString(1));
	    		quizDBObject.setOptionA(c.getString(2));
	    		quizDBObject.setOptionB(c.getString(3));
	    		quizDBObject.setOptionC(c.getString(4));
	    		quizDBObject.setOptionD(c.getString(5));
	    		quizDBObject.setAnswer(c.getString(6));
	    		quizDBObject.setCreatedAt(c.getString(7));
	    		quizDBObject.setIsActive(c.getString(8));
	    		quizDBObjectList.add(quizDBObject);
			}
	    	
	    	
	    	return quizDBObjectList ;
	    }*/
	  public QuizDBObject getQuestionObjectByIndex(int index){
		  return this.quizData.getQuestionbyIndex(index);
	  }
	  
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		
	}
	 public void onTerminate() {
		    super.onTerminate();
		    this.quizData.close();
		    Log.i(TAG, "Application terminated");
		  }
}
