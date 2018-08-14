import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;


//the connection to the scraper.
public class Scraper{
	
	
	//hackerrank
	
	
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
	
	
	//for hackerrank contestRanting
	/*

	public static String contestRating_HR(String account_name){
		try{
			
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;
			String url="http://www.hackerrank.com/"+account_name;
			x.visit(url);
				
			String[] arr=new String[100];
			Element uno=x.doc.findFirst("<span class=txt-navy>");
			
			
			
			arr[0]=uno.getChildText();
			
			return arr[0];
			}
		catch(JauntException e){
			return "";
		}
		
	}
	*/
	
	//gold:

	
	public static int gold_HR(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.hackerrank.com/"+account_name;
        x.visit(url);
        
        
        Elements dos= x.doc.findEach("<img height=25>");
        String[] arr=new String[100];
        int i=0;
        for(Element rank:dos) {
        	arr[i]=rank.getParent().getChildText();
        	i++;
        }
        
        
        return Integer.parseInt(arr[0].trim());
		}
	catch(JauntException e){
		return 0;
	}
}

	
	
	
	public static int silver_HR(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.hackerrank.com/"+account_name;
        x.visit(url);
        
        
        Elements dos= x.doc.findEach("<img height=25>");
        String[] arr=new String[100];
        int i=0;
        for(Element rank:dos) {
        	arr[i]=rank.getParent().getChildText();
        	i++;
        }
        
        
        return Integer.parseInt(arr[1].trim());
		}
	catch(JauntException e){
		return 0;
	}
}

	
	public static int bronze_HR(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.hackerrank.com/"+account_name;
        x.visit(url);
        
        
        Elements dos= x.doc.findEach("<img height=25>");
        String[] arr=new String[100];
        int i=0;
        for(Element rank:dos) {
        	arr[i]=rank.getParent().getChildText();
        	i++;
        }
        
        
        return Integer.parseInt(arr[2].trim());
		}
	catch(JauntException e){
		return 0;
	}
}

	
	
	
	
	
	
	//codechef::
	
	
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

	public static int globalRank_CC(String account_name) {
		try{
		UserAgent x=new UserAgent();

		x.settings.autoSaveAsHTML=true;		
		String url="http://www.codechef.com/users/"+account_name;
        x.visit(url);
        String rating=x.doc.findFirst("<div class=rating-ranks>").findFirst("<strong>").getChildText();
        int globalRank=Integer.parseInt(rating);
         return globalRank;
		}
	catch(JauntException e){
		return 0;}
	}
	
	
	
		
		
		public static int localRank_CC(String account_name) {
			try{
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;		
			String url="http://www.codechef.com/users/"+account_name;
	        x.visit(url);
	        
	        Element uno=x.doc.findFirst("<div class=rating-ranks>");
	        Elements dos= uno.findEach("<strong>");
	        String[] arr=new String[100];
	        int i=0;
	        for(Element rank:dos) {
	        	arr[i]=rank.getChildText();
	        	i++;
	        }
	        
	        
	        return Integer.parseInt(arr[1]);
			}
		catch(JauntException e){
			return 0;
		}
	}

		
		
		

		public static int fully_solvedCC(String account_name) {
			try{
				UserAgent x=new UserAgent();

				x.settings.autoSaveAsHTML=true;		
				String url="http://www.codechef.com/users/"+account_name;
		        x.visit(url);/*
		        String rating=x.doc.findFirst("<h5>").getChildText();
		        
		         return rating;*/
		        Elements uno=x.doc.findEach("<h5>");//since the numis in h5
		        String[] all_solved=new String[10];
		        int i=0;
		        
		        for(Element val:uno) {
		        	all_solved[i]=val.getChildText();
		        	i++;
		        }

		        String returner=new String();
		        returner=all_solved[0].substring(14, all_solved[0].length()-1);
		        return Integer.parseInt(returner);//converts the string to the value
				}
			catch(JauntException e){
				return 0;
			}
		}
		

		public static int partial_solvedCC(String account_name) {
			try{
				UserAgent x=new UserAgent();

				x.settings.autoSaveAsHTML=true;		
				String url="http://www.codechef.com/users/"+account_name;
		        x.visit(url);/*
		        String rating=x.doc.findFirst("<h5>").getChildText();
		        
		         return rating;*/
		        Elements uno=x.doc.findEach("<h5>");
		        String[] all_solved=new String[10];
		        int i=0;
		        
		        for(Element val:uno) {
		        	all_solved[i]=val.getChildText();
		        	i++;
		        }
		        
		        String returner=new String();
		        returner=all_solved[1].substring(18, all_solved[1].length()-1);
		        return Integer.parseInt(returner);
				}
			catch(JauntException e){
				return 0;
			}
		}
		
		
		
		
//github:

		//no.ofrepos
		public static int repoGit(String account_name) {
			try{
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;		
			String url="http://www.github.com/"+account_name;
	        x.visit(url);
	        Elements uno=x.doc.findEach("<span class=Counter>");
	        String[] arr=new String [100];
	        int i=0;
	        for(Element val:uno) {
	        	arr[i]=val.getChildText();
	        	i++;
	        }
	       
	         return Integer.parseInt(arr[0].trim());
			}
		catch(JauntException e){
			return 0;}
		}
		
		
		

		//no. of stars
		public static int starGit(String account_name) {
			try{
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;		
			String url="http://www.github.com/"+account_name;
	        x.visit(url);
	        Elements uno=x.doc.findEach("<span class=Counter>");
	        String[] arr=new String [100];
	        int i=0;
	        for(Element val:uno) {
	        	arr[i]=val.getChildText();
	        	i++;
	        }
	       
	         return Integer.parseInt(arr[1].trim());
			}
		catch(JauntException e){
			return 0;}
		}



		//no. of followers
		public static int followerGit(String account_name) {
			try{
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;		
			String url="http://www.github.com/"+account_name;
	        x.visit(url);
	        Elements uno=x.doc.findEach("<span class=Counter>");
	        String[] arr=new String [100];
	        int i=0;
	        for(Element val:uno) {
	        	arr[i]=val.getChildText();
	        	i++;
	        }
	       
	         return Integer.parseInt(arr[2].trim());
			}
		catch(JauntException e){
			return 0;}
		}
		


		//no. of following
		public static int followingGit(String account_name) {
			try{
			UserAgent x=new UserAgent();

			x.settings.autoSaveAsHTML=true;		
			String url="http://www.github.com/"+account_name;
	        x.visit(url);
	        Elements uno=x.doc.findEach("<span class=Counter>");
	        String[] arr=new String [100];
	        int i=0;
	        for(Element val:uno) {
	        	arr[i]=val.getChildText();
	        	i++;
	        }
	       
	         return Integer.parseInt(arr[3].trim());
			}
		catch(JauntException e){
			return 0;}
		}
		
		
		
		
		
		
		
		
		
		
	public static void main(String args[]){


		 
//hackerrrank

		System.out.println("The starHR is "+star_HR("scturtle"));
		
	//	System.out.println("The contest rating in hr is "+contestRating_HR("scturtle"));
	 //Due to a limitation in jaunt, it is impossible to extract the data of the contestRatings, so instead, i'm taking details of goldbadge,silverbadges and bronze

		System.out.println("The gold badges in hr is "+gold_HR("scturtle"));

		System.out.println("The silver badges in hr is "+silver_HR("scturtle"));

		System.out.println("The bronze badges in hr is "+bronze_HR("scturtle"));
		
		
//codechef
		System.out.println("The starCC is "+star_CC("yash"));
		
		System.out.println("The global rank is "+globalRank_CC("yash"));
		
		System.out.println("The local rank is "+localRank_CC("yash"));
		System.out.println("The fully solved is "+fully_solvedCC("yash"));
		System.out.println("The partial solved is "+partial_solvedCC("yash"));
		
		
//github

		System.out.println("The repos are "+repoGit("YashAndonia"));
		System.out.println("The stars are "+starGit("YashAndonia"));
		System.out.println("The followers are "+followerGit("YashAndonia"));
		System.out.println("The following are "+followingGit("YashAndonia"));
	
	}


	
    

    
}
