/* This program sends email to given list of recipients. Sender's email and password are hardcoded. You just need to make changes in main function.
 * reference- https://javaee.github.io/javamail/#Samples ->samples.zip-> msgsendsample.java
 * 
 * packages required are javax.mail.jar(javaMail) and activation.jar(JAF)
 * download link of packages:-
 * 		javaMail - https://www.oracle.com/technetwork/java/index-138643.html
 * 		JAF		 - https://www.oracle.com/technetwork/java/jaf11-139815.html
 */

import java.util.Properties;
import javax.mail.*;    
import javax.mail.internet.*;    


public class SendEmail{    
	 public static void main(String[] args) {   
		   String[] recipients = {""};	// enter valid emails only as it does not check if email exists of not
		   SendEmail.send("123tmails@gmail.com", "12345pass",	recipients,	"Not so urgent", "text message");  // enter your details
	 }    
	 
	 /** details- sends mail using gmail SMTP server and javaMail API 
	  	* params:- 
	    * from 		- sender's email 
	    * password	- sender's password
	    * to		- receiver's email
	    * sub 		- subject of the email
	    * msg		- body of the email
	    */
	    public static void send(String from,String password,String[] to,String sub,String msg){  
	          //Get properties object    
	          Properties props = new Properties();    
	          props.put("mail.smtp.host", "smtp.gmail.com");    
	          
	          // Here 465 states that SSL encryption will be used. But i guess its not working in our case as security detail of received mails shows TLS. 
	          // May be we need to provide details of SSL. As of now its not a major issue
	          props.put("mail.smtp.socketFactory.port", "465");    
	          props.put("mail.smtp.socketFactory.class",    
	                    "javax.net.ssl.SSLSocketFactory");    
	          props.put("mail.smtp.auth", "true");    
	          props.put("mail.smtp.port", "465");    
	          //get Session   
	          Session session = Session.getDefaultInstance(props,    
	            new javax.mail.Authenticator() {    
	            protected PasswordAuthentication getPasswordAuthentication() {    
	        	   return new PasswordAuthentication(from,password);  
	            }    
	          }
	          ); 
	          
	          
	          //compose and send message    
	          try {    
		            MimeMessage messenger = new MimeMessage(session);
		            
		      	    messenger.setFrom(new InternetAddress(from));
		      	    
		      	    InternetAddress[] addresses=new InternetAddress[to.length];
		      	    for(int i=0;i<to.length;i++)
		      	    	addresses[i]=new InternetAddress(to[i]);
		      	    
		      	    messenger.setRecipients(Message.RecipientType.TO, addresses);
		      	  	messenger.setSubject(sub);
		      	    messenger.setText(msg);
		      	    Transport.send(messenger);
		      	    System.out.println("mail sent successfully to:- ");
		      	    for(String each:to)
		      	    	System.out.println(each);
	          } catch (MessagingException mex) {
		        	System.out.println("\n--Exception handling in msgsendsample.java");
		
		      	    mex.printStackTrace();
		      	    System.out.println();
		      	    Exception ex = mex;
		      	    do {
		      		if (ex instanceof SendFailedException) {
		      		    SendFailedException sfex = (SendFailedException)ex;
		      		    Address[] invalid = sfex.getInvalidAddresses();
		      		    if (invalid != null) {
		      			System.out.println("    ** Invalid Addresses");
		      			for (int i = 0; i < invalid.length; i++) 
		      			    System.out.println("         " + invalid[i]);
		      		    }
		      		    Address[] validUnsent = sfex.getValidUnsentAddresses();
		      		    if (validUnsent != null) {
		      			System.out.println("    ** ValidUnsent Addresses");
		      			for (int i = 0; i < validUnsent.length; i++) 
		      			    System.out.println("         "+validUnsent[i]);
		      		    }
		      		    Address[] validSent = sfex.getValidSentAddresses();
		      		    if (validSent != null) {
		      			System.out.println("    ** ValidSent Addresses");
		      			for (int i = 0; i < validSent.length; i++) 
		      			    System.out.println("         "+validSent[i]);
		      		    }
		      		}
		      		System.out.println();
		      		if (ex instanceof MessagingException)
		      		    ex = ((MessagingException)ex).getNextException();
		      		else
		      		    ex = null;
		      	    } while (ex != null);
	          }                 
	    }
	    
	    
	 // Overloaded
	    /** details- overloaded function which sends mail to single recipient using gmail SMTP server and javaMail API 
	  	* params:- 
	    * from 		- sender's email 
	    * password	- sender's password
	    * to		- receiver's email
	    * sub 		- subject of the email
	    * msg		- body of the email
	    */
	    public static void send(String from,String password,String to,String sub,String msg){  
	    		String[] recipient = {to};
	    		SendEmail.send(from,password,recipient,sub,msg);  // enter your details
	    }
}

