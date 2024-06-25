package market.repoIm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

import market.DTO.UserDTO;
import market.panel.DBConnectionManager;
import market.repo.UserRepo;

public class UserRepoImpl implements UserRepo {

	public static final String ADD_USER = " insert into user(user_id, name, password) values(?, ?, ?)  ";
//	public static final String CHECKUSER = " select * from user "
	public static final String CHECK_USERID = " select user_id from user where user_id = ? ";
	public static final String CHECK_USERPWD = " select * from user where user_id = ? and password = ? ";
	private Connection conn;

	@Override
	public int adduser(String id, String name, String pwd) throws SQLException {

		int resultRowCount = 0;

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(ADD_USER);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, pwd);
			resultRowCount = pstmt.executeUpdate();

		}
		return resultRowCount;
	}

	// 회원가입시 아이디 중복 확인 메서드
	// resultRowCount = 0 이면 사용가능 1이면 사용 불가
	// 한번 생각해봐야 할거 , 중복확인하고 다른거 하는중 다른사람이 그아이디 사용해버리면??
	// 해결법 생각 해보기 방법은 다양,,,
	@Override
	public boolean checkuserID(String userId) throws SQLException {
		// int resultRowCount = 0;
		ResultSet rs;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(CHECK_USERID);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			System.out.println();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public UserDTO checkUserPwd(String userId, String userpwd) throws SQLException {

		ResultSet rs;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(CHECK_USERPWD);
			pstmt.setString(1, userId);
			pstmt.setString(2, userpwd);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 비밀번호 맞음
				int myNum = rs.getInt("user_num");
				String myId = rs.getString("user_id");
				String myName = rs.getString("name");
				String myPwd = rs.getString("password");
				return new UserDTO(myNum,myId,myName,myPwd);
				
				//return true;
			} else {
				// 비밀번호 틀림
				return null;
			}

		}

	}

}
