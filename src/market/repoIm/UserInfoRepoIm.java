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

	@Override
	public void deleteItem(int product_id) throws SQLException {
		String itemQuery = " DELETE FROM item WHERE product_id = ? ";
		String sImageQuery = " DELETE FROM scaled_item_image WHERE product_id = ? ";
		String oImageQuery = " DELETE FROM original_item_image WHERE product_id = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement itemPstmt = conn.prepareStatement(itemQuery);
				PreparedStatement sImagePstmt = conn.prepareStatement(sImageQuery);
				PreparedStatement oImagePstmt = conn.prepareStatement(oImageQuery)) {
			itemPstmt.setInt(1, product_id);
			sImagePstmt.setInt(1, product_id);
			oImagePstmt.setInt(1, product_id);
			itemPstmt.executeUpdate();
			sImagePstmt.executeUpdate();
			oImagePstmt.executeUpdate();
		}
	}

}
