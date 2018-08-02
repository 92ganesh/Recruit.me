package attemp2;
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

/*also refer this for extra info about adding stuff into the sql via java:
 https://stackoverflow.com/questions/14565097/sql-select-statement-with-where-clause
 
*/

import java.sql.*;

import com.jaunt.*;

import java.util.*;



//the connection to the scraper.
public class Scraper{
	
	
	
	//for hackerrank stars
	public static int star_HR(String account_name){
		try{
			
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;
			String url="http://www.hackerrank.com/"+account_name;
			x.visit(url);
			Elements stars= x.doc.findEvery("<svg class=badge-star>");
			return stars.size();
			}
		catch(JauntException e){
			return 0;
		}
		
	}
	
	
	
	
	//for codechef stars
	public static int star_CC(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.codechef.com/users/"+account_name;
        x.visit(url);
        Elements stars=x.doc.findEvery("<div class=rating-star>").findEvery("<span>");
        return stars.size();
		}
	catch(JauntException e){
		return 0;
	}
		
	}
	
	
	
	
	//for codechef ratings
	public static int rating_CC(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.codechef.com/users/"+account_name;
        x.visit(url);
        String rating=x.doc.findFirst("<div class=rating-number>").getChildText();
        int rating_value=Integer.parseInt(rating);
         return rating_value;
		}
	catch(JauntException e){
		return 0;
	}
		
	}
	
    

    
}




//the connection to the database

public class postgresConnection{	
	
	

//**********************************************************************************
// set details of this block acc your postgres settings
//'test' is the DB name	
	
   private final String url = "jdbc:postgresql://localhost/test";
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

   
   
   
   //used to return value whenever requested- check out it's use in insertDataScraped
   //returns in string form the data 'what_data' which is required to be extracted from table 'tablename' having p.k=regnovalue
   public static String selectCertainData(String tableName,int regNo_value,String what_data){
	   //the PK_identifier is the value of the reg_no in the table 'tableName'(identify it by the primary key)
	   //what_data is the data you want to extract from this. 
	    
	
	    try {
	    	PreparedStatement pst=null;
	    	//this is a test
	    	//System.out.println("we made it here");
	    	   pst=conn.prepareStatement("SELECT "+what_data+" FROM "+tableName+" WHERE regNo = ? ;");
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
	  
	   Scraper sc=new Scraper();
	   
	   int cc_stars=Scraper.star_CC(selectCertainData("candidateDetails",regNo,"codechef"));
	   int cc_rating=Scraper.rating_CC(selectCertainData("candidateDetails",regNo,"codechef"));
	   int hr_stars=Scraper.star_HR(selectCertainData("candidateDetails",regNo,"hackerrank"));
	  
	    PreparedStatement pst=null;
       try {		
           pst=conn.prepareStatement("INSERT INTO candidateScraped VALUES(?,?,?,?);");
           pst.setInt(1,regNo);//puts the regno in the 1st posn
           pst.setInt(2,hr_stars);
           pst.setInt(3,cc_stars);
           pst.setInt(4, cc_rating);
           int r=pst.executeUpdate();
           System.out.println("Data scraped and inserted into table!");
           pst.close();
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
               int id = r.getInt("regNo");
               String name = r.getString("cName");
               String email=r.getString("cEmail");
               String linkedIn=r.getString("linkedIn");
               String gitHub=r.getString("gitHub");
               String codeChef=r.getString("codeChef");
               String hackerRank=r.getString("hackerRank");
               System.out.println(id+" "+name+" "+email+" "+linkedIn+" "+gitHub+" "+codeChef+" "+hackerRank);
           }
           
          }
          else if(tableName=="candidateScraped") {
        	    while(r.next()){
                    int reg = r.getInt("regNo");
                    String HRstar = r.getString("hackerRankStar");
                    String CCstar=r.getString("codeChefStar");
                    String CCrating=r.getString("codeChefRating");
                    System.out.println(reg+" "+HRstar+" "+CCstar+" "+CCrating);
        	  
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
   public void insertDataCandidate(String tableName,int regNo, String cName,String cEmail, String linkedIn,String gitHub,String codeChef,String hackerRank){
       PreparedStatement pst=null;
       try {		
           pst=conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?,?,?);");
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
       postgresConnection app = new postgresConnection();
       // start the connection
       app.connect();

       // create table test:    
       app.createTable();
       
       // insert data test for cadidateDetails(String tableName,int regNo, String cName,String cEmail, String linkedIn,String gitHub,String codeChef,String hackerRank):   
       //from here itll automatically fill in the scraped info into candidateScraped as well!
       app.insertDataCandidate("candidateDetails",1,"Yash Naik","yashnaik2406@gmail.com","yashnaik2909","YashAndonia","andonia2","yashnaik2406");
       

       // select all details-candidateDetails:
      

       app.selectAllData("candidateDetails");
       app.selectAllData("candidateScraped");
       // close the connection
       try {
           conn.close();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }
}

