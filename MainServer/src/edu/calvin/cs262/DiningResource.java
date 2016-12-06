package edu.calvin.cs262;

import com.google.gson.Gson;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 Server created By Kyle Harkema 
 For TeamFly Dining hall project for cs262 at Calvin College
 Used to get access and modify data in dining hall database
 Contains records for users, polls, and poll responses
 Last Updated 11-28-16
 **/
@Path("/Dining")
public class DiningResource {

    /**
     * GET method that returns a list of all users
     *
     * @return a JSON list representation of the user records
     */
    @GET
    @Path("/users")
    @Produces("application/json")
    public String getUsers() {
        try {
            return new Gson().toJson(retrieveUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST method that creates a new user
     *
     * @return a JSON of the added user
     */
    @POST
    @Path("/users")
    @Consumes("application/json")
    @Produces("application/json")
    public String postUser(String playerLine) {
        try {
            User user = new Gson().fromJson(playerLine, User.class);
            return new Gson().toJson(addUser(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns user with given ID
     *
     * @return a JSON of the user
     */
    @GET
    @Path("/users/{id}")
    @Produces("application/json")
    public String getUser(@PathParam("id") int id) {
        try {
            return new Gson().toJson(retrieveUser(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns user with given username
     *
     * @return a JSON of the user
     */
    @GET
    @Path("/user/{name}")
    @Produces("application/json")
    public String getName(@PathParam("name") String name) {
        try {
            return new Gson().toJson(retrieveName(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * DELETE method that deletes the user with the given ID
     *
     * @return a JSON of the deleted user
     */
    @DELETE
    @Path("/users/{id}")
    @Produces("application/json")
    public String deleteUser(@PathParam("id") int id) {
        try {
            User x = new User(id, "deleted", "deleted", 0);
            User y = deleteUser(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a given users meals
     *
     * @returns an integer value of the meal
     */
    @GET
    @Path("/users/{id}/meals")
    @Produces("application/json")
    public String getMeals(@PathParam("id") int id) {
        try {
            return new Gson().toJson(retrieveMeals(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * PUT method that modifys the users number of meals
     *
     * @returns the changed integer
     */
    @PUT
    @Path("/users/{id}/meals")
    @Produces("application/json")
    public String updateMeals(@PathParam("id") int id, String mealNum) {
        try {
            int meals = Integer.parseInt(mealNum);
            return new Gson().toJson(updateMeal(id, meals));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a list of all polls
     *
     * @return a JSON list representation of the polls
     */
    @GET
    @Path("/polls")
    @Produces("application/json")
    public String getPolls(){
        try {
            return new Gson().toJson(retrievePolls());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST method that adds a new poll
     *
     * @return a JSON oof the added poll
     */
    @POST
    @Path("/polls")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String postPoll(String pollLine){
        System.out.println(pollLine);           //This is a stupid hack to convert html form response to json
        CharSequence s1 = "=";                  //While it is stupid it also works so I'm keeping it this way
        CharSequence s2 = "\":\"";              //It can easily be broken but I don't think a dining hall worker would ever put an = + or & in these polls
        pollLine = pollLine.replace(s1, s2);
        CharSequence s3 = "&";
        CharSequence s4 = "\",\"";
        pollLine = pollLine.replace(s3, s4);
        pollLine = "{\"" + pollLine + "\"}";
        CharSequence s5 = "\"0\"";
        CharSequence s6 = "0";
        pollLine = pollLine.replace(s5,s6);
        pollLine = pollLine.replace("+", " ");
        pollLine = pollLine.replace("%3F", "?");
        System.out.println(pollLine);
        try {
            Poll poll = new Gson().fromJson(pollLine, Poll.class);
            return new Gson().toJson(addPoll(poll));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DELETE method that deletes the poll with the given id
     *
     * @return a JSON of the deleted poll
     */
    @DELETE
    @Path("/polls/{id}")
    @Produces("application/json")
    public String deletePoll(@PathParam("id") int id) {
        try {
            Poll x = new Poll(id, "deleted", "deleted", "deleted","deleted","deleted","deleted","deleted");
            Poll y = deletePoll(x);
            return new Gson().toJson(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a list of all commons polls
     *
     * @return a JSON list representation of the commons polls
     */
    @GET
    @Path("/polls/commons")
    @Produces("application/json")
    public String getCommonsPolls(){
        try {
            return new Gson().toJson(retrieveCommonsPolls());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a list of the newest poll from commons
     *
     * @return a JSON of the newest commons poll
     */
    @GET
    @Path("/polls/commons/new")
    @Produces("application/json")
    public String getNewCommonsPolls(){
        try {
            return new Gson().toJson(retrieveNewCommonsPolls());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a list of all knollcrest polls
     *
     * @return a JSON list representation of the knollcrest polls
     */
    @GET
    @Path("/polls/knollcrest")
    @Produces("application/json")
    public String getKnollcrestPolls(){
        try {
            return new Gson().toJson(retrieveKnollcrestPolls());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a list of the newest poll from knollcrest
     *
     * @return a JSON of the newest knollcrest poll
     */
    @GET
    @Path("/polls/knollcrest/new")
    @Produces("application/json")
    public String getNewKnollcrestPolls(){
        try {
            return new Gson().toJson(retrieveNewKnollcrestPolls());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a JSON list of responses
     *
     * @return a JSON list of responses
     */
    @GET
    @Path("/responses")
    @Produces("application/json")
    public String getResponses(){
        try {
            return new Gson().toJson(retrieveResponses());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST method that adds a new response
     *
     * @return a JSON of new response
     */
    @POST
    @Path("/responses")
    @Consumes("application/json")
    @Produces("application/json")
    public String postResponse(String responseLine){
        try {
            Response response = new Gson().fromJson(responseLine, Response.class);
            return new Gson().toJson(addResponse(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a JSON list of responses for a given pollID
     *
     * @return a JSON list of responses
     */
    @GET
    @Path("/responses/{id}")
    @Produces("application/json")
    public String getPollResponses(@PathParam("id") int id){
        try {
            return new Gson().toJson(retrievePollResponses(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a JSON list of responses for a given pollID
     *
     * @return a JSON list of responses
     */
    @GET
    @Path("/responses/{id}/stats")
    @Produces("application/json")
    public String getPollStats(@PathParam("id") int id){
        try {
            return new Gson().toJson(retrievePollStats(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET method that returns a HTML form to submit a new poll
     *
     * @return a HTML Form
     * This is a stupid hack but it works
     */
    @GET
    @Path("/pollForm")
    @Produces({MediaType.TEXT_HTML})
    public String viewHome()
    {
        return "<html> <form action=\"http://localhost:8086/Dining/polls\" method=\"POST\">\n" +
                " <input type=\"hidden\" name=\"id\" value=0>\n" +
                "  Dining Hall:<br>\n" +
                " <input type=\"radio\" name=\"diningHall\" value=\"Commons\" checked> Commons<br>\n" +
                " <input type=\"radio\" name=\"diningHall\" value=\"Knollcrest\"> Knollcrest<br>\n" +
                " Question Type:<br>\n" +
                " <input type=\"radio\" name=\"questionType\" value=\"multipleChoice\" checked> Multiple choice<br>\n" +
                " <input type=\"radio\" name=\"questionType\" value=\"trueFalse\"> yes no<br>\n" +
                " Question:<br>\n" +
                " <input type=\"text\" name=\"question\" value=\"\"><br>\n" +
                " Option1:<br>\n" +
                " <input type=\"text\" name=\"option1\" value=\"\"><br>\n" +
                "  Option2:<br>\n" +
                " <input type=\"text\" name=\"option2\" value=\"\"><br>\n" +
                "  Option3:<br>\n" +
                " <input type=\"text\" name=\"option3\" value=\"\"><br>\n" +
                "  Option4:<br>\n" +
                " <input type=\"text\" name=\"option4\" value=\"\"><br>\n" +
                " Note leave option 3 and 4 blank if using a yes no question\n" +
                " <input type=\"submit\" value=\"Submit\">\n" +
                "</form> </html>";
    }

    private static final String DB_URI = "jdbc:postgresql://localhost:5432/Dining";
    private static final String DB_LOGIN_ID = "postgres";
    private static final String DB_PASSWORD = "password";
	private static final String PORT = "8086";

    private List retrieveUsers() throws Exception {
        List users = new ArrayList<>();
        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Person")) {
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return users;
    }

    private User addUser (User user) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        boolean newUser = true;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT username FROM Person");
            while (rs.next()) {
                if (user.getUserName().equals(rs.getString(1))) {
                    newUser = false;
                    break;
                }
            }
            if (newUser == true) {
                rs = statement.executeQuery("SELECT MAX(ID) FROM Person");
                if (rs.next()) {
                    user.setId(rs.getInt(1) + 1);
                } else {
                    throw new RuntimeException("failed to find unique id...");
                }
                statement.executeUpdate("INSERT INTO Person VALUES (" + user.getId() + ", '" + user.getUserName() + "', '" + user.getPassword() + "', " + user.getMealCount() + ")");
            }
            } catch(SQLException e){
                throw (e);
            } finally{
                rs.close();
                statement.close();
                connection.close();
            }
            return user;
    }

    private User retrieveUser(int id) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Person WHERE ID=" + id);
            if (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return user;
    }

    private User retrieveName(String name) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Person WHERE username='"+name+"'");
            if (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return user;
    }

    public User deleteUser(User user) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Person WHERE id=" + user.getId());
        } catch (SQLException e) {
            throw (e);
        } finally {
            statement.close();
            connection.close();
        }
        return user;
    }

    private int retrieveMeals(int id) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int meals = 0;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT meals FROM Person WHERE ID=" + id);
            if (rs.next()) {
                meals =  rs.getInt(1);
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return meals;
    }

    private int updateMeal (int id, int meals) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT meals FROM Person WHERE ID=" + id);
            if (rs.next()) {
                statement.executeUpdate("UPDATE Person Set meals =" + meals + "WHERE ID=" + id);
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return meals;
    }

    private List retrievePolls() throws Exception {
        List polls = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Poll")) {
            while (rs.next()) {
                //noinspection unchecked
                polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return polls;
    }

    private Poll addPoll(Poll poll) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT MAX(pollID) FROM Poll");
            if (rs.next()) {
                poll.setId(rs.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique id...");
            }
            statement.executeUpdate("INSERT INTO Poll VALUES (" + poll.getId() + ", '" + poll.getDiningHall() + "', '" + poll.getQuestionType() + "', " +
                    "'" + poll.getQuestion() + "', '" + poll.getOption1() + "', '" + poll.getOption2() + "', '" + poll.getOption3() + "','" + poll.getOption4() + "')");
        } catch(SQLException e){
            throw (e);
        } finally{
            rs.close();
            statement.close();
            connection.close();
        }
        return poll;
    }

    public Poll deletePoll(Poll poll) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Poll WHERE pollID=" + poll.getId());
        } catch (SQLException e) {
            throw (e);
        } finally {
            statement.close();
            connection.close();
        }
        return poll;
    }

    private List retrieveCommonsPolls() throws Exception {
        List polls = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Poll WHERE diningHall = 'Commons' ")) {
            while (rs.next()) {
                //noinspection unchecked
                polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return polls;
    }

    private List retrieveNewCommonsPolls() throws Exception {
        List polls = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Poll WHERE diningHall = 'Commons' ORDER BY pollID DESC LIMIT 1 ")) {
            while (rs.next()) {
                //noinspection unchecked
                polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return polls;
    }

    private List retrieveKnollcrestPolls() throws Exception {
        List polls = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Poll WHERE diningHall = 'Knollcrest' ")) {
            while (rs.next()) {
                polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return polls;
    }

    private List retrieveNewKnollcrestPolls() throws Exception {
        List polls = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Poll WHERE diningHall = 'Knollcrest' ORDER BY pollID DESC LIMIT 1  ")) {
            while (rs.next()) {
                //noinspection unchecked
                polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return polls;
    }

    private List retrieveResponses() throws Exception {
        List responses = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Response")) {
            while (rs.next()) {
                responses.add(new Response(rs.getInt(1), rs.getInt(2), rs.getBoolean(3), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return responses;
    }

    private Response addResponse(Response response) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Response VALUES (" + response.getPollID() + ", '" + response.getPersonID() + "', '" + response.getAnswer1() + "', " +
                    "'" + response.getAnswer2() + "', '" + response.getAnswer3() + "', " + response.getAnswer4() + ")");
        } catch(SQLException e){
            throw (e);
        } finally{
            statement.close();
            connection.close();
        }
        return response;
    }

    private List retrievePollResponses(int id) throws Exception {
        List responses = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Response WHERE pollID =" + id)) {
            while (rs.next()) {
                responses.add(new Response(rs.getInt(1), rs.getInt(2), rs.getBoolean(3), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6)));
            }
        } catch (SQLException e) {
            throw (e);
        }
        return responses;
    }

    private List retrievePollStats(int id) throws Exception {
        List stats = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        int total = 0;
        float option1 = 0;
        float option2 = 0;
        float option3 = 0;
        float option4 = 0;
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Response WHERE pollID =" + id)) {
            while (rs.next()) {
                Response current = new Response(rs.getInt(1), rs.getInt(2), rs.getBoolean(3), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6));
                total += 1;
                if (current.getAnswer1()){
                    option1 += 1;
                }
                if (current.getAnswer2()){
                    option2 += 1;
                }
                if (current.getAnswer3()){
                    option3 += 1;
                }
                if (current.getAnswer4()){
                    option4 += 1;
                }
            }
        } catch (SQLException e) {
            throw (e);
        }
        option1 = (option1/total) * 100;
        option2 = (option2/total) * 100;
        option3 = (option3/total) * 100;
        option4 = (option4/total) * 100;
        stats.add(option1);
        stats.add(option2);
        stats.add(option3);
        stats.add(option4);

        return stats;
    }


    /**
     * Run this main method to fire up the service.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:" + PORT + "/");
        server.start();
        File here = new File(".");
        System.out.println(here.getAbsolutePath());
        System.out.println("Server running...");
        System.out.println("Web clients should visit: http://localhost:8086/dining");
        System.out.println("Android emulators should visit: http://LOCAL_IP_ADDRESS:8086/dining");
        System.out.println("Hit return to stop...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.out.println("Stopping server...");
        server.stop(0);
        System.out.println("Server stopped...");
    }


}
