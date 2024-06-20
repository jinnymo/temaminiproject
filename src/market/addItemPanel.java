package market;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class addItemPanel extends JPanel implements Runnable {

	private LabelMouseListener labelMouseListener;

	private ImageIcon bigicon;
	private ImageIcon scaledicon;
	
	private JLabel imageLabel;
	private JTextField titleTF;
	private JTextField priceTF;
	private JTextField contentTF;
	private JButton submitBtn;

	private JLabel scaledImgLabel1;
	private JLabel scaledImgLabel2;
	private JLabel scaledImgLabel3;
	
	private JComboBox<String> categoryBox;

	private JFileChooser fileSelecter = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
	// 재사용 불가능 todo!! [ imagePaht have to make three instance each name for
	// imagePath1 ,imagePath2,imagePath3]

	// 이거 3개는 서버 올리고 전부 다시null 로 해줘야 함 !!!!
	private String imagePath1 = null;
	private String imagePath2 = null;
	private String imagePath3 = null;
	
	//이거도 올리고 null 처리 해야함!!
	private ImageIcon[] bigimageIconArr = new ImageIcon[3];
	// 재사용 가능
	private File fileUrl = null;

	public addItemPanel() {
		categoryBox = new JComboBox<>();
		categoryBox.addItem("의류");
		categoryBox.addItem("전자기기");
		categoryBox.addItem("도서");
		categoryBox.addItem("가구");
		categoryBox.addItem("스포츠");
		
		
		bigicon = new ImageIcon("img/show_big_img.png");
		scaledicon = new ImageIcon("img/add_img.png");
		imageLabel = new JLabel(bigicon);
		submitBtn = new JButton("상품 등록하기");

		// todo -!! 힌트로변경 하
		titleTF = new JTextField("제목 입력");
		priceTF = new JTextField("가격 입력");
		contentTF = new JTextField("내용 입력");
		
		
		scaledImgLabel1 = new JLabel(scaledicon);
		scaledImgLabel2 = new JLabel(scaledicon);
		scaledImgLabel3 = new JLabel(scaledicon);

		labelMouseListener = new LabelMouseListener();
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0, 50);

		imageLabel.setSize(300, 210);
		imageLabel.setLocation(100, 0);

		submitBtn.setSize(400, 50);
		submitBtn.setLocation(0, 450);

		titleTF.setSize(200, 30);
		titleTF.setLocation(0, 210);
		
		priceTF.setSize(200, 30);
		priceTF.setLocation(200, 210);
		
		contentTF.setSize(400,180);
		contentTF.setLocation(0,270);
		
		scaledImgLabel1.setSize(100, 70);
		scaledImgLabel2.setSize(100, 70);
		scaledImgLabel3.setSize(100, 70);

		scaledImgLabel1.setLocation(0, 0);
		scaledImgLabel2.setLocation(0, 70);
		scaledImgLabel3.setLocation(0, 140);
		
		categoryBox.setSize(400,30);
		categoryBox.setLocation(0, 240);

		// 파일 검색창에 표시할 확장자명 필터 거는 부분 아마도???
		fileSelecter.setFileFilter(filter);
	}

	private void setInitLayout() {
		setLayout(null);
		add(imageLabel);
		add(submitBtn);
		add(titleTF);
		add(priceTF);
		add(scaledImgLabel1);
		add(scaledImgLabel2);
		add(scaledImgLabel3);
		add(categoryBox);
		add(contentTF);
		// 레이아웃 재검토
		revalidate();
		repaint();

	}

	private void addEventListener() {
		// TODO Auto-generated method stub

		imageLabel.addMouseListener(labelMouseListener);

		scaledImgLabel1.addMouseListener(labelMouseListener);
		scaledImgLabel2.addMouseListener(labelMouseListener);
		scaledImgLabel3.addMouseListener(labelMouseListener);
	}

	@Override
	public void run() {
		

	}

	class LabelMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (e.getSource() == scaledImgLabel1) {
				imagePath1 = getImagePath();
				scaledImgLabel1.setIcon(getScaledIcon(imagePath1,0));
			}else if (e.getSource() == scaledImgLabel2) {
				imagePath2 = getImagePath();
				scaledImgLabel2.setIcon(getScaledIcon(imagePath2,1));
			}else if (e.getSource() == scaledImgLabel3) {
				imagePath3 = getImagePath();
				scaledImgLabel3.setIcon(getScaledIcon(imagePath3,2));
			}

		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == scaledImgLabel1) {
				imageLabel.setIcon(bigimageIconArr[0]);
			}else if (e.getSource() == scaledImgLabel2) {
				imageLabel.setIcon(bigimageIconArr[1]);
			}else if (e.getSource() == scaledImgLabel3) {
				imageLabel.setIcon(bigimageIconArr[2]);
			}
		}
	}

	public String getImagePath() {
		int tempCount = fileSelecter.showOpenDialog(getParent());
		if (tempCount == JFileChooser.APPROVE_OPTION) {
			fileUrl = fileSelecter.getSelectedFile();
			String imagePath;
			imagePath = fileUrl.toString();
			ImageIcon tempImg = new ImageIcon(imagePath);

			return imagePath;
		}
		return null;
	}

	public ImageIcon getScaledIcon(String imagepath, int index) {
		ImageIcon tempImg = new ImageIcon(imagepath);
		Image smallImage = tempImg.getImage().getScaledInstance(100, 70, Image.SCALE_FAST);
		Image bigImage = tempImg.getImage().getScaledInstance(300, 210, Image.SCALE_FAST);
		bigimageIconArr[index] = new ImageIcon(bigImage);
		return new ImageIcon(smallImage);
	}

}
