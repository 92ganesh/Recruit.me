package attemp2;

import com.jaunt.*;

import java.util.*;


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
	
    
	public static void main(String[]args){
     
            //for hackerrank
        	
        	int star_hr_returned=star_HR("92ganesh");
        	int star_cc_returned=star_CC("yash");
        	int rating_cc_returned=rating_CC("yash");
             

           System.out.println("There are a total of "+star_hr_returned+" stars in the hackerrank account of the applicant");

           System.out.println("There are a total of "+star_cc_returned+" stars on this applicants codechef account.");

           System.out.println("Their account has a rating of "+rating_cc_returned+" on codechef");
   
    }
    
    
}