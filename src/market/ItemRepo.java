package market;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepo {

	int addItem(String productName, String price, String state, String content, int myUserNum, int categoryId)
			throws SQLException;

	int addImage(int product_id, byte[] image, String tableName,int num) throws SQLException;

	int getProductId(int userNum) throws SQLException;

	List<ItemListDTO> getItemDTO() throws SQLException;
	
	List<ItemListDTO> getItemDetailDTO() throws SQLException;
}
