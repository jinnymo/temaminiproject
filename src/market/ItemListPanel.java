package market;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemListPanel extends JPanel implements Runnable {

	private JLabel imageLabel;

	public ItemListPanel() {

		initData();
		setInitLayout();

	}

	private void initData() {

		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0, 50);

		imageLabel = new JLabel();
		imageLabel.setSize(400, 200);
		imageLabel.setLocation(0, 50);
	}

	private void setInitLayout() {
		setBackground(Color.pink);
		add(imageLabel);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
