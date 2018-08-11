
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/logout")
public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		HttpSession session =request.getSession();
		//Removing the attributes (i.e. uname and pwd) from session after the user logout and redirecting it to loginPage
		session.removeAttribute("username");
		session.removeAttribute("OTP");
		session.invalidate();
		response.sendRedirect("loginPage.jsp");
	}
}
	