package com.fbTalk;


public class QuizDBObject {

	/**
	 * this class will work as the place holder for the quiz object.
	 * An extra variable is added to store answer given by user.
	 */
	private static final long serialVersionUID = 1L;
	private String question;
	private String answer;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String createdAt;
	private String isActive;
	/**
	 * for storing the answer given by the user for every question.
	 */
	private String userAnswer;
	
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public QuizDBObject(){
		super();
	}
	private long index ;
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
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
	
}
