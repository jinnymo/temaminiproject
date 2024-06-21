package market;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ItemDetilPanel extends JPanel {

	private MainFrame mContext;
	private UserDTO userDTO;
	private ItemRepoImpl itemRepoImpl;
	private ItemDetailDTO itemDetailDTO;
	private String product_name;

	private JLabel scaledImgLabel1;
	private JLabel scaledImgLabel2;
	private JLabel scaledImgLabel3;
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel priceLabel;
	private JTextArea descriptionField;
	private JLabel separatorLabel;

	private ImageIcon bigIcon;
	private ImageIcon scaledicon;

	private ImageIcon[] bigimageIconArr = new ImageIcon[3];
	private ImageIcon[] smallImageIconArr = new ImageIcon[3];

	private int product_id;

	public ItemDetilPanel(MainFrame mContext) {
		this.mContext = mContext;

		userDTO = mContext.getMyUserDTO();
		itemRepoImpl = mContext.getItemRepoImpl();
		try {
			itemDetailDTO = itemRepoImpl.getItemDetailDTO(product_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initData();
		setInitLayout();
		addEventLisener();
	}

	private void initData() {
		setLayout(null);
		setSize(400, 500);

		bigIcon = new ImageIcon();
		scaledicon = new ImageIcon();
		scaledImgLabel1 = new JLabel(scaledicon);
		scaledImgLabel2 = new JLabel(scaledicon);
		scaledImgLabel3 = new JLabel(scaledicon);
		imageLabel = new JLabel(bigIcon);

		priceLabel = new JLabel();
		nameLabel = new JLabel();
		separatorLabel = new JLabel();
		descriptionField = new JTextArea();

		for (int i = 0; i < 3; i++) {
			byte[] tempSmall = itemDetailDTO.getSImageList().get(i);
			byte[] tempbig = itemDetailDTO.getBImageList().get(i);

			try {
				smallImageIconArr[i] = byteArrayToImageIcon(tempSmall);
				bigimageIconArr[i] = byteArrayToImageIcon(tempbig);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void setInitLayout() {
		// 받아야할 목록 - 이미지 6장, 사용자이름, 상품이름, 상태, 상품설명
		// 이미지 라벨들
		// 작은거 3장 큰거3장 -> 배열

		// 이미지 라벨 위치 및 크기 설정
		scaledImgLabel1.setIcon(smallImageIconArr[0]);
		scaledImgLabel1.setSize(100, 70);
		scaledImgLabel1.setLocation(0, 0);

		scaledImgLabel2.setIcon(smallImageIconArr[1]);
		scaledImgLabel2.setSize(100, 70);
		scaledImgLabel2.setLocation(0, 70);

		scaledImgLabel3.setIcon(smallImageIconArr[2]);
		scaledImgLabel3.setSize(100, 70);
		scaledImgLabel3.setLocation(0, 140);

		imageLabel.setSize(300, 210);
		imageLabel.setLocation(100, 0);

		// 텍스트 라벨들
		nameLabel.setText(itemDetailDTO.getProduct_name());
		nameLabel.setSize(360, 30);
		nameLabel.setLocation(20, 230);

		priceLabel.setText(itemDetailDTO.getPrice());
		priceLabel.setSize(360, 30);
		priceLabel.setLocation(20, 260);

		// 구분선 라벨
		separatorLabel.setSize(360, 1);
		separatorLabel.setLocation(20, 290);
		separatorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// 텍스트 필드
		descriptionField.setText(itemDetailDTO.getProduct_info());
		descriptionField.setSize(360, 60);
		descriptionField.setLocation(20, 300);

		// 컴포넌트를 패널에 추가
		add(scaledImgLabel1);
		add(scaledImgLabel2);
		add(scaledImgLabel3);
		add(imageLabel);
		add(nameLabel);
		add(priceLabel);
		add(separatorLabel);
		add(descriptionField);
	}

	private void addEventLisener() {

	}

	private JLabel createTextLabel(String text) {
		JLabel label = new JLabel(text, SwingConstants.LEFT);
		label.setOpaque(false); // 백그라운드 없앰
		return label;
	}

	private JTextArea createTextArea(String text) {
		JTextArea textArea = new JTextArea(text);
		textArea.setLineWrap(true); // 자동 줄바꿈 설정
		textArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈
		textArea.setOpaque(false); // 배경색 없앰
		textArea.setEditable(false); // 값 입력 불가하게 설정
		textArea.setBorder(BorderFactory.createEmptyBorder()); // 테두리 없앰
		return textArea;
	}

	public ImageIcon byteArrayToImageIcon(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		BufferedImage bImage = ImageIO.read(bis);
		bis.close();
		Image image = bImage.getScaledInstance(bImage.getWidth(), bImage.getHeight(), Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}

}
