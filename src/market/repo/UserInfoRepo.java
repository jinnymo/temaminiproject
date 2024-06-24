package market.repo;

import java.sql.SQLException;

public interface UserInfoRepo {
	void updateUserInfo(String name, String password, String id) throws SQLException;

	void deleteItem(int product_id) throws SQLException;
}
