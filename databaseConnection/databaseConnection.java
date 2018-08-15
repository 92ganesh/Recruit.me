
/**
Note:-
*You will also need to keep this file along with the Scraper file as well, for it to work
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
add jar files to Apache lib too.
*/

/*also refer this for extra info about adding stuff into the sql via java:
 https://stackoverflow.com/questions/14565097/sql-select-statement-with-where-clause
 
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
	   
	   
	   /**
	    * details used to return value whenever requested- check out it's use in insertDataScraped
	    * @param tableName
	    * @param regNo_value
	    * @param what_data (what_data is the data you want to extract from this.)
	    * @return returns in string form the data 'what_data' which is required to be extracted from table 'tablename' having p.k=regnovalue
	    */
	   public static String selectCertainData(String tableName,int regNo_value,String what_data){
		   //the PK_identifier is the value of the reg_no in the table 'tableName'(identify it by the primary key)
		    try {
	    	   PreparedStatement pst=null;
	    	   pst=conn.prepareStatement("SELECT "+what_data+" FROM "+tableName+" WHERE reg_no = ? ;");
	    	 
	    	   pst.setInt(1, regNo_value);
	    	   String return_value=new String(); 
	    	   ResultSet r=(ResultSet)pst.executeQuery();
	    	   while(r.next()){ 
	           
	            return_value =  r.getString(what_data);
	    	   }
	    	   pst.close();
	           	return return_value;
	        
	           }catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	           return "";
		    }
		}

	   
	   //scrapes the required data from the candidateDetails table, and then adds it into the candidateScraped table
	   public static void insertDataScraped(int regNo) {
		    //inserting in codechef table:   
		    int cc_rating=Scraper.rating_CC(selectCertainData("candidatedetails",regNo,"codechef"));
		    int cc_stars=Scraper.star_CC(selectCertainData("candidatedetails",regNo,"codechef"));
		    int cc_fullySolved=Scraper.fullySolved_CC(selectCertainData("candidatedetails",regNo, "codechef"));
		    int cc_partiallySolved=Scraper.partialSolved_CC(selectCertainData("candidatedetails",regNo, "codechef"));
		    int cc_globalRank=Scraper.globalRank_CC(selectCertainData("candidatedetails",regNo, "codechef"));
		    int cc_localRank=Scraper.localRank_CC(selectCertainData("candidatedetails",regNo, "codechef"));
		    
			//hackerrank
		    int hr_star=Scraper.star_HR(selectCertainData("candidatedetails",regNo,"hackerrank"));
		    int hr_gold=Scraper.gold_HR(selectCertainData("candidatedetails",regNo,"hackerrank"));
		    int hr_silver=Scraper.silver_HR(selectCertainData("candidatedetails",regNo,"hackerrank"));
		    int hr_bronze=Scraper.bronze_HR(selectCertainData("candidatedetails",regNo,"hackerrank"));
		    
			//github
		    int git_repo=Scraper.repo_Git(selectCertainData("candidatedetails",regNo,"github"));
		    int git_star=Scraper.stars_Git(selectCertainData("candidatedetails",regNo,"github"));
		    int git_followers=Scraper.followers_Git(selectCertainData("candidatedetails",regNo,"github"));
		    int git_following=Scraper.following_Git(selectCertainData("candidatedetails",regNo,"github"));

		    PreparedStatement pst=null;
			try {		
			   pst=conn.prepareStatement("INSERT INTO codechef VALUES(?,?,?,?,?,?,?);");
			   pst.setInt(1,regNo);//puts the regno in the 1st posn
			   pst.setInt(2,cc_rating);
			   pst.setInt(3,cc_stars);
			   pst.setInt(4, cc_fullySolved);
			   pst.setInt(5, cc_partiallySolved);
			   pst.setInt(6, cc_globalRank);
			   pst.setInt(7, cc_localRank);
			   
			   int r=pst.executeUpdate();
			   System.out.println("Data scrapedcc and inserted into table!");
			   pst.close();
			} catch (SQLException e) {
			   System.out.println("databaseConnection:"+e.getMessage());
			}
	       
	       
	       PreparedStatement pst2=null;
	       try {		
	           pst2=conn.prepareStatement("INSERT INTO hackerrank VALUES(?,?,?,?,?);");
	           pst2.setInt(1,regNo);//puts the regno in the 1st posn
	           pst2.setInt(2,hr_star);
	           pst2.setInt(3,hr_gold);
	           pst2.setInt(4, hr_silver);
	           pst2.setInt(5, hr_bronze);
	           int r=pst2.executeUpdate();
	           System.out.println("Data scrapedhr and inserted into table!");
	           pst2.close();
	       } catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	       }  
	       
	       
			//github
	       PreparedStatement pst3=null;
	       try {		
	           pst3=conn.prepareStatement("INSERT INTO github VALUES(?,?,?,?,?);");
	           pst3.setInt(1,regNo);//puts the regno in the 1st posn
	           pst3.setInt(2,git_repo);
	           pst3.setInt(3,git_star);
	           pst3.setInt(4, git_followers);
	           pst3.setInt(5, git_following);
	           int r=pst3.executeUpdate();
	           System.out.println("Data scrapedgit and inserted into table!");
	           pst3.close();
	       } catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	       }
		   
		  
	   }
	   
	   
	   //Selects and displays all the data in the given table 
	   public static String selectAllData(String tableName){
		   connect();
	       PreparedStatement pst=null;
	       try {
			  pst=conn.prepareStatement("SELECT * FROM "+tableName);
			  ResultSet r=(ResultSet)pst.executeQuery();
			  String htmlTable = "";
			  if(tableName.compareTo("candidatedetails")==0) {
			   while(r.next()){
			       int reg_no = r.getInt("reg_no");
				   String name = r.getString("cname");
				   String email=r.getString("cemail");
				   String linkedIn=r.getString("linkedin");
				   String gitHub=r.getString("github");
				   String codeChef=r.getString("codechef");
				   String hackerRank=r.getString("hackerrank");
				   htmlTable+="<tr><td>"+reg_no+"</td><td>"+name+"</td><td>"+email+"</td><td>"+linkedIn+"</td><td>"+gitHub+"</td><td>"+codeChef+"</td><td>"+hackerRank+"</td></tr>";
				}
	           
	          }else if(tableName.compareTo("codechef")==0) {
	        	    while(r.next()){
	                    int reg = r.getInt("reg_no");
	                    int cc_star = r.getInt("stars");
	                    int cc_rating=r.getInt("rating");
	                    int cc_fullSolved=r.getInt("problems_fully_solved");
	                    int cc_partiallySolved=r.getInt("problems_partially_solved");
	                    System.out.println(reg+" "+cc_star+" "+cc_rating+" "+cc_fullSolved+" "+cc_partiallySolved);
	        	    }
	          }else {
	        	    	System.out.println("Lol");
	          }
	           pst.close();
	           disconnect();
	           return htmlTable;
	       } catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	           disconnect();
	           return "";
	       }
	   }
	   
	   
	   //inserts data into candidateDetails, and then automatically scrapes the require information and adds it into the other table!
	   public void insertDataCandidate(int regNo, String cName,String cEmail, String linkedIn,String gitHub,String codeChef,String hackerRank){
	      connect();
		   
		   PreparedStatement pst=null;
	       try {		
	           pst=conn.prepareStatement("INSERT INTO candidatedetails VALUES(?,?,?,?,?,?,?);");
	           pst.setInt(1,regNo);//puts the regno in the 1st posn
	           pst.setString(2,cName);
	           pst.setString(3,cEmail);
	           pst.setString(4, linkedIn);
	           pst.setString(5,gitHub);
	           pst.setString(6,codeChef);
	           pst.setString(7,hackerRank);
	           int r=pst.executeUpdate();
	           System.out.println("Data inserted");
	           pst.close();
	           
	           
			   insertDataScraped(regNo);
			   
	       } catch (SQLException e) {
	           System.out.println("databaseConnection:"+e.getMessage());
	       }
	   }
	   
	   

	   /**
	    * details inserts generated OTP in database
	    * @param tableName
	    * @param candidateEmail
	    * @param OTP
	    */
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
					System.out.println("databaseConnection:"+e.getMessage());
				}
			}else {
				try {
					pst=conn.prepareStatement("UPDATE "+tableName+" SET OTP=? where candidate_email=?");
					pst.setString(1,OTP);
					pst.setString(2,candidateEmail);
					int r=pst.executeUpdate();
					System.out.println("Data inserted");
					pst.close();
				} catch (SQLException e) {
					System.out.println("databaseConnection:"+e.getMessage());
				}
			}
			disconnect();
		}
		
		
		/**
		 * details:- used for extracting password from database by LoginOTP.java and directly by studentlogin.html
		 * @param tableName
		 * @param candidateEmail
		 * @return OTP corresponding to entered candidateEmail
		 */
		public static String selectOTP(String tableName,String candidateEmail){
			connect();
			PreparedStatement pst=null;
			String command = "SELECT * FROM "+tableName+" WHERE candidate_email='"+candidateEmail+"'";
			
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
		
		
		/**
		 * details:- used for extracting password from database by LoginPassword.java and directly by employerlogin.html
		 * @param tableName
		 * @param employerEmail
		 * @return:- password corresponding to entered employerEmail
		 */
		public static String selectPassword(String tableName,String employerEmail){
			connect();
			PreparedStatement pst=null;
			String command = "SELECT * FROM "+tableName+" WHERE employer_email='"+employerEmail+"'";
			
			  try {
          pst=conn.prepareStatement(command);
          ResultSet r=(ResultSet)pst.executeQuery();
          r.next();
          String password = r.getString("password");
          pst.close();
          disconnect();
          return password;
			  } catch (SQLException e) {
          System.out.println("databaseConnection:"+e.getMessage());
          disconnect();
          return "NA";
		  	}
		}
		 
		public static void main(String[] args) {
			databaseConnection app = new databaseConnection();
			// start the connection
			app.connect();
			app.insertDataCandidate(561,"Yash Naik","yas23456hna452r3i2k24906@gmail.com","yashnaik2909","YashAndonia","andonia2","yashnaik2406");
			   
			// select all details-candidateDetails:
			app.selectAllData("candidateDetails");
			   
			// close the connection
			try {
			   conn.close();
			} catch (SQLException e) {
			   System.out.println(e.getMessage());
			}
		}
}
