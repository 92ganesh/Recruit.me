/**
 Note:-
 * you will need the postgres jdbc driver which can be downloaded from https://jdbc.postgresql.org/download.html
 * if using java 9 you can the package called postgresql-42.2.4.jar uploaded with this program
 * This code is tested with postgres 9.4 and java 9
 references:-
 * http://www.postgresqltutorial.com/postgresql-jdbc/
 * JDBC notes from PL 2
*/

/* How to setup Eclipse
open eclipse-> File-> project -> java project -> give project name and select 'Use project folder as root for sources and class file' 
then click next -> finish
then right-click on project->new->class->give class name as postgresConnection 
copy paste this code in created .java file
then right-click the project->build path->add external archive->select .jar file
 */




import java.sql.*;
public class postgresConnection{

//**********************************************************************************
//  set details of this block acc your postgres settings
// 'test' is the DB name			
									   
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/test";
    private static final String user = "postgres";
    private static final String password = "postgres";  
//**********************************************************************************
    
    public static Connection conn = null;
    public static Connection connect() {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
    	
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    public static void disconnect() {
    	 // close the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    // this is just for testing. You have to make required table manually in postgres
    public void createTable(){
        PreparedStatement pst=null;
        try {
            pst=conn.prepareStatement("create table student(stuId integer PRIMARY KEY,stuName varchar)");
            int r=pst.executeUpdate();
            System.out.println("Table created");
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertOTP(String tableName,String candidateEmail,String OTP){
    	PreparedStatement pst=null;
        
        String s = selectOTP(tableName,candidateEmail);
        connect();
        
        if(s.compareTo("NA")==0)
        {	try {
	            pst=conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?)");
	            pst.setString(1,candidateEmail);
	            pst.setString(2,OTP);
	            int r=pst.executeUpdate();
	            System.out.println("Data inserted");
	            pst.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
        }else {
        	try {
	            pst=conn.prepareStatement("UPDATE "+tableName+" SET OTP=? where candidateEmail=?");
	            pst.setString(1,OTP);
	            pst.setString(2,candidateEmail);
	            int r=pst.executeUpdate();
	            System.out.println("Data inserted");
	            pst.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
        }
        disconnect();
    }
    
    public static String selectOTP(String tableName,String candidateEmail){
    	connect();
    	PreparedStatement pst=null;
    	String command = "SELECT * FROM "+tableName+" WHERE candidateEmail='"+candidateEmail+"'";
    	
        try {
        	pst=conn.prepareStatement(command);
            ResultSet r=(ResultSet)pst.executeQuery();
            r.next();
            String id = r.getString("OTP");
            pst.close();
            disconnect();
            return id;
        } catch (SQLException e) {
            System.out.println("databaseConnection:"+e.getMessage());
            disconnect();
            return "NA";
        }
    }

    public static String selectData(String tableName){
    	connect();
    	PreparedStatement pst=null;
        try {
            pst=conn.prepareStatement("SELECT * FROM "+tableName);
            ResultSet r=(ResultSet)pst.executeQuery();
            while(r.next()){
                int id = r.getInt("regno");
                String cname = r.getString("cname");
                String cemail = r.getString("cemail");
                String linkedin = r.getString("linkedin");
                String github = r.getString("github");
                String codechef = r.getString("codechef");
                String hackerrank = r.getString("hackerrank");
                System.out.println(id+" "+cname+" "+cemail+" "+linkedin);
            
            
               /* PrintWriter pr = response.getWriter();
        		response.setContentType("text/html");
        		pr.println("<pre>"+id+" "+cname+" "+cemail+" "+linkedin+"</pre>");
        		pr.close();
        		*/
            }
            pst.close();
            disconnect();
            return "IN";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            disconnect();
            return "OUT";
        }
    }
    
    

    
    public static void main(String[] args) {
      // selectData("candidateDetails");
       
    	insertOTP("OTPdetails","sham","9294");
    }
}