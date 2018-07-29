import com.jaunt.*;

import java.util.*;


public class codechef_scraper{
    public static void main(String[]args){
        try{
            //for hackerrank
             UserAgent x=new UserAgent();
            System.out.println("Enter the name of your hackerrank account");
            Scanner sc=new Scanner(System.in);
            String account_name=sc.next();
            String url="http://www.hackerrank.com/"+account_name;
            x.settings.autoSaveAsHTML=true;
            x.visit(url);
            Elements stars= x.doc.findEvery("<svg class=badge-star>");
           
           System.out.println("There are a total of "+stars.size()+" stars in the hackerrank account of the applicant");

//for codechef
           System.out.println("Now, enter the codechef account of the user");
           account_name=sc.next();
            url="http://www.codechef.com/users/"+account_name;
            x.visit(url);
            stars=x.doc.findEvery("<div class=rating-star>").findEvery("<span>");
            String rating=x.doc.findFirst("<div class=rating-number>").getChildText();
            System.out.println("There are a total of "+stars.size()+"stars on this applicants codechef account.");
            
            System.out.println("Their account has a rating of "+rating+" on codechef");

            Elements fully_solved=x.doc.findEvery("<div class=content>").findEvery("<h5>");
            
        for(Element u:fully_solved)
            System.out.println(u.getChildText());
        }
        catch(JauntException e){
            System.err.println(e);
            
        }
    }

}