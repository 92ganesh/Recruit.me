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
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


public class SendEmail{    
	 public static void main(String[] args) {   
		   String[] recipients = {"sample@gmail.com"};	// enter valid emails only as it does not check if email exists of not
		   SendEmail.send("123tmails@gmail.com", "12345pass",	recipients,	"Not so urgent", "text message");  // enter your details
		   SendEmail.sendWithAttachment("123tmails@gmail.com", "12345pass",	recipients,	"This is subject part", "text message",Initializer.path+"scrappedInfo8"+".csv");
	
	 }    
	 
	 /** details- sends mail using gmail SMTP server and javaMail API 
	  	* params:- 
	    * from 		- sender's email 
	    * password	- sender's password
	    * to		- receiver's email
	    * sub 		- subject of the email
	    * msg		- body of the email
	    */
	    public static void send(final String from,final String password,String[] to,String sub,String msg){  
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
	    
		/** details- sends mail (which consists of text and attachment) using gmail SMTP server and javaMail API 
	  	* params:- 
		* from 		- sender's email 
		* password	- sender's password
		* to		- receiver's email
		* sub 		- subject of the email
		* msg		- body of the email
		* filePath  -Directory in which file to be sent is stored
		*/
		public static void sendWithAttachment(final String from,final String password,String[] to,String sub,String msg,String filePath)
		{//Get properties object    
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
				
				//compose and send message with attachment
				
			try 
			{
				MimeMessage messenger = new MimeMessage(session);
				messenger.setFrom(new InternetAddress(from));
				InternetAddress[] addresses=new InternetAddress[to.length];	
				for(int i=0;i<to.length;i++)
					addresses[i]=new InternetAddress(to[i]);
				messenger.setRecipients(Message.RecipientType.TO, addresses);
					
				messenger.setSubject(sub);
					
				//Dividing the body part of the email into 2 parts
				//1st one contains text and 2nd contains some attachment
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setText(msg);
				// create new MimeBodyPart object and set DataHandler object to this object      
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
					
				//The specified file(written with exact extension) name should present in the specified directory
				//Almost all types of files(such as .csv,.doc.jpg,etc) can be sent using DataHandler object
				DataSource source = new FileDataSource(filePath);  
				messageBodyPart2.setDataHandler(new DataHandler(source));
				//extract the file name from filePath
				int beginIndex=filePath.lastIndexOf('/');
				messageBodyPart2.setFileName("ExtractedInfo.csv");//The file name can be changed before mailing it
					   
					   
				// create Multipart object and add MimeBodyPart objects to this object      
				Multipart multipart = new MimeMultipart();  
				multipart.addBodyPart(messageBodyPart1);  
				multipart.addBodyPart(messageBodyPart2);  
				
				// set the multipart object to the message object  
				messenger.setContent(multipart );  
			  	  	
			  	Transport.send(messenger);
			  	System.out.println("mail with gathered data is sent successfully to:- ");
			  	for(String each:to)
					System.out.println(each);
			} 
					
			catch (MessagingException mex)
			{
				System.out.println("\n--Exception handling in msgsendsample.java");
				mex.printStackTrace();
				System.out.println();
				mex.printStackTrace();
			  	System.out.println();
			  	Exception ex = mex;
			  	do 
				{
					if (ex instanceof SendFailedException) 
					{
						SendFailedException sfex = (SendFailedException)ex;
						Address[] invalid = sfex.getInvalidAddresses();
						if (invalid != null) 
						{
							System.out.println("    ** Invalid Addresses");
							for (int i = 0; i < invalid.length; i++) 
								System.out.println("         " + invalid[i]);
			  			}
						Address[] validUnsent = sfex.getValidUnsentAddresses();
						if (validUnsent != null) 
						{
							System.out.println("    ** ValidUnsent Addresses");
							for (int i = 0; i < validUnsent.length; i++) 
								System.out.println("         "+validUnsent[i]);
			  			}
						Address[] validSent = sfex.getValidSentAddresses();
						if (validSent != null) 
						{
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
			  	}
				while (ex != null);
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
		
		//Overloaded Method 
	    /**  details:Send the email to only one recipent 
	     * @param from
	     * @param password
	     * @param to
	     * @param sub
	     * @param msg
	     * @param filePath
	     */
	    public static void sendWithAttachment(String from,String password,String to,String sub,String msg,String filePath)
		{  
    		String[] recipient = {to};
    		SendEmail.sendWithAttachment(from,password,recipient,sub,msg,filePath); 
		}
	
	
}

