package ItemManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ItemRendererPanel extends JPanel implements ListCellRenderer<Itme> {

	private JLabel lbIcon = new JLabel();
	private JLabel lbName = new JLabel();
	private JLabel lbAuthor = new JLabel();
	private JPanel panelText;
	private JPanel panelIcon;
	private BufferedImage image;
	private JFileChooserLayout chooserLayout;

	public ItemRendererPanel() {
		setLayout(new BorderLayout(5, 5));

		JPanel panelText = new JPanel(new GridLayout(0, 1));
		panelText.add(lbName);
		// price
		panelText.add(lbAuthor);
		add(lbIcon, BorderLayout.WEST);
		add(panelText, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Itme> list, Itme itme, int index, boolean isSelected,
			boolean cellHasFocus) {
		lbIcon.setIcon(new ImageIcon(getClass().getResource("/JLabel/images/" + itme.getIconName() + ".jpg"))); // resultSet.getImage
		lbName.setText(itme.getName()); // resultSet.getName
		lbAuthor.setText(itme.getPirce()); // resultSet.getPrice

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