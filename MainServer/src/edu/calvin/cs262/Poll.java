package edu.calvin.cs262;

/**
 * Created by Kyle on 11/25/2016.
 */
public class Poll {

    private int id;
    private String diningHall, questionType, question, option1, option2, option3, option4;

    @SuppressWarnings("unused")
    Poll() { /* a default constructor, required by Gson */ }

    Poll(int id, String diningHall, String questionType, String question, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.diningHall = diningHall;
        this.questionType = questionType;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public int getId() {
        return id;
    }
    public String getDiningHall() {
        return diningHall;
    }
    public String getQuestionType() {return questionType;}
    public String getQuestion() {return question;}
    public String getOption1() {return option1;}
    public String getOption2() {return option2;}
    public String getOption3() {return option3;}
    public String getOption4() {return option4;}

    public void setId(int id) {
        this.id = id;
    }
    public void setDiningHall(String diningHall) {this.diningHall = diningHall;}
    public void setQuestionType(String questionType) {this.questionType = questionType;}
    public void setQuestion(String question) {this.question = question;}
    public void setOption1(String option1) {this.option1 = option1;}
    public void setOption2(String option2) {this.option2 = option2;}
    public void setOption3(String option3) {this.option3 = option3;}
    public void setOption4(String option4) {this.option4 = option4;}
}
