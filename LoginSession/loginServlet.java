

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
  
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//getParameter function is used to get the values entered by user for authentication
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		
		if(uname.equals("shubham")&&(pwd.equals("1614099")))// username and OTP hardcoded
		{
			//making the HttpSession object work on uname as "username" and OTP as "pwd" 
			HttpSession session =request.getSession();
			session.setAttribute("username",uname);
			session.setAttribute("OTP",pwd);

			response.sendRedirect("welcomePage.jsp");//if account verified, the user will be given access to welcomePage
		}
		else
		{
			response.sendRedirect("loginPage.jsp");//if account is not verified, the user will be redirected again to loginPage
		}
	}

	
}
