

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class studentDetail
 */
@WebServlet("/studentDetail")
public class studentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		String email = request.getParameter("email");
		String sgpa = request.getParameter("sgpa");
		String github = request.getParameter("github");
		String hackerrank = request.getParameter("hackerrank");
		String codechef = request.getParameter("codechef");
		String linkedin = request.getParameter("linkedin");
		String skills = request.getParameter("skills");
		int regNo = databaseConnection.totalCandidates()+1;
		databaseConnection.insertDataCandidate(regNo, cname, email, linkedin, github, codechef, hackerrank, skills);
		
		try 
		{
			writeFile.createCsv(regNo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    SendEmail.sendWithAttachment("123tmails@gmail.com", "12345pass", email,	"From recruit.Me team", "Summary of your contributions. please see the attached file",(Initializer.path+"scrappedInfo"+regNo+".csv"));
	}

}
