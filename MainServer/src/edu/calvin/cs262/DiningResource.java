package edu.calvin.cs262;

import com.google.gson.Gson;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * This module implements a RESTful service for the player table of the monopoly database.
 * Only the player relation is supported, not the game or playergame objects.
 * The server requires Java 1.7 (not 1.8).
 * <p>
 * I tested these services using IDEA's REST Client test tool. Run the server and open
 * Tools-TestRESTService and set the appropriate HTTP method, host/port, path and request body and then press
 * the green arrow (submit request).
 *
 * @author kvlinden
 * @version summer, 2016 - upgraded to JSON; added User POJO; removed unneeded libraries
 */
@Path("/dining")
public class DiningResource {

    /**
     * a hello-world resource
     *
     * @return a simple string value
     */
    @SuppressWarnings("SameReturnValue")
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello, Jersey!";
    }

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
            // As an example of GSON, we'll hard-code a couple players and return their JSON representation.
            List<User> hardCodedUsers = new ArrayList<>();
            hardCodedUsers.add(new User(1, "bob", "Password", 40));
            hardCodedUsers.add(new User(2, "bob2", "Password1234", 40));
            return new Gson().toJson(retrieveUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PUT
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

    private static final String DB_URI = "jdbc:postgresql://localhost:5432/Dining";
    private static final String DB_LOGIN_ID = "postgres";
    private static final String DB_PASSWORD = "password";

    private List retrieveUsers() throws Exception {
        List users = new ArrayList<>();
        //noinspection CaughtExceptionImmediatelyRethrown
        Class.forName("org.postgresql.Driver");
        //noinspection CaughtExceptionImmediatelyRethrown
        try (Connection connection = DriverManager.getConnection(DB_URI, DB_LOGIN_ID, DB_PASSWORD); Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("SELECT * FROM Person")) {
            while (rs.next()) {
                //noinspection unchecked
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



    /**
     * Run this main method to fire up the service.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running...");
        System.out.println("Web clients should visit: http://localhost:9998/monopoly");
        System.out.println("Android emulators should visit: http://LOCAL_IP_ADDRESS:9998/monopoly");
        System.out.println("Hit return to stop...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.out.println("Stopping server...");
        server.stop(0);
        System.out.println("Server stopped...");
    }
}
