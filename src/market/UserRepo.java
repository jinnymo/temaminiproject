package market;

import java.sql.SQLException;
import java.util.List;

public interface UserRepo {
//
//	int addQuizQuestion(String question, String answer) throws SQLException;
//	List<QuizDTO> viewQuizQuestion() throws SQLException;
//	QuizDTO playQuizGame() throws SQLException;
//	
	
	
	int adduser(String id,String name, String pwd) throws SQLException;
	//void checkuser();
	List<UserDTO> checkuser() throws SQLException;
	
	
}
