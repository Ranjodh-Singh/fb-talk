package com.fbTalk;
import com.google.gson.annotations.SerializedName;
import java.sql.Date;



public class QuizDBObject {

	/**
	 * this class will work as the place holder for the quiz object.
	 * An extra variable is added to store answer given by user.
	 */
	@SerializedName("_id")
	 Id _id;

	
       
	public Id get_id() {
		return _id;
	}
	public void set_id(Id _id) {
		this._id = _id;
	}
	@SerializedName("index")

	private long index ;
	@SerializedName("question")

	private String question;
	@SerializedName("answer")

	private String answer;
	@SerializedName("optiona")

	private String optiona;
	@SerializedName("optionb")

	private String optionb;
	@SerializedName("optionc")

	private String optionc;
	@SerializedName("optiond")

	private String optiond;
	@SerializedName("created_at") 
	MyDate created_at;
	
public MyDate getCreated_at() {
		return created_at;
	}
	public void setCreated_at(MyDate created_at) {
		this.created_at = created_at;
	}
	/*	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}*/
	public void setOptiona(String optiona) {
		this.optiona = optiona;
	}
	@SerializedName("Active")
	private String Active;
	/**
	 * for storing the answer given by the user for every question.
	 */
	private String userAnswer;
	
	/*public Object get_id() {
		return _id;
	}
	public void set_id(Object _id) {
		this._id = _id;
	}*/
	
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public QuizDBObject(){
		super();
	}
	
	public long getIndex() {
		return index;
	}
	public void setIndex(long index) {
		this.index = index;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getOptiona() {
		return optiona;
	}
	public void setOptionA(String optiona) {
		this.optiona = optiona;
	}
	public String getOptionb() {
		return optionb;
	}
	public void setOptionb(String optionb) {
		this.optionb = optionb;
	}
	public String getOptionc() {
		return optionc;
	}
	public void setOptionc(String optionc) {
		this.optionc = optionc;
	}
	public String getOptiond() {
		return optiond;
	}
	public void setOptiond(String optiond) {
		this.optiond = optiond;
	}
	
	public String getActive() {
		return Active;
	}
	public void setActive(String Active) {
		this.Active = Active;
	}
	/*public void setCreatedAtString(String type) {
		this.created_at.parse(type);
		
	}
	*/
	
	static class Id{
		String $id;

		public String get$id() {
			return $id;
		}

		public void set$id(String $id) {
			this.$id = $id;
		}
		
	}
	static class MyDate{
		String $date;

		public String get$date() {
			return $date;
		}

		public void set$date(String $date) {
			this.$date = $date;
		}
	}
	
}
