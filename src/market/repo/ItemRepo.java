package market.repo;

import java.sql.SQLException;
import java.util.List;

import market.DTO.ItemDetailDTO;
import market.DTO.ItemListDTO;

public interface ItemRepo {

	int addItem(String productName, String price, String state, String content, int myUserNum, int categoryId)
			throws SQLException;

	int addImage(int product_id, byte[] image, String tableName, int num) throws SQLException;

	int getProductId(int userNum) throws SQLException;

	List<ItemListDTO> getUserItemDTO(int user_num, int offset, int pageSize) throws SQLException;

	ItemDetailDTO getItemDetailDTO(int id) throws SQLException;

	List<ItemListDTO> getItemDTO(int offset, int pageSize) throws SQLException;
}
