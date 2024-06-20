package market;

import java.sql.SQLException;

public interface ItemRepo {

	int addItem(String productName, int price, String state, String content, int myUserNum, int categoryId)
			throws SQLException;

	int addImage(int product_id, byte[] image) throws SQLException;
}
