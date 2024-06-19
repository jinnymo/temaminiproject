package ItemManager.last;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import lombok.Data;
import market.DBConnectionManager;
import market.ItemDTO;

@Data
public class ItemDAO {

	private ItemBufferedImage image;
	private BufferedImage bfimage;

	public ItemDAO(ItemJListLayout context) {
		image = new ItemBufferedImage(context);
	}

	public void addItem(ItemDTO dto) throws Exception {
		String query = " INSERT INTO item(product_name, price, state, product_info, img) VALUES(?,?,?,?,?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, dto.getProduct_name());
			pstmt.setInt(2, dto.getPrice());
			pstmt.setString(3, dto.getState());
			pstmt.setString(4, dto.getText());
			pstmt.setBytes(5, image.imageToByteArray());
		}
	}

	public void addItemImage() throws Exception {
		String query = " INSERT INTO imagetest(image) VALUES(?) ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setBytes(1, image.imageToByteArray()); // sql에 이미지 저장
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void imageSelectItem() {
		String query = " SELECT image FROM imagetest where id = 1 ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Blob blob = rs.getBlob("image");
			int blobLength = (int) blob.length();

			byte[] bytes = blob.getBytes(1, blobLength);
			blob.free(); // blob 리소스 해제
			bfimage = ImageIO.read(new ByteArrayInputStream(bytes));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
