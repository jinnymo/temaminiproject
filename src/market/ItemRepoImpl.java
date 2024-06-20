package market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemRepoImpl implements ItemRepo {

	@Override
	public int addItem(String productName, int price, String state, String content, int myUserNum, int categoryId)
			throws SQLException {
		int rowCount = 0;
		String query = " INSERT INTO item (product_name, price, state, date, product_info, user_num, category_ID)\r\n"
				+ "VALUES(?, ?, ?, now(), ?, ?, ?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, productName);
			pstmt.setInt(2, price);
			pstmt.setString(3, state);
			pstmt.setString(4, content);
			pstmt.setInt(5, myUserNum);
			pstmt.setInt(6, categoryId);

			rowCount = pstmt.executeUpdate();
		}
		return rowCount;
	}

	@Override
	public int addImage(int product_id, byte[] image) throws SQLException {
		int rowCount = 0;
		String query = " INSERT INTO original_item_image (product_id, image) VALUES(?, ?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, product_id);
			pstmt.setBytes(2, image);
			rowCount = pstmt.executeUpdate();
		}
		return rowCount;
	}

}
