package market.repoIm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import market.panel.DBConnectionManager;
import market.repo.UserInfoRepo;

public class UserInfoRepoIm implements UserInfoRepo {

	@Override
	public void updateUserInfo(String name, String password, String id) throws SQLException {
		String query = " UPDATE user SET  name = ? , password = ? WHERE user_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
		}
	}

 }
