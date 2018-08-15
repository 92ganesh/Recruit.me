
/**
Note:-
*You will also need to keep this file along wiht the Scraper file as well, for it to work
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


	//the connection to the database
		
		

	//**********************************************************************************
	// set details of this block acc your postgres settings
	//'test' is the DB name	

	private final String url = "jdbc:postgresql://localhost/RecruitMe";
	   private final String user = "postgres";
	   private final String password = "postgres";  
	//**********************************************************************************
	   
	   public static Connection conn = null;
	   public Connection connect() {
	       try {
	           conn = DriverManager.getConnection(url, user, password);
	           System.out.println("Connected to the PostgreSQL server successfully.");
	       } catch (SQLException e) {
	           System.out.println(e.getMessage());
	       }

	       return conn;
	   }
	   
	   //create the required table using the sql statements in testDB
	   /*
	   
	   //creates the tables required for the setup
	   public void createTable(){
	       PreparedStatement pst=null;
	       try {
	           pst=conn.prepareStatement("CREATE TABLE candidateDetails (\r\n" + 
	           		"	regNo integer PRIMARY KEY,\r\n" + 
	           		"	cName varchar NOT NULL,\r\n" + 
	           		"	cEmail varchar NOT NULL UNIQUE,\r\n" + 
	           		"	linkedIn varchar,\r\n" + 
	           		"	github varchar,\r\n" + 
	           		"	codechef varchar,\r\n" + 
	           		"	hackerrank varchar,\r\n" + 
	           		"	inviteForNextRound varchar CHECK(inviteForNextRound='YES' OR inviteForNextRound='NO') DEFAULT 'NO'\r\n" + 
	           		");");
	           int r=pst.executeUpdate();
	           
	           pst=conn.prepareStatement("\r\n" + 
	           		"CREATE TABLE candidateScraped (\r\n" + 
	           		"	regNo integer   PRIMARY KEY ,\r\n" + 
	           		"	hackerrankStar int,\r\n" + 
	           		"	codechefStar int,\r\n" + 
	           		"	codechefRating int\r\n" + 
	           		");");

	           r=pst.executeUpdate();
	           
	           pst=conn.prepareStatement("alter table candidateScraped add constraint connec foreign key(regNo) references candidateDetails(regno);");   
	           
	           r=pst.executeUpdate();
	           
	           System.out.println("Table created");
	           pst.close();
	       } catch (SQLException e) {
	           System.out.println(e.getMessage());
	       }
	   }

	   */
	   
	   
	   //used to return value whenever requested- check out it's use in insertDataScraped
	   //returns in string form the data 'what_data' which is required to be extracted from table 'tablename' having p.k=regnovalue
	   public static String selectCertainData(String tableName,int regNo_value,String what_data){
		   //the PK_identifier is the value of the reg_no in the table 'tableName'(identify it by the primary key)
		   //what_data is the data you want to extract from this. 
		    
		
		    try {
		    	PreparedStatement pst=null;
		    	//this is a test
		    	//System.out.println("we made it here");
		    	   pst=conn.prepareStatement("SELECT "+what_data+" FROM "+tableName+" WHERE reg_no = ? ;");
		    	 //cast-this ensures that the output is in varchar format.
		    	
		    	   pst.setInt(1, regNo_value);
		    	   String return_value=new String(); 
		    	   ResultSet r=(ResultSet)pst.executeQuery();
		    	   while(r.next()){ 
		           
		            return_value =  r.getString(what_data);
		    	   }
		    	   pst.close();
		           	return return_value;
		        
		           }catch (SQLException e) {
		           System.out.println(e.getMessage());
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
	           System.out.println(e.getMessage());
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
	           System.out.println(e.getMessage());
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
	           System.out.println(e.getMessage());
	       }
		   
		  
	   }
	   
	   
	   
	   //Selects and displays all the data in the given table 
	   public void selectAllData(String tableName){
	       PreparedStatement pst=null;
	       try {
	           pst=conn.prepareStatement("SELECT * FROM "+tableName);
	           ResultSet r=(ResultSet)pst.executeQuery();
	          if(tableName=="candidateDetails") {
	           while(r.next()){
	               int id = r.getInt("reg_no");
	               String name = r.getString("cName");
	               String email=r.getString("cEmail");
	               String linkedIn=r.getString("linkedIn");
	               String gitHub=r.getString("gitHub");
	               String codeChef=r.getString("codeChef");
	               String hackerRank=r.getString("hackerRank");
	               System.out.println(id+" "+name+" "+email+" "+linkedIn+" "+gitHub+" "+codeChef+" "+hackerRank);
	           }
	           
	          }
	          else if(tableName=="codechef") {
	        	    while(r.next()){
	                    int reg = r.getInt("reg_no");
	                    int cc_star = r.getInt("stars");
	                    int cc_rating=r.getInt("rating");
	                    int cc_fullSolved=r.getInt("problems_fully_solved");
	                    int cc_partiallySolved=r.getInt("problems_partially_solved");
	                    System.out.println(reg+" "+cc_star+" "+cc_rating+" "+cc_fullSolved+" "+cc_partiallySolved);
	        	  
	          }}
	        	    else {
	        	    	System.out.println("Lol");
	        	    }
	           pst.close();
	       } catch (SQLException e) {
	           System.out.println(e.getMessage());
	       }
	   }
	   
	   
	   
	   

	   
	   //inserts data into candidateDetails, and then automatically scrapes the require information and adds it into the other table!
	   public void insertDataCandidate(int regNo, String cName,String cEmail, String linkedIn,String gitHub,String codeChef,String hackerRank){
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
	           System.out.println(e.getMessage());
	       }
	   }
	   
	   
		
		 
		   public static void main(String[] args) {
		       databaseConnection app = new databaseConnection();
		       // start the connection
		       app.connect();

		       // create table test:    
		      // app.createTable();
		       
		       // insert data test for cadidateDetails(String tableName,int regNo, String cName,String cEmail, String linkedIn,String gitHub,String codeChef,String hackerRank):   
		       //from here itll automatically fill in the scraped info into candidateScraped as well!
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
