package market.repoIm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import market.DTO.ItemDetailDTO;
import market.DTO.ItemListDTO;
import market.panel.DBConnectionManager;
import market.repo.ItemRepo;

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
		String query = " INSERT INTO " + tableName + " (product_id, image, image_num) VALUES(?, ?, ?) ";
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
	public List<ItemListDTO> getItemDTO(int pageSize, int offset) throws SQLException {
		int userNum = 0;
		int productId = 0;
		String name = null;
		String price = null;
		byte[] image = null;
		List<ItemListDTO> itemListDTOs = new ArrayList<>();

		String query = " SELECT i.user_num, i.product_id, i.product_name, i.price, s.image "
				+ " FROM item AS i "
				+ " JOIN ( "
				+ "    SELECT product_id, image "
				+ "    FROM scaled_item_image "
				+ "    WHERE image_num = 1 "
				+ "    ORDER BY product_id DESC "
				+ "    LIMIT ? OFFSET ? "
				+ " ) AS s ON i.product_id = s.product_id; ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userNum = rs.getInt("user_num");
				productId = rs.getInt("product_id");
				name = rs.getString("product_name");
				price = rs.getString("price");
				image = rs.getBytes("image");
				itemListDTOs.add(new ItemListDTO(userNum, productId, name, price, image));
			}
		}
		return itemListDTOs;
	}

	@Override
	public ItemDetailDTO getItemDetailDTO(int id) throws SQLException {
		int product_id = id;
		String product_name = null;
		String price = null;
		String state = null;
		String date = null;
		String product_info = null;
		int user_num = 0;
		String user_id = null;

		List<byte[]> sImageList = new ArrayList<>();
		List<byte[]> bImageList = new ArrayList<>();

		byte[] sImage = null;
		byte[] bImage = null;

		List<ItemDetailDTO> itemDetailDTOs = new ArrayList<>();
		String userQuery = " SELECT i.product_id, product_name, price, state, date, product_info, u.user_num, u.user_id "
				+ " FROM item AS i JOIN user AS u ON i.user_num = u.user_num WHERE i.product_id = ? ";
		String smallQuery = " SELECT s.image FROM item AS i "
				+ " JOIN scaled_item_image AS s ON i.product_id = s.product_id WHERE i.product_id = ? ";
		String bigQuery = " SELECT image FROM item AS i "
				+ " JOIN original_item_image AS o ON i.product_id = o.product_id WHERE i.product_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmtUser = conn.prepareStatement(userQuery);
				PreparedStatement pstmtSmall = conn.prepareStatement(smallQuery);
				PreparedStatement pstmtBig = conn.prepareStatement(bigQuery)) {

			pstmtUser.setInt(1, product_id);
			pstmtSmall.setInt(1, product_id);
			pstmtBig.setInt(1, product_id);

			ResultSet rsU = pstmtUser.executeQuery();
			ResultSet rsS = pstmtSmall.executeQuery();
			ResultSet rsB = pstmtBig.executeQuery();

			if (rsU.next()) {
				product_id = rsU.getInt("product_id");
				product_name = rsU.getString("product_name");
				price = rsU.getString("price");
				state = rsU.getString("state");
				date = rsU.getString("date");
				product_info = rsU.getString("product_info");
				user_num = rsU.getInt("user_num");
				user_id = rsU.getString("user_id");
			}
			while (rsS.next()) {
				sImage = rsS.getBytes("image");
				sImageList.add(sImage);
			}
			while (rsB.next()) {
				bImage = rsB.getBytes("image");
				bImageList.add(bImage);
			}

		}
		return new ItemDetailDTO(product_id, product_name, price, state, date, product_info, user_num, user_id,
				sImageList, bImageList);

	}

	@Override
	public List<ItemListDTO> getUserItemDTO(int user_num, int pageSize, int offset) throws SQLException {
		int userNum = 0;
		int productId = 0;
		String name = null;
		String price = null;
		byte[] image = null;
		List<ItemListDTO> itemListDTOs = new ArrayList<>();
		String query = " SELECT i.user_num, i.product_id, i.product_name, i.price, MAX(s.image) AS image "
				+ "FROM item AS i JOIN scaled_item_image AS s ON i.product_id = s.product_id JOIN user AS u ON i.user_num = u.user_num "
				+ "WHERE s.image_num = 1 GROUP BY s.product_id ORDER BY i.date DESC LIMIT ? OFFSET ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userNum = rs.getInt("user_num");
				productId = rs.getInt("product_id");
				name = rs.getString("product_name");
				price = rs.getString("price");
				image = rs.getBytes("image");
				itemListDTOs.add(new ItemListDTO(userNum, productId, name, price, image));
			}

		}
		return itemListDTOs;
	}
}
