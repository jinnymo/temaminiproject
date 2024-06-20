package ItemManager.last;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import lombok.Data;
import lombok.ToString;
import market.ItemDTO;

@Data
@ToString
public class ItemJListLayout extends JFrame {

	private int width = 350;
	private int height = 350;
	private JList<ItemDTO> listItme;
	private BufferedImage image;
	private JPanel panel;
	private JLabel label;
	private JPanel btnPanel;
	private JButton button;
	private JButton button2;
//	private JFileChooserLayout chooserLayout;

	// File Path ---
	private JFileChooser chooser = new JFileChooser();
	// 확장자 필터
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
	private String imagePath;
	private File fileUrl;
	private ItemDAO dao;

	public ItemJListLayout() {
		initData();
		setInitLayout();
		addEventListener();

		dao = new ItemDAO(this);
	}

	private void initData() {
		button = new JButton();
		button2 = new JButton();
		button2.setText("이미지");
		label = new JLabel();
		panel = new JPanel();
		btnPanel = new JPanel();
//		chooserLayout = new JFileChooserLayout();
	}

	private void setInitLayout() {
		//add(createMainPanel());
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLayout(null);
		//setLocation(0,0);

		panel.setLayout(null);
		
		label.setSize(100,100);
		panel.setSize(100,100);
		
		label.setLocation(0, 0);
		panel.setLocation(0, 0);
		
		panel.setVisible(true);
		panel.setBackground(Color.black);
		//panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		//panel.add(label);
		btnPanel.add(button.add(printFilePath()));
		btnPanel.add(button2);
		//panel.add(btnPanel, BorderLayout.SOUTH);
		//add(btnPanel, BorderLayout.SOUTH);
		add(panel);
		//add(label);
	}

	private JList<ItemDTO> createList() {

		DefaultListModel<ItemDTO> model = new DefaultListModel<>();
		// TODO system.in 으로 값받기 item_name, price, 이미지(borwserBox)
		// ? ? ?
		// SELECT username, price, image FROM item
		// resultset !isLast
//		model.addElement(new ItemDTO("C/C++ Programming", "A", "cpp"));
//		model.addElement(new ItemDTO("Java Programming", "B", "java"));
//		model.addElement(new ItemDTO("C# Programming", "C", "cs"));
//		model.addElement(new ItemDTO("IOS Programming", "D", "ios"));
//		model.addElement(new ItemDTO("Windows Phone Programming", "E", "wp"));
//		model.addElement(new ItemDTO("Android Programming", "F", "android"));
		JList<ItemDTO> list = new JList<ItemDTO>(model);
		list.setCellRenderer(new ItemRendererPanel(this));
		return list;
	}

	private void createMainPanel() {
	
		//return panel;
	}

	private void addEventListener() {
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dao.imageSelectItem();
			}
		});
	}


	private class ItemRendererPanel extends JPanel implements ListCellRenderer<ItemDTO> {

		private JLabel lbIcon = new JLabel();
		private JLabel lbName = new JLabel();
		private JLabel lbAuthor = new JLabel();
		private JPanel panelText;
		private JPanel panelIcon;
		private BufferedImage image;

		public ItemRendererPanel(ItemJListLayout context) {
			setLayout(new BorderLayout(5, 5));

			JPanel panelText = new JPanel(); //(new GridLayout(0, 1));
			panelText.add(lbName);
			// price
			panelText.add(lbAuthor);
			context.add(lbIcon, BorderLayout.WEST);
			add(panelText, BorderLayout.CENTER);
			lbIcon.setBackground(Color.black);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemDTO> list, ItemDTO value, int index,
				boolean isSelected, boolean cellHasFocus) {
			ImageIcon ic = new ImageIcon(dao.getBfimage());
			
//			JLabel image = (Icon) dao.getBfimage();
			lbIcon.setIcon(ic); // resultSet.getImage
//			lbName.setText(ItemDTO.getPrice_name); // resultSet.getName
//			lbAuthor.setText(ItemDTO.getPirce()); // resultSet.getPrice

			// 선택시 배경 불투명도 true로 해둬야 선택을 확인가능
			lbIcon.setOpaque(true);
			lbAuthor.setOpaque(true);
			lbName.setOpaque(true);

			// 선택 체크
			if (isSelected) {
				lbName.setBackground(list.getSelectionBackground());
				lbAuthor.setBackground(list.getSelectionBackground());
				lbIcon.setBackground(list.getSelectionBackground());
				setBackground(list.getSelectionBackground());
			} else {
				lbName.setBackground(list.getBackground());
				lbAuthor.setBackground(list.getBackground());
				lbIcon.setBackground(list.getBackground());
				setBackground(list.getBackground());
			}

			return this;
		}

	}
}