package market;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class testItem extends JPanel{
	Image[] bigArr = new Image[3];
	Image[] smallArr = new Image[3];
	public static void main(String[] args) {
		new testItem();
		
	}
	public testItem() {
		try {
			testInsetitem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testInsetitem() throws SQLException {
		int product_id = 0;
		getScaledIcon("img/image1.png", 0);
		getScaledIcon("img/image2.png", 1);
		getScaledIcon("img/image3.png", 2);

		List<byte[]> big = new ArrayList<>();
		List<byte[]> small = new ArrayList<>();
		
		System.out.println(bigArr.length);
		for (int i = 0; i < 3; i++) {
			big.add(toByte(bigArr[i]));
			small.add(toByte(smallArr[i]));
		}
		for (int t = 4; t < 100; t++) {
			String query = " INSERT INTO item (product_name, price, state, date, product_info, user_num, category_id) "
					+ "	VALUES(?, ?, ?, now(), ?, ?, ?) ";
			String productidQuery = " SELECT product_id from item WHERE user_num = 3 order by date desc limit 1 ";
			String bImagequery = " INSERT INTO original_item_image (product_id, image, image_num) VALUES(?, ?, ?) ";
			String sImagequery = " INSERT INTO scaled_item_image (product_id, image, image_num) VALUES(?, ?, ?) ";
			try (Connection conn = DBConnectionManager.getInstance().getConnection();
					PreparedStatement userPstmt = conn.prepareStatement(query);
					PreparedStatement bIamgePstmt = conn.prepareStatement(bImagequery);
					PreparedStatement sImagePstmt = conn.prepareStatement(sImagequery);
					PreparedStatement productIdPstmt = conn.prepareStatement(productidQuery)) {

				userPstmt.setString(1, "테스트");
				userPstmt.setString(2, "1000");
				userPstmt.setString(3, "테스트 중");
				userPstmt.setString(4, "테스트 아이템 입니다.");
				userPstmt.setInt(5, t);
				userPstmt.setInt(6, 1);
				userPstmt.executeUpdate();
				ResultSet set = productIdPstmt.executeQuery();
				if (set.next()) {
					product_id = set.getInt("product_id");

				}

				for (int k = 0; k < 3; k++) {
					bIamgePstmt.setInt(1, product_id);
					bIamgePstmt.setBytes(2, big.get(k));
					bIamgePstmt.setInt(3, k + 1);
					bIamgePstmt.executeUpdate();

					sImagePstmt.setInt(1, product_id);
					sImagePstmt.setBytes(2, small.get(k));
					sImagePstmt.setInt(3, k + 1);
					sImagePstmt.executeUpdate();
				}
			}
		}

	}

	public void getScaledIcon(String imagepath, int index) {
		ImageIcon tempImg = new ImageIcon(imagepath);
		Image smallImage = tempImg.getImage().getScaledInstance(100, 70, Image.SCALE_FAST);
		Image bigImage = tempImg.getImage().getScaledInstance(300, 210, Image.SCALE_FAST);
		bigArr[index] = bigImage;
		smallArr[index] = smallImage;

	}

	// null point 예상
	private byte[] toByte(Image image) {
		System.out.println(image.toString());
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();

	}

}
