/*	This program requires databaseConnection.java and SendEmail.java
 */

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginOTP
 */
@WebServlet("/LoginOTP")
public class LoginOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String receivedOTP = request.getParameter("OTP");
		System.out.println("LoginOTP: user entered OTP "+receivedOTP);
		LogManager.logger.info("user entered OTP "+receivedOTP);
		
		if(receivedOTP.compareTo("sendOTP")==0)
		{
			//generate OTP
			String strOTP="";  
			for(int i=1;i<=4;i++)
			{	double doubleOTP = new Random().nextDouble();
				int intOTP = (int)(doubleOTP*10);
				strOTP=strOTP+intOTP;
			}
			
			// store in DB
			databaseConnection.insertOTP("otpdetails",email,strOTP);
			
			// strOTP is generated OTP
			// send it for verification
			SendEmail.send("123tmails@gmail.com", "12345pass",email,"OTP",
					"Your OTP is "+strOTP+"."); 
			
			System.out.println("LoginOTP: sent OTP via mail");
			LogManager.logger.info("Generated OTP is "+strOTP+". Sent OTP via mail");
		}else {
			/// get OTP from database and compare
			String getOTP=databaseConnection.selectOTP("otpdetails",email);
			if(getOTP.compareTo(receivedOTP)==0)
			{	System.out.println("LoginOTP: entered OTP is CORRECT");
				LogManager.logger.info("entered OTP is CORRECT");
				response.addHeader("OPTstatus","RIGHT");
			}else {
				System.out.println("LoginOTP: entered OTP is WRONG");
				LogManager.logger.info("entered OTP is WRONG");
				response.addHeader("OPTstatus","WRONG");
			}
			
		}
	}

}
