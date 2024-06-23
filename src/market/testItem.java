package market;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class testItem extends JPanel {
	Image[] bigArr = new Image[3];
	Image[] smallArr = new Image[3];
	private int repeatCount;  // 반복 횟수 저장 변수 추가

	// DBConnectionManager 및 userIdQuery는 아래 예시로 추가된 부분입니다.
	private static final String userIdQuery = "SELECT user_num FROM user"; // 실제 쿼리에 맞게 수정 필요

	public static void main(String[] args) {
		// 생성자에 반복 횟수를 전달
		new testItem(100);
		System.out.println("생성 끝");
	}

	// 생성자에서 반복 횟수 받기
	public testItem(int repeatCount) {
		this.repeatCount = repeatCount;
		try {
			testInsetitem();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void testInsetitem() throws SQLException {
		int product_id = 0;
		if (!getScaledIcon("img/image1.png", 0) || !getScaledIcon("img/image2.png", 1)
				|| !getScaledIcon("img/image3.png", 2)) {
			System.err.println("이미지 로딩 실패");
			return;
		}

		List<byte[]> big = new ArrayList<>();
		List<byte[]> small = new ArrayList<>();
		List<Integer> usernum = new ArrayList<>();

		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement userNumPstmt = conn.prepareStatement(userIdQuery);
				ResultSet rs = userNumPstmt.executeQuery()) {

			while (rs.next()) {
				usernum.add(rs.getInt("user_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 3; i++) {
			byte[] bigImageBytes = toByte(bigArr[i]);
			byte[] smallImageBytes = toByte(smallArr[i]);
			if (bigImageBytes == null || smallImageBytes == null) {
				System.err.println("이미지를 바이트 배열로 변환하는데 실패했습니다.");
				return;
			}
			big.add(bigImageBytes);
			small.add(smallImageBytes);
		}

		for (Integer userNum : usernum) {
			for (int j = 0; j < repeatCount; j++) {  // 반복 횟수 조절
				String query = "INSERT INTO item (product_name, price, state, date, product_info, user_num, category_id) VALUES(?, ?, ?, now(), ?, ?, ?)";
				String productidQuery = "SELECT product_id FROM item WHERE user_num = ? ORDER BY date DESC LIMIT 1";
				String bImagequery = "INSERT INTO original_item_image (product_id, image, image_num) VALUES(?, ?, ?)";
				String sImagequery = "INSERT INTO scaled_item_image (product_id, image, image_num) VALUES(?, ?, ?)";

				try (Connection conn = DBConnectionManager.getInstance().getConnection();
						PreparedStatement userPstmt = conn.prepareStatement(query);
						PreparedStatement bIamgePstmt = conn.prepareStatement(bImagequery);
						PreparedStatement sImagePstmt = conn.prepareStatement(sImagequery);
						PreparedStatement productIdPstmt = conn.prepareStatement(productidQuery)) {

					userPstmt.setString(1, "테스트");
					userPstmt.setString(2, "1000");
					userPstmt.setString(3, "테스트 중");
					userPstmt.setString(4, "테스트 아이템 입니다.");
					userPstmt.setInt(5, userNum);
					userPstmt.setInt(6, 1);
					userPstmt.executeUpdate();

					productIdPstmt.setInt(1, userNum);
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
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean getScaledIcon(String imagepath, int index) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(imagepath));
			if (originalImage == null) {
				System.err.println("이미지 로딩 실패: " + imagepath);
				return false;
			}
			Image smallImage = originalImage.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
			Image bigImage = originalImage.getScaledInstance(300, 210, Image.SCALE_SMOOTH);
			bigArr[index] = bigImage;
			smallArr[index] = smallImage;
			return true;
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			return false;
		}
	}

	private byte[] toByte(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("이미지가 null입니다.");
		}
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("이미지의 너비와 높이가 유효하지 않습니다: " + width + "x" + height);
		}

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return baos.toByteArray();
	}
}
