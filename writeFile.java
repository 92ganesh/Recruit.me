import java.sql.*;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;

public class writeFile 
{
    public static void selectData_c(String tableName,String tableName2,String tableName3)throws Exception
    
    {
    	Scanner sc=new Scanner(System.in);
        PreparedStatement pst=null;
        try 
        {
        	//for codechef account
        
        	System.out.print("Enter your codechef account:");
        	String codechef_account=sc.nextLine();
			pst=databaseConnection.conn.prepareStatement("SELECT * FROM "+tableName+" where username='"+codechef_account+"'");

            ResultSet r=(ResultSet)pst.executeQuery();
        	//System.out.print("SELECT * FROM "+tableName+" where username="+codechef_account);
            
            //make the FileWriter object which will extract the data from database for particular user
            //and write it inside a csv file in a tabular format 
            FileWriter sb = new FileWriter("test.csv");
            sb.append("codechef details");
            sb.append("\n");
            //header will be appear in a format as coded below
            sb.append("username,rating,stars,percent_fully_solved_questions,percent_partially_solved_questions,global_rank,country_rank");    
            sb.append('\n');
            
            
            while(r.next()){
            	String username_c=r.getString("username");
                int rating_c = r.getInt("rating");
                String stars_c = r.getString("stars");
                float percent_fully_solved_questions_c=r.getFloat("percent_fully_solved_questions");
                float percent_partially_solved_questions_c=r.getFloat("percent_partially_solved_questions");
                int global_rank_c=r.getInt("global_rank");
                int country_rank_c=r.getInt("country_rank");
                
                sb.append(username_c+',');  
                sb.append(Integer.toString(rating_c)+',');  
                sb.append(stars_c+',');  
                sb.append(Float.toString(percent_fully_solved_questions_c)+',');
                sb.append(Float.toString(percent_partially_solved_questions_c)+',');
                sb.append(Integer.toString(global_rank_c)+',');   
                sb.append(Integer.toString(country_rank_c)+',');
                sb.append('\n');
                
                System.out.println("done!");    
            }

            //for hackkerank account
            
            System.out.print("Enter your hackkerank account:");
        	String hackkerank_account=sc.nextLine();
            pst=databaseConnection.conn.prepareStatement("SELECT * FROM "+tableName2+" where username='"+hackkerank_account+"'");      
            r=(ResultSet)pst.executeQuery();
             
            sb.append("\n");
            sb.append("Hackkerank details");
            sb.append("\n");
            
            sb.append("username,stars,contest_ranking,percentile,competitions\n");    
           
            
            
            while(r.next()){
            	String username_h=r.getString("username");
                int stars_h = r.getInt("stars");
                float contest_rankings_h=r.getFloat("ContestRating");
                float percentile_h=r.getFloat("percentile");
                int Competitions_h=r.getInt("Competitions");
                
                sb.append(username_h);
                sb.append(',');
                sb.append(Integer.toString(stars_h));
                sb.append(','+Float.toString(contest_rankings_h)+','+Float.toString(percentile_h)+','+Integer.toString(Competitions_h));
                
                sb.append('\n');
                //pw.write(sb.toString());
                System.out.println("done!");    
            }
            
            System.out.print("Enter your git account:");
        	String git_account=sc.nextLine();
            pst=databaseConnection.conn.prepareStatement("SELECT * FROM "+tableName3+" where username='"+git_account+"'");
            r=(ResultSet)pst.executeQuery();
            
           sb.append("\n");
           sb.append("Github details");
           sb.append("\n");
           
           sb.append("username,Repositries,stars,followers,following\n");    
         
           while(r.next()){
           	String username_g=r.getString("username");
               int repositries_g = r.getInt("repositries");
               int stars_g=r.getInt("stars");
               int followers_g =r.getInt("followes");
               int following_g=r.getInt("following_");
               
               sb.append(username_g);
               sb.append(',');
               sb.append(Integer.toString(repositries_g));
               sb.append(','+Integer.toString(stars_g)+','+Integer.toString(followers_g)+','+Integer.toString(following_g));
               
               sb.append('\n');
               //pw.write(sb.toString());
               
               System.out.println("done!");    
           }
            //close the FileWriter object and the connection 
            sb.close();
            //sb.flush();
            pst.close();
            
        } 
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

	
	public static void main(String args[])throws Exception
	{
		 databaseConnection app = new databaseConnection();
	     // start the connection
		   

	     app.connect();
	
	     // select and print data
	     selectData_c("codechef","hackkerank","github");
	
	     // close the connection
	    try 
	     {
	         databaseConnection.conn.close();
	     } 
	     catch (SQLException e) 
	     {
	         System.out.println(e.getMessage());
	     }
	}
}
