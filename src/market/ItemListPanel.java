package market;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

class ItemListPanel extends JPanel {
	private MainFrame mContext;
	private JList<ItemListDTO> listItemDTO;
	private DefaultListModel<ItemListDTO> model;
	private ItemRepoImpl itemRepoImpl;
	private List<ItemListDTO> itemListDTOs;
	private JPanel jPanel;
	private JScrollPane jsPane;

	public ItemListPanel(MainFrame mContext) {
		this.mContext = mContext;
		this.itemRepoImpl = mContext.getItemRepoImpl();
		initData();
		setInitLayout();
		try {
			itemListDTOs = itemRepoImpl.getItemDTO();
			for (ItemListDTO itemListDTO : itemListDTOs) {
				model.addElement(itemListDTO);
				listItemDTO.setCellRenderer(new ItemListDTORenderer());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initData() {
		setLayout(null);
		setSize(400, 500);
		setLocation(0, 50);
		setVisible(true);

	}

	private void setInitLayout() {

		add(jsPane = new JScrollPane(listItemDTO = createListItemDTO()));
		jsPane.setSize(400, 500);
		jsPane.setLocation(0, 0);
	}

	private JList<ItemListDTO> createListItemDTO() {
		// TODO 커스텀 예정
		model = new DefaultListModel<>();
		JList<ItemListDTO> list = new JList<ItemListDTO>(model);

		return list;
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

			JPanel panelText = new JPanel(new GridLayout(0, 1));
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
			}

			lbIcon.setIcon(icon);
			lbName.setText(itemListDTO.getProductName());

			lbPrice.setText(itemListDTO.getPrice());
			lbPrice.setForeground(Color.blue);

			// set Opaque to change background color of JLabel
			lbName.setOpaque(true);
			lbPrice.setOpaque(true);
			lbIcon.setOpaque(true);

			// 선택시 배경 불투명도 true로 해둬야 선택을 확인가능
			lbIcon.setOpaque(true);
			lbPrice.setOpaque(true);
			lbName.setOpaque(true);

			// 선택 체크
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

	}

	public ImageIcon byteArrayToImageIcon(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		BufferedImage bImage = ImageIO.read(bis);
		bis.close();
		Image image = bImage.getScaledInstance(bImage.getWidth(), bImage.getHeight(), Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}

}
