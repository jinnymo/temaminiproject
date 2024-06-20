package market;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// 아이템 정보를 담는 클래스
class Item {
	private ImageIcon imageIcon;
	private String itemName;
	private int price;

	public Item(ImageIcon imageIcon, String itemName, int price) {
		this.imageIcon = imageIcon;
		this.itemName = itemName;
		this.price = price;

	}

	// 각 요소의 정보를 문자열로 표현
	@Override
	public String toString() {
		return "Item{" + "itemName='" + itemName + '\'' + ", price=" + price + '}';
	}

	// getter 메서드들
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public String getItemName() {
		return itemName;
	}

	public int getPrice() {
		return price;
	}
}

// 각 아이템을 표시할 JPanel
class ItemPanel extends JPanel {
	private JLabel itemNameLabel;
	private JLabel priceLabel;
	private JLabel imageLabel;

	public void MainFrame() {
		setSize(600, 400);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ItemListPanel itemListPanel = new ItemListPanel();
		add(itemListPanel);
		setVisible(true);
	}

	public ItemPanel(Item item) {
		setLayout(new BorderLayout());

		// 이미지 레이블
		imageLabel = new JLabel();
		imageLabel.setIcon(item.getImageIcon());
		add(imageLabel, BorderLayout.WEST);

		// 텍스트 패널
		JPanel panelText = new JPanel(new GridLayout(2, 1));
		itemNameLabel = new JLabel(item.getItemName());
		panelText.add(itemNameLabel);

		priceLabel = new JLabel("$" + item.getPrice());
		panelText.add(priceLabel);

		add(panelText, BorderLayout.CENTER);
	}
}

// 목록을 담당하는 패널
class ItemListPanel extends JPanel {
	private JList<Item> itemList;

	public ItemListPanel() {
		setLayout(new BorderLayout());

		// 아이템 리스트 생성
		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item(new ImageIcon("product1.jpg"), "물건1", 10000));
		items.add(new Item(new ImageIcon("product2.jpg"), "물건2", 20000));
		items.add(new Item(new ImageIcon("product3.jpg"), "물건3", 30000));

		itemList = new JList<>(items.toArray(new Item[0]));
		itemList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				ItemPanel itemPanel = new ItemPanel((Item) value);
				itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // 보더 추가
				return itemPanel;
			}
		});

		JScrollPane scrollPane = new JScrollPane(itemList);
		add(scrollPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame());
	}
}
