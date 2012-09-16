package com.fbTalk;






import java.net.UnknownHostException;
import java.sql.Date;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import android.util.Log;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.WriteConcern;

public class QuizApplication extends Application implements
OnSharedPreferenceChangeListener {
	private static final String TAG = QuizApplication.class.getSimpleName();
	public static final String LOCATION_PROVIDER_NONE = "NONE";
	  public static final long INTERVAL_NEVER = 0;
	  private SharedPreferences prefs;
	  private QuizData quizData;
	  Mongo m;
	  String uriHost;
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
	  private QuizData getQuizData(){
		  return this.quizData;
	  }
	  
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		
	}

}
