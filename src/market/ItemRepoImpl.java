package market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepoImpl implements ItemRepo {

	@Override
	public int addItem(String productName, String price, String state, String content, int myUserNum, int categoryId)
			throws SQLException {
		int rowCount = 0;
		String query = " INSERT INTO item (product_name, price, state, date, product_info, user_num, category_id) "
				+ "VALUES(?, ?, ?, now(), ?, ?, ?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, productName);
			pstmt.setString(2, price);
			pstmt.setString(3, state);
			pstmt.setString(4, content);
			pstmt.setInt(5, myUserNum);
			pstmt.setInt(6, categoryId);

			rowCount = pstmt.executeUpdate();
		}
		return rowCount;
	}

	@Override
	public int addImage(int product_id, byte[] image, String tableName, int num) throws SQLException {
		int rowCount = 0;
		String query = " INSERT INTO " + tableName + " (product_id, image,image_num) VALUES(?, ?,?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, product_id);
			pstmt.setBytes(2, image);
			pstmt.setInt(3, num + 1);
			rowCount = pstmt.executeUpdate();
		}
		return rowCount;
	}

	@Override
	public int getProductId(int userNum) throws SQLException {
		int product_id = 0;
		String query = " SELECT product_id from item WHERE user_num = ? order by date desc limit 1 ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, userNum);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				product_id = rs.getInt("product_id");
			}
		}
		return product_id;
	}

	@Override
	public List<ItemListDTO> getItemDTO(int productNum) throws SQLException {
		
		int userNum = 0;
		int productId = 0;
		String name = null;
		String price = null;
		byte[] image = null;
		List<ItemListDTO> itemListDTOs = new ArrayList<>();
		String query = " SELECT i.user_num,i.product_id, i.product_name, i.price, s.image " + "from item as i "
				+ "join scaled_item_image as s on i.product_id = s.product_id " + "where s.image_num = 1"
				+ " and i.product_id > ? order by i.date desc ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, productNum);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userNum = rs.getInt("user_num");
				productId = rs.getInt("product_id");
				name = rs.getString("product_name");
				price = rs.getString("price");
				image = rs.getBytes("image");
				// TODO DTO 입력 -> JList
				itemListDTOs.add(new ItemListDTO(userNum,productId, name, price, image));
			}
		}
		return itemListDTOs;

	}

	@Override
	public List<ItemListDTO> getItemDetailDTO() throws SQLException {
		// TODO SELECT 쿼리
		String query = "  "; 
		// TODO 이미지 큰거3장 작은거3장 받기
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();

		}
		return null;
	}

}
