package ItemManager;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ItemBufferedImage {

	private JFileChooserLayout chooserLayout;
	private File fileUrl = null;
	private byte[] returnValue = null;

	public ItemBufferedImage() {
		chooserLayout = new JFileChooserLayout();
	}

	public byte[] imageToByteArray(String imagePath) throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedInputStream bf = new BufferedInputStream(new FileInputStream(chooserLayout.getImagePath()))) {
			byte[] buffer = new byte[1024];
			int read = 0;

			while ((read = bf.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}
			returnValue = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.getDecoder().decode(returnValue);
	}

	public void imageOutputBuffer(Connection conn, String query) throws IOException {
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			BufferedImage im = ImageIO.read(rs.getBinaryStream("Image"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
