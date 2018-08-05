

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String receivedOTP = request.getParameter("OTP");
		
		if(receivedOTP=="")
		{
			//generate OTP
			String strOTP="";  
			for(int i=1;i<=4;i++)
			{	double doubleOTP = new Random().nextDouble();
				int intOTP = (int)(doubleOTP*10);
				strOTP=strOTP+intOTP;
			}
			
			// strOTP is generated OTP
			// send it for verification
			Mailer.send("123tmails@gmail.com", "12345pass","tp92ganeshpatra@gmail.com","OTP",
					"Your OTP is "+strOTP+"."); 
		}else {
			/// get OTP from database and compare
			String getOTP;
			if(getOTP==receivedOTP)
				System.out.println("CORRECT");
			else
				System.out.println("WRONG");
			
		}
			
	}

}
