package edu.calvin.cs262;

/**
 * A User class (POJO) for the player relation
 *
 * @author kvlinden
 * @version summer, 2016
 */
class User {

    private int id, mealCount;
    private String userName, password;

    @SuppressWarnings("unused")
    User() { /* a default constructor, required by Gson */ }

    User(int id, String userName, String password, int mealCount) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.mealCount = mealCount;
    }

    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getUserName() {
        return userName;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    public int getMealCount() { return mealCount;}

    @SuppressWarnings("unused")
    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMealCount(int mealCount) { this.mealCount = mealCount;}
}
