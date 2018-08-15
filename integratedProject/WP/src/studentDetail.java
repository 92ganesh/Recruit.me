

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
		String sgpa = request.getParameter("sgpa");
		String github = request.getParameter("github");
		String hackerrank = request.getParameter("hackerrank");
		String codechef = request.getParameter("codechef");
		String linkedin = request.getParameter("linkedin");
		
		System.out.println("cname:"+cname);
		System.out.println("sgpa:"+sgpa);
		System.out.println("github:"+github);
		System.out.println("hackerrank:"+hackerrank);
		System.out.println("codechef:"+codechef);
		System.out.println("linkedin:"+linkedin);
		
	}

}
