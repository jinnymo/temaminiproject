package market;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ItemDetilLayout extends JPanel {

	public ItemDetilLayout() {

		initData();
		setInitLayout();
	}

	private void initData() {
		setLayout(null);
		setSize(400, 500);
	}

	private void setInitLayout() {
		// 이미지 라벨들
		// 작은거 3장 큰거1장
		JLabel scaledImgLabel1 = createImageLabel(null);
		JLabel scaledImgLabel2 = createImageLabel(null);
		JLabel scaledImgLabel3 = createImageLabel(null);
		JLabel imageLabel = createImageLabel(null);

		// 이미지 라벨 위치 및 크기 설정
		scaledImgLabel1.setSize(100, 70);
		scaledImgLabel1.setLocation(0, 0);

		scaledImgLabel2.setSize(100, 70);
		scaledImgLabel2.setLocation(0, 70);

		scaledImgLabel3.setSize(100, 70);
		scaledImgLabel3.setLocation(0, 140);

		imageLabel.setSize(300, 210);
		imageLabel.setLocation(100, 0);

		// 텍스트 라벨들
		JLabel nameLabel = createTextLabel(null);
		nameLabel.setSize(360, 30);
		nameLabel.setLocation(20, 230);

		JLabel priceLabel = createTextLabel(null);
		priceLabel.setSize(360, 30);
		priceLabel.setLocation(20, 260);

		// 구분선 라벨
		JLabel separatorLabel = new JLabel();
		separatorLabel.setSize(360, 1);
		separatorLabel.setLocation(20, 290);
		separatorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// 텍스트 필드
		JTextArea descriptionField = createTextArea(null);
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

	private JLabel createImageLabel(String text) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 얇은 테두리 추가
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		return label;
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

}
