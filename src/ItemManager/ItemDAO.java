package ItemManager;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDAO {

	private JFileChooserLayout chooserLayout;
	private ItemBufferedImage image;

	public ItemDAO() {
		chooserLayout = new JFileChooserLayout();
	}

	public void addItem(ItemDTO dto) throws Exception {
		String query = " INSERT INTO item(product_name, price, state, product_info, img) VALUES(?,?,?,?,?) ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, dto.getProduct_name());
			pstmt.setInt(2, dto.getPrice());
			pstmt.setString(3, dto.getState());
			pstmt.setString(4, dto.getText());
			pstmt.setBytes(5, image.imageToByteArray(chooserLayout.getImagePath()));
		}
	}

	public void addItemImage() throws Exception {
		String query = " INSERT INTO imagetest(image) VALUES(?) ";
		try (Connection conn = DBConnationManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			System.out.println(image.imageToByteArray(chooserLayout.getImagePath().toString()));
//			pstmt.setBytes(1, image.imageToByteArray(chooserLayout.getImagePath()));
//			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ItemDTO selectItem() {
		String qurey = " SELECT * FROM item WHERE = ? ";

		return null;
	}
}
