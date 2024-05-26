import java.sql.*;
import java.util.*;

/*
 - Download mysql connector j file (jar file).
 - Go to File -> Project Structure -> Libararies -> Tap on "+" -> Click Java -> Select the donwloaded jar file from the system -> Click OK.
 - If in project structure, in External Libraries , we can see the jar connector folder, then it means that our IDE is connected with or DB.
 - Load necessary drivers.
 - Create connection.
 - Execute query. If retrieving : executeQuery(), if add,delete etc : executeUpdate()
 - Result of st.executeQuery(query) will be a table, which will be stored in Result Set.
 -
 */

public class Main {

    /*
      How to know these 3 credentials :
      - Go to mySql Workbench.
      - Then, right click on any DB instance we want to use, & click copy JDBC Connection string.
      - Create a database & add any table , and insert data in that.
      - In url, replace "?user=root" with the database name, (here, mydb)
     */

    // Defining credentials of our DB system
    // private static final String url="jdbc:mysql://localhost:3306/?user=root";
    private static final String url1 ="jdbc:mysql://localhost:3306/mydb";
    private static final String url2="jdbc:mysql://localhost:3306/accounts";
    private static final String username="root";
    private static final String password="1234";

    public static void main(String[] args) {

        // Loading driver class.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Creating Connection
        try {
            Connection con1 = DriverManager.getConnection(url1,username,password);
            // Create Statement
            Statement st = con1.createStatement();
            // Execute Query & saving table in ResultSet
            String query1 = "select * from students";
            ResultSet rs = st.executeQuery(query1); // Here, the query is compiled everytime it is executed.

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double marks = rs.getDouble("marks");

                System.out.println("ID : "+id);
                System.out.println("NAME : "+name);
                System.out.println("AGE : "+age);
                System.out.println("MARKS : "+marks);
            }

            // Query to insert data.
            String query2 = String.format("insert into students(name,age,marks) values('%s',%o,%f)","Rahul",21,91.5f);
            int rowAffected = st.executeUpdate(query2);
            if(rowAffected>0) System.out.println("Data Inserted Successfully!");
            else System.out.println("Data Not Inserted!");

            // Query to update data.
            String query3 = String.format("update students set marks = %f where id=%o",40.5,2);
            int rowUpdated = st.executeUpdate(query3);
            if(rowUpdated>0) System.out.println("Data Updated Successfully!");
            else System.out.println("Data Not Updated!");

            // Query to delete data.
            String query4 = String.format("delete from students where id = %d",2);
            int rowDeleted = st.executeUpdate(query4);
            if(rowDeleted>0) System.out.println("Data Deleted Successfully!");
            else System.out.println("Data Not Deleted!");

            /*
             Problem with Statements , & Need of PreparedStatement :

             -  SQL Injection can be prevented. If user input is directly included in the SQL query1 , it can be manipulated to execute unintended commands.
             -  For e.g., if user enter Drop table user; , it can delete the entire user table.
             -  PreparedStatements use placeholders (?) for parameters , separating the SQL code from the data. This ensures that user input is treated as data, not as part of the SQL command, making it impossible to inject malicious SQL.

             -  Repeatitive Parsing can be prevented. Each time a Statement is executed, db needs to pars & complile the SQL query1, which is inefficient if the same query1 is run multiple times with different values.
             -  PreparedStatements are compiled &  cached by the db. If we execute the same query1 with mulitple times with different parameters , the db doesn't need to recompile each time, which improves performance.
             */

            // Using PreparedStatements

            // Inserting Using PreparedStatements
            String query5 = "insert into students(name,age,marks) values(?,?,?)";
            PreparedStatement ps = con1.prepareStatement(query5); // Here, the query5 is compiled, & later it will only be executed, not compiled again.
            // Setting values in the placeholders (1,"Anika") means value of 1st parameter (name) will be Anika
            ps.setString(1,"Anika");
            ps.setInt(2,23);
            ps.setDouble(3,99.6);
            // Executing statements
            int rowAffectedPS = ps.executeUpdate(); // Here, query5 is already compiled, so we don't have to mention that!
            if(rowAffectedPS>0) System.out.println("Data Inserted Successfully Using PreparedStatements!");
            else System.out.println("Data Not Inserted Using PreparedStatements!");

            // Retrieving Using PreparedStatements
            String query6 = "select marks from students where id = ?";
            PreparedStatement ps2 = con1.prepareStatement(query6); // Here, the query5 is compiled, & later it will only be executed, not compiled again.
            ps2.setInt(1,1);
            ResultSet rs2 = ps2.executeQuery();
            // If data is present, then only print marks , that's what is checked by rs2.next()
            if(rs2.next()){
                double marks = rs2.getDouble("marks");
                System.out.println("Marks : "+marks);
            }
            else System.out.println("Marks not found!");

            /*
              Batch Processing
              - Running multiple queries at once.
              - A set of queries can be compiled & executed at once. 
             */

            // Batch Processing Using Statements
            Scanner sc = new Scanner(System.in);
            Statement stmt = con1.createStatement();

            while(true){
                System.out.println("Enter name : ");
                String name = sc.next();
                System.out.println("Enter age : ");
                int age = sc.nextInt();
                System.out.println("Enter marks : ");
                Double marks = sc.nextDouble();
                System.out.println("Do you wish to continue? (Y/N)");
                String choice = sc.next();
                String query7 = String.format("insert into students(name,age,marks) values('%s',%d,%f)",name,age,marks);
                // Adding query in the batch, to execute them all simultaneously later.
                stmt.addBatch(query7);
                if(choice.toUpperCase().equals("N")) break;
            }

            // Whichever query gets executed successfully, its respective index will contain '1', else it will contain '0'
            // After executing the batch, all these data which we have entered, will be added in our db (as, we have fired insert query with all these)

            int[] batchArr = stmt.executeBatch();

            // Will tell which query is not executed
            for(int i=1;i<=batchArr.length;i++){
                if(batchArr[i-1]==0) System.out.println("Query "+(i-1)+" not executed successfully!");
            }

            // Batch Processing using Prepared Statements
            String query8 = String.format("insert into students(name,age,marks) values(?,?,?)");
            PreparedStatement prepstmt = con1.prepareStatement(query8);

            while(true){
                System.out.println("Enter name : ");
                String name = sc.next();
                System.out.println("Enter age : ");
                int age = sc.nextInt();
                System.out.println("Enter marks : ");
                Double marks = sc.nextDouble();

                prepstmt.setString(1,name);
                prepstmt.setInt(2,age);
                prepstmt.setDouble(3,marks);
                prepstmt.addBatch();

                System.out.println("Do you wish to continue? (Y/N)");
                String choice = sc.next();
                if(choice.toUpperCase().equals("N")) break;
            }

            int[] batchArrPS = prepstmt.executeBatch();

            for(int i=1;i<=batchArrPS.length;i++){
                if(batchArrPS[i-1]==0) System.out.println("Query "+(i-1)+" not executed successfully!");
            }

            // Transaction Handling

            // using "accounts" db
            // Table name = "transaction"
            Connection con2 = DriverManager.getConnection(url2,username,password);
            con2.setAutoCommit(false);
            String debit_query = "update transaction set balance = balance - ? where account_no = ?";
            String credit_query = "update transaction set balance = balance + ? where account_no = ? ";
            PreparedStatement debitPreparedStatement = con2.prepareStatement(debit_query);
            PreparedStatement creditPreparedStatement = con2.prepareStatement(credit_query);

            System.out.println("Enter the amount to be transferred : ");
            double amount = sc.nextDouble();

            debitPreparedStatement.setDouble(1,amount);
            debitPreparedStatement.setInt(2,101);
            creditPreparedStatement.setDouble(1,amount);
            creditPreparedStatement.setInt(2,102);

            debitPreparedStatement.executeUpdate();
            creditPreparedStatement.executeUpdate();

            if(isSufficient(con2,101,amount)) {
                con2.commit();
                System.out.println("Transaction Successful!");
            }
            else {
                con2.rollback();
                System.out.println("Transaction Failed!");
            }


            // close all the instances. 
             rs.close();
             con1.close();
             con2.close();



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    static  boolean isSufficient(Connection con, int account_no, double amount){
        try {
            String checkBalance = "select balance from transaction where account_no = ? ";
            PreparedStatement balancePS = con.prepareStatement(checkBalance);
            balancePS.setInt(1,account_no);
            ResultSet resultSet = balancePS.executeQuery();
            if(resultSet.next()){
                double currBal = resultSet.getDouble("balance");
                if(amount>currBal) return false;
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}