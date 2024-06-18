package market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepoImpl implements UserRepo {

	public static final String ADD_USER = " insert into user(user_id, name, password) valuser(?, ?)  ";
//	public static final String CHECKUSER = " select * from user "

	
	@Override
	public int adduser(String id,String name, String pwd) throws SQLException {

		int resultRowCount = 0;

		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(ADD_USER);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, pwd);
			resultRowCount = pstmt.executeUpdate();

		}
		return resultRowCount;
	}

	@Override
	public void checkuser() {
	

	}

}
