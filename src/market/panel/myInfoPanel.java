package market.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import market.MainFrame;
import market.Resource;
import market.TextHint;
import market.DTO.ItemListDTO;
import market.repoIm.ItemRepoImpl;
import market.repoIm.UserInfoRepoIm;

public class myInfoPanel extends JPanel implements ActionListener, ListSelectionListener {

	private MainFrame mContext;
//	private PanelAdapter panelAdapter;
	private UserInfoRepoIm userInfoRepoIm;
	private ItemRepoImpl itemRepoImpl;

	private JList<ItemListDTO> listItemDTO;
	private DefaultListModel<ItemListDTO> model;
	private JButton reviceBtn; // 수정 버튼
	private JLabel reviceLbl; // "회원정보 수정" 라벨
	private JLabel reviceNameLbl; // "이름 수정" 라벨
	private JLabel revicePassLbl; // "비밀번호 수정" 라벨
	private JPasswordField revicePassText; // 비밀번호 입력 필드
	private JTextField reviceNameText; // 이름 입력 필드

	private JButton updateBtn;
	private JButton deleteBtn;
	private JScrollPane jScrollPane;

	private int currentPage = 0;
	private static final int PAGE_SIZE = 20;

	private int product_id;

	public myInfoPanel(MainFrame mContext, PanelAdapter panelAdapter) {
		this.mContext = mContext;
//		this.panelAdapter = panelAdapter;
		this.userInfoRepoIm = new UserInfoRepoIm();
		this.itemRepoImpl = mContext.getItemRepoImpl();
		initData();
		setInitLayout();
		addEventLisetener();
		loadItems(currentPage);

		jScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (!e.getValueIsAdjusting()
					&& e.getValue() + e.getAdjustable().getVisibleAmount() >= e.getAdjustable().getMaximum()) {
				currentPage++;
				loadItems(currentPage);
			}
		});
	}

	private void loadItems(int page) {
		new SwingWorker<List<ItemListDTO>, Void>() {

			@Override
			protected void done() {
				try {
					List<ItemListDTO> items = get();
					System.out.println(items.size());
					for (ItemListDTO itemListDTO : items) {
						System.out.println("123");
						model.addElement(itemListDTO);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			protected List<ItemListDTO> doInBackground() throws Exception {
				int user_num = mContext.getMyUserDTO().getUser_num();
				return itemRepoImpl.getUserItemDTO(user_num, page * PAGE_SIZE, PAGE_SIZE);
			}
		}.execute();
	}

	private void initData() {
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0, 50);

		reviceBtn = new JButton("수정");
		reviceLbl = new JLabel("회원정보 수정");
		reviceNameLbl = new JLabel("이름 수정");
		revicePassLbl = new JLabel("비밀번호 수정");

		updateBtn = new JButton("상품 수정");
		deleteBtn = new JButton("상품 삭제");

		model = new DefaultListModel<>();
		listItemDTO = new JList<>(model);
		listItemDTO.setCellRenderer(new ItemListDTORenderer());

		jScrollPane = new JScrollPane(listItemDTO);

		reviceNameText = new JTextField();
		new TextHint(reviceNameText, "이름 입력");
		revicePassText = new JPasswordField();
		new TextHint(revicePassText, "비밀번호 입력");

		reviceLbl.setSize(100, 100);
		reviceLbl.setLocation(10, 10);

		reviceBtn.setSize(100, 100);
		reviceBtn.setLocation(280, 10);

		reviceNameLbl.setSize(100, 25);
		reviceNameLbl.setLocation(110, 10);

		reviceNameText.setSize(150, 25);
		reviceNameText.setLocation(110, 35);
		reviceNameText.setBackground(Color.white);

		revicePassLbl.setSize(100, 25);
		revicePassLbl.setLocation(110, 60);

		revicePassText.setSize(150, 25);
		revicePassText.setLocation(110, 85);
		revicePassText.setBackground(Color.white);

		jScrollPane.setSize(380, 300);
		jScrollPane.setLocation(10, 150);

		updateBtn.setSize(100, 35);
		updateBtn.setLocation(180, 455);

		deleteBtn.setSize(100, 35);
		deleteBtn.setLocation(285, 455);

	}

	private void setInitLayout() {
		setBackground(Color.gray);
		setLayout(null); // 절대 레이아웃 사용
		add(reviceLbl);
		add(reviceBtn);
		add(reviceNameLbl);
		add(reviceNameText);
		add(revicePassLbl);
		add(revicePassText);
		add(jScrollPane);
		add(updateBtn);
		add(deleteBtn);
	}

	private void addEventLisetener() {
		reviceBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		listItemDTO.addListSelectionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source == reviceBtn) {
			String name = reviceNameText.getText();
			String Password = new String(revicePassText.getPassword());
			System.out.println(Password);
			String userId = mContext.getMyUserDTO().getUser_id();
			try {
				userInfoRepoIm.updateUserInfo(name, Password, userId);
				Resource.MsgDialog("사용자 정보 변경 !!");
				reviceNameText.setText("");
				revicePassText.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (source == updateBtn) {
			// addItemPanel 띄우고 저장된 product_name, price, categoryId, content, 이미지 띄우기
			upDateList();
		} else if (source == deleteBtn) {
			try {
				System.out.println(product_id);
				userInfoRepoIm.deleteItem(product_id);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			upDateList();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			ItemListDTO selectItem = listItemDTO.getSelectedValue();
			if (selectItem != null) {
				product_id = selectItem.getProductId();
			}
		}
	}

	private class ItemListDTORenderer extends JPanel implements ListCellRenderer<ItemListDTO> {

		private JLabel lbIcon = new JLabel();
		private JLabel lbName = new JLabel();
		private JLabel lbPrice = new JLabel();
		private JPanel panelText;
		private JPanel panelIcon;
		private ImageIcon icon = null;

		public ItemListDTORenderer() {
			setLayout(new BorderLayout(5, 5));

			panelText = new JPanel(new GridLayout(0, 1));
			panelText.add(lbName);
			panelText.add(lbPrice);

			panelIcon = new JPanel();
			panelIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
			panelIcon.add(lbIcon);

			add(panelIcon, BorderLayout.WEST);
			add(panelText, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemListDTO> list, ItemListDTO itemListDTO,
				int index, boolean isSelected, boolean cellHasFocus) {
			try {
				icon = byteArrayToImageIcon(itemListDTO.getImage());
			} catch (IOException e) {
				e.printStackTrace();
				icon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // 기본 아이콘
			}

			lbIcon.setIcon(icon);
			lbName.setText(itemListDTO.getProductName());

			lbPrice.setText(itemListDTO.getPrice());
			lbPrice.setForeground(Color.blue);

			lbName.setOpaque(true);
			lbPrice.setOpaque(true);
			lbIcon.setOpaque(true);

			if (isSelected) {
				lbName.setBackground(list.getSelectionBackground());
				lbPrice.setBackground(list.getSelectionBackground());
				lbIcon.setBackground(list.getSelectionBackground());
				setBackground(list.getSelectionBackground());
			} else {
				lbName.setBackground(list.getBackground());
				lbPrice.setBackground(list.getBackground());
				lbIcon.setBackground(list.getBackground());
				setBackground(list.getBackground());
			}
			return this;
		}

		public ImageIcon byteArrayToImageIcon(byte[] bytes) throws IOException {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage bImage = ImageIO.read(bis);
			bis.close();
			Image image = bImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 적절한 크기로 조정
			return new ImageIcon(image);
		}
	}

	public void upDateList() {
		model.clear();
		currentPage = 0;
		loadItems(currentPage);
	}
}
