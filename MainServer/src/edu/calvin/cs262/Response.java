package edu.calvin.cs262;

/**
 * Created by Kyle on 11/27/2016.
 */
public class Response {

    private int pollID, personID;
    private Boolean answer1, answer2, answer3, answer4;

    @SuppressWarnings("unused")
    Response() { /* a default constructor, required by Gson */ }

    Response(int pollID, int personID, Boolean answer1, Boolean answer2, Boolean answer3, Boolean answer4) {
        this.pollID = pollID;
        this.personID = personID;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public int getPollID() {
        return pollID;
    }
    public int getPersonID() { return personID; }
    public Boolean getAnswer1() {return answer1;}
    public Boolean getAnswer2() {return answer2;}
    public Boolean getAnswer3() {return answer3;}
    public Boolean getAnswer4() {return answer4;}

    public void setPollID(int id) {
        this.pollID = id;
    }
    public void setPersonID(int id) { this.personID = id;}
    public void setAnswer1(Boolean answer1) {this.answer1 = answer1;}
    public void setAnswer2(Boolean answer2) {this.answer2 = answer2;}
    public void setAnswer3(Boolean answer3) {this.answer3 = answer3;}
    public void setAnswer4(Boolean answer4) {this.answer4 = answer4;}
}
