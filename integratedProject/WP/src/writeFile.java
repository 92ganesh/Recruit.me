import java.sql.*;

import java.io.FileWriter;
import java.sql.SQLException;

public class writeFile 
{
	/**
	 * details:This function will take input as registration number from the database 
	 * 			and  the student's scraped information will be taken from database if 
	 * 			present inside database and will be stored in the scrappedInfo.csv file
	 * 			based on user's registration number entered. 
	 * 
	 * params:registration_no
	 */
    public static void createCsv(int registration_no)throws Exception
    {
    	// start the connection		  
    	databaseConnection.connect();
        PreparedStatement pst=null;
        try 
        {
        	//for codechef account
        	pst=databaseConnection.conn.prepareStatement("SELECT * FROM codechef where reg_no="+registration_no);
            ResultSet r=(ResultSet)pst.executeQuery();
            //make the FileWriter object which will extract the data from database for particular user
            //and write it inside a csv file in a tabular format 
            FileWriter sb = new FileWriter(Initializer.path+"scrappedInfo"+registration_no+".csv");
            sb.append("codechef details");
            sb.append("\n");
            //header will be appear in a format as coded below
            sb.append("registration number,rating,stars,fully_solved_questions,partially_solved_questions,global_rank,country_rank");    
            sb.append('\n');
            
            while(r.next())
            {
            	int registration_c=r.getInt("reg_no");
                int rating_c = r.getInt("rating");
                String stars_c = r.getString("stars");
                float percent_fully_solved_questions_c=r.getFloat("problems_fully_solved");
                float percent_partially_solved_questions_c=r.getFloat("problems_partially_solved");
                int global_rank_c=r.getInt("global_rank");
                int country_rank_c=r.getInt("country_rank");
                
                sb.append(Integer.toString(registration_c)+',');  
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
            pst=databaseConnection.conn.prepareStatement("SELECT * FROM hackerrank where reg_no="+registration_no);      
            r=(ResultSet)pst.executeQuery();
             
            sb.append("\n");
            sb.append("Hackkerank details");
            sb.append("\n");
            sb.append("registration number,stars,gold,silver,bronze\n");    
           
            while(r.next())
            {
            	int registration_h=r.getInt("reg_no");
                int stars_h = r.getInt("stars");
                int gold_h=r.getInt("gold");
                int silver_h=r.getInt("silver");
                int bronze_h=r.getInt("bronze");
                
                sb.append(Integer.toString(registration_h));
                sb.append(',');
                sb.append(Integer.toString(stars_h));
                sb.append(','+Integer.toString(gold_h)+','+Integer.toString(silver_h)+','+Integer.toString(bronze_h));
                sb.append('\n');
                System.out.println("done!");    
            }
            
            //for Github account
            pst=databaseConnection.conn.prepareStatement("SELECT * FROM github where reg_no="+registration_no);
            r=(ResultSet)pst.executeQuery();
            
           sb.append("\n");
           sb.append("Github details");
           sb.append("\n");
           sb.append("registration number,Repositries,stars,followers,following\n");    
         
           while(r.next())
           {
               int registration_g=r.getInt("reg_no");
               int repositries_g = r.getInt("repositories");
               int stars_g=r.getInt("stars");
               int followers_g =r.getInt("followers");
               int following_g=r.getInt("following_");
               
               sb.append(Integer.toString(registration_g));
               sb.append(',');
               sb.append(Integer.toString(repositries_g));
               sb.append(','+Integer.toString(stars_g)+','+Integer.toString(followers_g)+','+Integer.toString(following_g));
               sb.append('\n');               
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
	    // select and print data
	    createCsv(2);
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
