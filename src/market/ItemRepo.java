package market;

import java.sql.SQLException;

public interface ItemRepo {

	int addItem(String productName, String price, String state, String content, int myUserNum, int categoryId)
			throws SQLException;

	int addImage(int product_id, byte[] image, String tableName) throws SQLException;

	int getProductId(int userNum) throws SQLException;
	
}
