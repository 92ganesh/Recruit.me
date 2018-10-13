import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
	public static Logger logger = Logger.getLogger("RecruitMe");  

	public static void initLogs() {
		try {  
	    	// This block configure the logger with handler and formatter  
			FileHandler fh = new FileHandler(Initializer.path+"RecruitMe.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	} 
    
	
	public static void main(String[] args) {  
		LogManager.initLogs();
		LogManager.logger.info("My first log");  
		LogManager.logger.severe("dangerous");
		LogManager.logger.warning("waring");

	    

	    logger.info("Hi How r u?");  

	}
}
