package com.fbTalk;






import java.net.UnknownHostException;
import java.sql.Date;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import android.util.Log;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.WriteConcern;

public class QuizApplication extends Application implements
OnSharedPreferenceChangeListener {
	private static final String TAG = QuizApplication.class.getSimpleName();
	public static final String LOCATION_PROVIDER_NONE = "NONE";
	  public static final long INTERVAL_NEVER = 0;
	  public static final long QUIZ_LENGTH = 30;
	  private SharedPreferences prefs;
	  private QuizData quizData;
	  private Mongo m;
	  private String uriHost;
	  public void onCreate() {
		    super.onCreate();
		    this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		    this.prefs.registerOnSharedPreferenceChangeListener(this);
		    this.quizData = new QuizData(this);
		    
		    Log.i(TAG, "Application started");
		  }
	
	  public synchronized void addQuestions(){
		  try {
				 m = new Mongo();
				 uriHost="mongodb://ds037447.mongolab.com:37447";
				 MongoURI uri= new MongoURI(uriHost);
				 m=uri.connect();
				 m.setWriteConcern(WriteConcern.SAFE);
				 DB db = m.getDB( QuizData.DATABASE );
				 db.authenticate("ranjodh", "ranjodh".toCharArray());
				
				 
				 BasicDBObject tableDBObject = new BasicDBObject();
				 DBCollection coll =db.createCollection(QuizData.TABLE, tableDBObject);
				 
				
				 
				 BasicDBObject doc = new BasicDBObject();
				 String dateString ="2012-09-16";
			        doc.put(QuizData.C_QUESTTION, "whats the unit of Current ?");
			        /*doc.put(QuizData.C_ID,1);*/
			        doc.put(QuizData.C_OPTIONA, "Ampier");
			        doc.put(QuizData.C_OPTIONB, "volt");
			        doc.put(QuizData.C_OPTIONC, "newton");
			        doc.put(QuizData.C_OPTIOND, "none");
			        doc.put(QuizData.C_ANSWER, "A");
			        doc.put(QuizData.C_CREATED_AT, Date.valueOf(dateString));
			        doc.put(QuizData.C_ACTIVE, 1);
			        

			        /*BasicDBObject info = new BasicDBObject();

			        info.put("x", 203);
			        info.put("y", 102);

			        doc.put("info", info);*/

			        coll.insert(doc);
			        coll.save(doc);
			} catch (UnknownHostException e) {
				
				Log.d(TAG, "unknown host exception");
			}
		  
		  
	  }
	  public QuizData getQuizData(){
		  return this.quizData;
	  }
	  public synchronized int fetchQuizQuestions(){
		  
		  int count=0;
		  long totalDocuments=0;
		   
		  Log.d(TAG, "Fetching Quiz Questions");
		  try {
				 m = new Mongo();
				 uriHost="mongodb://ds037447.mongolab.com:37447";
				 MongoURI uri= new MongoURI(uriHost);
				 m=uri.connect();
				 DB db = m.getDB( QuizData.DATABASE );
				 db.authenticate("ranjodh", "ranjodh".toCharArray());
				 
				 BasicDBObject query = new BasicDBObject();
				 DBCollection coll =db.getCollection(QuizData.TABLE);
				 totalDocuments = coll.getCount();
				 
				 
				 if(totalDocuments < 30){
					Log.d(TAG, "insufficient Data in the database");
					return 0;
				 }
				 query.put("i", new BasicDBObject("$gt", totalDocuments-30).append("$lte" ,totalDocuments));
				 
				 DBCursor cursor= coll.find(query);
				 ContentValues values = new ContentValues();
					try {
						while (cursor.hasNext()) {
							DBObject tableDBObject = cursor.next();	
							values.put(QuizData.C_QUESTTION,(String)tableDBObject.get(QuizData.C_QUESTTION));
							values.put(QuizData.C_OPTIONA,(String)tableDBObject.get(QuizData.C_OPTIONA));
							values.put(QuizData.C_OPTIONB,(String)tableDBObject.get(QuizData.C_OPTIONB));
							values.put(QuizData.C_OPTIONC,(String)tableDBObject.get(QuizData.C_OPTIONC));
							values.put(QuizData.C_OPTIOND,(String)tableDBObject.get(QuizData.C_OPTIOND));
							values.put(QuizData.C_ANSWER,(String)tableDBObject.get(QuizData.C_ANSWER));
							values.put(QuizData.C_CREATED_AT,((Date)tableDBObject.get(QuizData.C_CREATED_AT)).getTime());
							values.put(QuizData.C_ACTIVE,(Integer)tableDBObject.get(QuizData.C_ACTIVE));
							Log.d(TAG, "Got updates for " + tableDBObject + ". Saving");
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
		  catch (UnknownHostException e) {
				
				Log.d(TAG, "unknown host exception");
				return 0;
			}
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
