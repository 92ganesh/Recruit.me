

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrintAllCandidateDetails
 */
@WebServlet("/PrintAllCandidateDetails")
public class PrintAllCandidateDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintAllCandidateDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		String htmlTable  = "<table style='width:100%; border: 1px solid black;border-collapse: collapse'>";
		String tableHeader = "<tr><th>Reg No.</th><th>Name</th><th>Email</th><th>LinkedIn</th><th>GitHub</th><th>Codechef</th><th>Hackerrank</th></tr>";
		htmlTable+=tableHeader;
		htmlTable+= databaseConnection.selectAllData("candidatedetails");
		htmlTable+="</table>";
		response.addHeader("candidateDetailsTable",htmlTable);
	}
}



















