package ItemManager.last;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class ItemBufferedImage {

	private ItemJListLayout chooser;

	private byte[] returnValue = null;

	public ItemBufferedImage(ItemJListLayout context) {
		this.chooser = context;
	}

	public byte[] imageToByteArray() throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedInputStream bf = new BufferedInputStream(new FileInputStream(chooser.getImagePath()))) {
			byte[] buffer = new byte[1024];
			int read = 0;

			while ((read = bf.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}
			returnValue = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

}
