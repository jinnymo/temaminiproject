package market;

import java.sql.SQLException;

public interface UserInfoRepo {
	void updateUserInfo(String name, String password, String id) throws SQLException;
}
