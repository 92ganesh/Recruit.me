

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginPassword
 */
@WebServlet("/LoginPassword")
public class LoginPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String receivedPassword = request.getParameter("password");
		/// get password from database and compare
		String getPassword = databaseConnection.selectPassword("passworddetails",email);
		if(getPassword.compareTo(receivedPassword)==0)
		{	response.addHeader("PasswordStatus","RIGHT");
		}else {
			response.addHeader("PasswordStatus","WRONG");
		}
			
	}

}
