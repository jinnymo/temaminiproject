package market.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import market.DTO.UserDTO;

public interface UserRepo {
//
//	int addQuizQuestion(String question, String answer) throws SQLException;
//	List<QuizDTO> viewQuizQuestion() throws SQLException;
//	QuizDTO playQuizGame() throws SQLException;
//	
	
	
	int adduser(String id,String name, String pwd) throws SQLException;
	//void checkuser();
	boolean checkuserID(String userId) throws SQLException;
	
	UserDTO checkUserPwd(String userId,String userpwd) throws SQLException;
	
}
