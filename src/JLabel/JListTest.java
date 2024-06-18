package JLabel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class JListTest extends JFrame {

	private int width = 350;
	private int height = 350;
	private JList<Book> listBook;
	private BufferedImage image;
	private JPanel panel;

	public JListTest() {
		setInitLayout();
	}

	private void setInitLayout() {
		add(createMainPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private JList<Book> createList() {

		DefaultListModel<Book> model = new DefaultListModel<>();
		// TODO system.in 으로 값받기 item_name, price, 이미지(borwserBox)
		// 									?			?	  ?
		model.addElement(new Book("C/C++ Programming", "A", "cpp"));
		model.addElement(new Book("Java Programming", "B", "java"));
		model.addElement(new Book("C# Programming", "C", "cs"));
		model.addElement(new Book("IOS Programming", "D", "ios"));
		model.addElement(new Book("Windows Phone Programming", "E", "wp"));
		model.addElement(new Book("Android Programming", "F", "android"));
		JList<Book> list = new JList<Book>(model);
		list.setCellRenderer(new BookRenderer());
		return list;
	}

	private JPanel createMainPanel() {
		panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(new JScrollPane(listBook = createList()), BorderLayout.CENTER);
		return panel;
	}
}
