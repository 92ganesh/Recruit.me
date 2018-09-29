import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Invitation
 */
@WebServlet("/Invitation")
public class Invitation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inviteType = request.getParameter("inviteType");
		String checkList = request.getParameter("checkList");
		if(inviteType.equals("selectForNextRound")) {
			databaseConnection.updateInvitationStatus(checkList);
		}else if(inviteType.equals("sendInvitation")) {
			String[] selectedCandidates = databaseConnection.getSelectedCandidates();	
			String[] namesList = selectedCandidates[0].split(";");
			String[] emailsList = selectedCandidates[1].split(";");
					
			for(int i=0;i<namesList.length;i++) {
				String sub = "Invitation for next round";
				String msg = "Congratulation!! "+ namesList[i] +"\nyou have been invited for next round of interview";
				SendEmail.send("123tmails@gmail.com", "12345pass", emailsList[i] , sub, msg);
			}
			System.out.println("Invitation: sent invitation to all selected candidates");			
		}
	}
}
