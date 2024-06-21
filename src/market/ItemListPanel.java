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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lombok.Data;

@Data
class ItemListPanel extends JPanel implements ListSelectionListener {
	
	private MainFrame mContext;
	private PanelAdapter panelAdapter;
	
	private JList<ItemListDTO> listItemDTO;
	private DefaultListModel<ItemListDTO> model;
	private ItemRepoImpl itemRepoImpl;
	private List<ItemListDTO> itemListDTOs;
	private JPanel jPanel;
	private JScrollPane jsPane;
	private int product_id;
	ItemDetilPanel itemDetilPanel;

	public ItemListPanel(MainFrame mContext,PanelAdapter panelAdapter) {
		this.mContext = mContext;
		this.panelAdapter = panelAdapter;
		this.itemRepoImpl = mContext.getItemRepoImpl();
		initData();
		setInitLayout();
		try {
			itemListDTOs = itemRepoImpl.getItemDTO(0);
			for (ItemListDTO itemListDTO : itemListDTOs) {
				model.addElement(itemListDTO);
				listItemDTO.setCellRenderer(new ItemListDTORenderer());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  listItemDTO.addListSelectionListener(this);
	}

	public void upDateList() {
		// 아이템 리스트 업데이트 부분 수정완료
		// 기존 전체 모델 지우고 다시 불러오는 방법에서
		// 현재 표시되고 있는 마지막 아이템의 id를 기준으로 db에서 검색후
		// 새롭게 올라온 아이템만 업데이트 하는 방식
		// 발표에 이기능 설명 들어가야함

		int lastItemNum = model.get(0).getProductId();
		try {
			itemListDTOs = itemRepoImpl.getItemDTO(lastItemNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ItemListDTO itemListDTO : itemListDTOs) {
			model.add(0, itemListDTO);
			listItemDTO.setCellRenderer(new ItemListDTORenderer());
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		 if (!e.getValueIsAdjusting()) {  
	            ItemListDTO selectedItem = listItemDTO.getSelectedValue();
	            if (selectedItem != null) {
	            	product_id = selectedItem.getProductId();
	            	itemDetilPanel = new ItemDetilPanel(mContext.getMyUserDTO() ,product_id, itemRepoImpl,panelAdapter);
	                // 선택된 아이템을 여기서 처리합니다.
	                // TODO 상품 id  멤버변수에 저장 . //-> 사용할때마다 계속 생성 x , 덮어쓰는것.
	            	panelAdapter.addItemDetailPanel(itemDetilPanel);
	            }
	        }
	}

	@Data
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
