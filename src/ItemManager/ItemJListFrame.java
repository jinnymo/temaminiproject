package ItemManager;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ItemJListFrame extends JFrame {

	private int width = 350;
	private int height = 350;
	private JList<Itme> listItme;
	private BufferedImage image;
	private JPanel panel;
	private JButton button;
	private JFileChooserLayout chooserLayout;

	public ItemJListFrame() {
		initData();
		setInitLayout();
	}

	private void initData() {
		button = new JButton();
		chooserLayout = new JFileChooserLayout();
		button.setText("browser");
		button.add(chooserLayout.printFilePath());
	}

	private void setInitLayout() {
		add(createMainPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private JList<Itme> createList() {

		DefaultListModel<Itme> model = new DefaultListModel<>();
		// TODO system.in 으로 값받기 item_name, price, 이미지(borwserBox)
		// ? ? ?
		// SELECT username, price, image FROM item
		// resultset !isLast
		model.addElement(new Itme("C/C++ Programming", "A", "cpp"));
		model.addElement(new Itme("Java Programming", "B", "java"));
		model.addElement(new Itme("C# Programming", "C", "cs"));
		model.addElement(new Itme("IOS Programming", "D", "ios"));
		model.addElement(new Itme("Windows Phone Programming", "E", "wp"));
		model.addElement(new Itme("Android Programming", "F", "android"));
		JList<Itme> list = new JList<Itme>(model);
		list.setCellRenderer(new ItemRendererPanel());
		return list;
	}

	private JPanel createMainPanel() {
		panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(new JScrollPane(listItme = createList()), BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		return panel;
	}
}