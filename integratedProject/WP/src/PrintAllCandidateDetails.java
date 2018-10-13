

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tableName = request.getParameter("tableName");
		LogManager.logger.info("Request received for table "+tableName);
		if(tableName.equals("candidatedetails"))
		{	String htmlTable  = "",tableHeader = "";
			htmlTable+=tableHeader;
			htmlTable+= databaseConnection.selectAllData("candidatedetails");
			response.addHeader("candidateDetailsTable",htmlTable);
		}else if(tableName.equals("codechef"))
		{	String tableHeader = "<tr><th>Reg No.</th><th>Name</th><th>Email</th><th>User Id</th><th>Rating</th><th>Stars</th><th>Problems Fully Solved</th><th>Problems Partial Solved</th><th>Global rank</th><th>Country rank</th></tr>";
			String htmlTable  = "";
			htmlTable+=tableHeader;
			htmlTable+= databaseConnection.selectAllData("codechef");
			response.addHeader("candidateDetailsTable",htmlTable);
		}else if(tableName.equals("hackerrank"))
		{	String tableHeader = "<tr><th>Reg No.</th><th>Name</th><th>Email</th><th>User Id</th><th>Stars</th><th>Gold medals</th><th>Silver medals</th><th>Bronze medals</th></tr>";
			String htmlTable  = "";
			htmlTable+=tableHeader;
			htmlTable+= databaseConnection.selectAllData("hackerrank");
			response.addHeader("candidateDetailsTable",htmlTable);
		}else if(tableName.equals("github"))
		{	String tableHeader = "<tr><th>Reg No.</th><th>Name</th><th>Email</th><th>User Id</th><th>Repositories</th><th>Stars</th><th>Total Followeres</th><th>Total Following</th></tr>";
			String htmlTable  = "";
			htmlTable+=tableHeader;
			htmlTable+= databaseConnection.selectAllData("github");
			response.addHeader("candidateDetailsTable",htmlTable);
		}
		else if(tableName.equals("countOfCandidateDetails"))
		{	String htmlTable  = "",tableHeader = "";
		htmlTable+=tableHeader;
		htmlTable+= Integer.toString(databaseConnection.selectCountOfData("candidatedetails"));
		response.addHeader("candidateDetailsTable",htmlTable);
	}
		else if(tableName.equals("countOfSelectedCandidates"))
		{	String htmlTable  = "",tableHeader = "";
		htmlTable+=tableHeader;
		htmlTable+= Integer.toString(databaseConnection.selectCountOfSelectedCandidates());
		response.addHeader("candidateDetailsTable",htmlTable);
	}
	}
}
