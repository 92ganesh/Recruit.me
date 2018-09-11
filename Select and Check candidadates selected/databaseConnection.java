/* How to setup Eclipse
open eclipse-> File-> project -> java project -> give project name and select 'Use project folder as root for sources and class file' 
then click next -> finish
then right-click on project->new->class->give class name as databseConnection
copy paste this code in created .java file
then right-click the project->build path->add external archive->select .jar file

*/
import java.sql.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;


public class databaseConnection {
	//**********************************************************************************
	// set details of this block according to your postgres settings
	//'RecruitMe' is the DB name	

     private static final String url = "jdbc:postgresql://localhost/RecruitMe";
	   private static final String user = "postgres";
	   private static final String password = "postgres";  
	//**********************************************************************************
	 
	   public static Connection conn = null;
	   public static Connection connect() {
		   try {
	    		Class.forName("org.postgresql.Driver");
	    	} catch (ClassNotFoundException e1) {
	    		e1.printStackTrace();
	    	}
		   
		   try {
	           conn = DriverManager.getConnection(url, user, password);
	           System.out.println("databaseConnection:"+"Connected to the PostgreSQL server successfully.");
	       } catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	       }

	       return conn;
	   }
	   
	   public static void disconnect() {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("databaseConnection:"+e.getMessage());
			}
		}

	   
	   public static String nextround(String tableName){
		   
	
	        PreparedStatement pst=null;
	        try {
	            pst=conn.prepareStatement("select * from "+tableName+" where invite_for_next_round='YES'");
	            ResultSet r=(ResultSet)pst.executeQuery();
	           String detail="";
	            while(r.next()){
	                int regno = r.getInt("reg_no");
	                String name = r.getString("cname");
	                String email = r.getString("cemail");
	                String linkedin = r.getString("linkedIn");
	                String github = r.getString("github");
	                String codechef = r.getString("codechef");
	                String hackerrank = r.getString("hackerrank");
	                String skills=r.getString("skills");
	                String ifnr=r.getString("invite_for_next_round");
	         
	               detail+=regno+"\t"+name+"\t"+email+"\t"+linkedin+"\t"+github+"\t"+codechef+"\t"+hackerrank+"\t"+skills+"\t"+ifnr+"\n";
	          
	           
	             
	            }
	
	            pst.close();
	            return detail;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	         return"";
	        }
	    }
	    
	    public static String check(String tableName,int regNo){
	    	connect();
	        PreparedStatement pst=null;
	        try {
	            pst=conn.prepareStatement("select reg_no,cName,invite_for_next_round from "+tableName+" where reg_no="+regNo);
	            ResultSet r=(ResultSet)pst.executeQuery();
	            String info="";
	            while(r.next()){
	                int regno = r.getInt("reg_no");
	                String name = r.getString("cName");
	                String ifnr=r.getString("invite_for_next_round");
	               
	                info+=regno+"\t"+name+"\t"+ifnr+"\n";
	            }
	            pst.close();
	            return info;
	       
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            return"";
	        }
	    }
	    
	  public void candidatesacccept(String tableName,int regNo){
		   connect();
	        PreparedStatement pst=null;
	        
	        try {
	            pst=conn.prepareStatement("UPDATE "+tableName+" SET invite_for_next_round = 'YES' WHERE reg_no="+regNo);
	          ResultSet r=(ResultSet)pst.executeQuery();
	       
	          
	            pst.close();
	            disconnect();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }  
	    public static void main(String[] args) {
	      databaseConnection app = new databaseConnection();
	        // start the connection
	           app.connect();
	       //list of student having yes in invite for next round     
	          app.nextround("candidateDetails");

	     
	      //checks whether candidate is selected for next round
	          app.check("candidateDetails",1);
	 
	     //changes invites for next round to yes
              app.candidatesacccept("candidateDetails",2);
	    //End the connection
	          app.disconnect();
	}
}