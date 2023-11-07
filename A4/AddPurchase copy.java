import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
import pgpass.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class AddPurchase(String[] args){

    private Connection conDB; // Connection to the database system.
    private String url; // URL: Which database?
    private String user = "vaidya28"; // Database user account

    private Integer custID; // customer ID of customer who makes the purchase
    private String club; // club the customer is a part of
    private String title; // title of book burchased
    private Integer year; // year of publishing
    private String whenp; // time of purchase
    private Integer qnty = 1; // number of copies purchased
    private String currentTime;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    LocalDateTime now = LocalDateTime.now(); 
    currentTime = (dtf.format(now)).toString();
    whenp = currentTime;

    // Constructor
    public AddPurchase(String[] args) {
        // Set up the DB connection.
        try {
            // Register the driver with DriverManager.
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // URL: Which database?
        // url = "jdbc:postgresql://db:5432/<dbname>?currentSchema=yrb";
        url = "jdbc:postgresql://db:5432/vaidya28";

        // set up acct info
        // fetch the PASSWD from <.pgpass>
        Properties props = new Properties();
        try {
            String passwd = PgPass.get("db", "*", user, user);
            props.setProperty("user", "godfrey");
            props.setProperty("password", passwd);
            // props.setProperty("ssl","true"); // NOT SUPPORTED on DB
        } catch (PgPassException e) {
            System.out.print("\nCould not obtain PASSWD from <.pgpass>.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Initialize the connection.
        try {
            // Connect with a fall-thru id & password
            // conDB = DriverManager.getConnection(url,"<username>","<password>");
            conDB = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Let's have autocommit turned off. No particular reason here.
        try {
            conDB.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        }

        // Customer
        if (/*args.length != 14 ||*/ args[0].equal("-c") != true) {
            // Don't know what's wanted. Bail.
            System.out.println("\nUsage: java AddPurchase cust#");
            System.exit(0);
        } else {
            try {
            	custID = (parseInt(args[1]));
                //custID = new Integer(parseInt(args[1]));
            } catch (NumberFormatException e) {
                System.out.println("\nUsage: java AddPurchase cust#");
                System.out.println("Provide an INT for the cust#.");
                System.exit(0);
            }
        }

        // Does customer with this ID exist?
        if (!customerCheck()) {
            System.out.print("There is no customer #");
            System.out.print(custID);
            System.out.println(" in the database.");
            System.out.println("Bye.");
            System.exit(0);
        }
        
        //Club
        if(args[2].equals("-b") != true) {
        	// Don't know what's wanted. Bail.
            System.out.println("\nUsage: java AddPurchase clubName");
            System.exit(0);
        } else {
            try {
            	club = args[3];
                //custID = new Integer(parseInt(args[1]));
            } catch (NumberFormatException e) {
                System.out.println("\nUsage: java AddPurchase club name--");
                System.out.println("Provide an STRING for the club name.");
                System.exit(0);
            }
        }
        
     // Does this club exist in data base?
        if (!clubCheck()) {
            System.out.print("There is no club of the name ");
            System.out.print(club);
            System.out.println(" in the database.");
            System.out.println("Bye.");
            System.exit(0);
        }
        
        
      //Book
        if(args[4].equals("-y") != true || args[6].equals("-t") != true) {
        	// Don't know what's wanted. Bail.
            System.out.println("\nUsage: java AddPurchase Book title and year");
            System.exit(0);
        } else {
            try {
            	title = args[7];
                year = (parseInt(args[5]));
            } catch (NumberFormatException e) {
                System.out.println("\nUsage: java AddPurchase book--");
                System.out.println("Provide an STRING for the title and INT for year.");
                System.exit(0);
            }
        }
        
     // Does this book exist in data base?
        if (!bookCheck()) {
            System.out.print("There is no book with title ");
            System.out.print(title);
            System.out.print(" and year ");
            System.out.print(year);
            System.out.println(" in the database.");
            System.out.println("Bye.");
            System.exit(0);
        }
        
        
        //Member
        if (!memberCheck()) {
            System.out.print("The customer with ID#");
            System.out.print(custID);
            System.out.print(" does not belong in club ");
            System.out.print(club);
            System.out.println(".");
            System.out.println("Bye.");
            System.exit(0);
        }
        
        
        //Offer
        if (!offerCheck()) {
            System.out.print("The club ");
            System.out.print(club);
            System.out.print(" does not offer the book ");
            System.out.print(title);
            System.out.println(" published in year ");
            System.out.print(year + ".");
            System.out.println("Bye.");
            System.exit(0);
        }
        
        
        insertPurchase(custID, club, title, year, whenp, qnty);
        

        // Commit. Okay, here nothing to commit really, but why not...
        try {
            conDB.commit();
        } catch (SQLException e) {
            System.out.print("\nFailed trying to commit.\n");
            e.printStackTrace();
            System.exit(0);
        }
        // Close the connection.
        try {
            conDB.close();
        } catch (SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        }

    }

    
    //SQL#1 - Checks if customer exists
    public boolean customerCheck() {
        String queryText = ""; // The SQL text.
        PreparedStatement querySt = null; // The query handle.
        ResultSet answers = null; // A cursor.

        boolean inDB = false; // Return.

        queryText = "SELECT cid " + "FROM yrb_customer " + "WHERE cid = ? ";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch (SQLException e) {
            System.out.println("SQL#1 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            querySt.setInt(1, custID.intValue());
            answers = querySt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL#1 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
                //custName = answers.getString("name");
            } else {
                inDB = false;
                //custName = null;
            }
        } catch (SQLException e) {
            System.out.println("SQL#1 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch (SQLException e) {
            System.out.print("SQL#1 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch (SQLException e) {
            System.out.print("SQL#1 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }
    
    
    //SQL#2 - Checks if club exists
    public boolean clubCheck() {
        String queryText = ""; // The SQL text.
        PreparedStatement querySt = null; // The query handle.
        ResultSet answers = null; // A cursor.

        boolean inDB = false; // Return.

        queryText = "SELECT club" + "FROM yrb_club " + "WHERE club = ? ";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch (SQLException e) {
            System.out.println("SQL#2 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
            querySt.setInt(1, club.toString());
            answers = querySt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL#2 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
            } else {
                inDB = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL#2 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch (SQLException e) {
            System.out.print("SQL#2 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch (SQLException e) {
            System.out.print("SQL#2 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }
    
    
    //SQL#3 - Checks if book exists
    public boolean bookCheck() {
        String queryText = ""; // The SQL text.
        PreparedStatement querySt = null; // The query handle.
        ResultSet answers = null; // A cursor.

        boolean inDB = false; // Return.

        queryText = "SELECT title, year " + " FROM yrb_book " + " WHERE title = ? AND year = ? ";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch (SQLException e) {
            System.out.println("SQL#3 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
        	querySt.setInt(1, title.toString());
        	querySt.setInt(2, year.intValue());
            answers = querySt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL#3 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
            } else {
                inDB = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL#3 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch (SQLException e) {
            System.out.print("SQL#3 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch (SQLException e) {
            System.out.print("SQL#3 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }
    
    
    //SQL#4 - Checks if the cid belogs in club
    public boolean memberCheck() {
        String queryText = ""; // The SQL text.
        PreparedStatement querySt = null; // The query handle.
        ResultSet answers = null; // A cursor.

        boolean inDB = false; // Return.

        queryText = "SELECT cid " + "FROM yrb_member " + "WHERE club = ? AND cid = ? ";
        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch (SQLException e) {
            System.out.println("SQL#4 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
        	querySt.setInt(1, club.toString());
        	querySt.setInt(2, custID.intValue());
            answers = querySt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL#4 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
            } else {
                inDB = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL#4 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch (SQLException e) {
            System.out.print("SQL#4 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch (SQLException e) {
            System.out.print("SQL#4 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }
    
    
    //SQL#5 - Checks if club offers the book
    public boolean offerCheck() {
        String queryText = ""; // The SQL text.
        PreparedStatement querySt = null; // The query handle.
        ResultSet answers = null; // A cursor.

        boolean inDB = false; // Return.

        queryText = "SELECT title, year " + "FROM yrb_offer " + " WHERE club = ? AND title = ? AND year = ? ";

        // Prepare the query.
        try {
            querySt = conDB.prepareStatement(queryText);
        } catch (SQLException e) {
            System.out.println("SQL#5 failed in prepare");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Execute the query.
        try {
        	querySt.setInt(1, club.toString());
        	querySt.setInt(2, title.toString());
        	querySt.setInt(3, year.intValue());
            answers = querySt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL#5 failed in execute");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Any answer?
        try {
            if (answers.next()) {
                inDB = true;
            } else {
                inDB = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL#5 failed in cursor.");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Close the cursor.
        try {
            answers.close();
        } catch (SQLException e) {
            System.out.print("SQL#5 failed closing cursor.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // We're done with the handle.
        try {
            querySt.close();
        } catch (SQLException e) {
            System.out.print("SQL#5 failed closing the handle.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        return inDB;
    }

    
    //SQL#6 - Insert Purchase
    public void insertPurchase(int cid, String club, String title, int year, String when, int qnty) {
    	 String queryText = ""; // The SQL text.
         PreparedStatement querySt = null; // The query handle.
         ResultSet answers = null; // A cursor.

         //queryText = "SELECT title, year " + "FROM yrb_offer " + " WHERE club = ? AND title = ? AND year = ? ";
         queryText = "INSERT INTO yrb_purchase (cid,club,title,year,whenp,qnty) VALUES "
         		+ "    (?,?,?,?,?,?) ";
         
         // Prepare the query.
         try {
             querySt = conDB.prepareStatement(queryText);
         } catch (SQLException e) {
             System.out.println("SQL#6 failed in prepare");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Execute the query.
         try {
        	querySt.setInt(1, cid.intValue());
         	querySt.setInt(2, club.toString());
         	querySt.setInt(3, title.toString());
         	querySt.setInt(4, year.intValue());
         	querySt.setInt(5, when.toString());
         	querySt.setInt(6, qnty.intValue());
             answers = querySt.executeQuery();
         } catch (SQLException e) {
             System.out.println("SQL#6 failed in execute");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Any answer?
         try {
             if (answers.next()) {
                 inDB = true;
             } else {
                 inDB = false;
             }
         } catch (SQLException e) {
             System.out.println("SQL#6 failed in cursor.");
             System.out.println(e.toString());
             System.exit(0);
         }

         // Close the cursor.
         try {
             answers.close();
         } catch (SQLException e) {
             System.out.print("SQL#6 failed closing cursor.\n");
             System.out.println(e.toString());
             System.exit(0);
         }

         // We're done with the handle.
         try {
             querySt.close();
         } catch (SQLException e) {
             System.out.print("SQL#6 failed closing the handle.\n");
             System.out.println(e.toString());
             System.exit(0);
         }

    }
    
    
    public static void main(String[] args) {
        AddPurchase AP = new AddPurchase(args);
    }
}