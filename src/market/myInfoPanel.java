package market;

import java.awt.Color;

import javax.swing.JPanel;

public class myInfoPanel extends JPanel implements Runnable{



	public myInfoPanel() {

		initData();
		setInitLayout();

	}

	private void initData() {
		
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0,50);
		
	}

	private void setInitLayout() {
		setBackground(Color.gray);
		

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}





}
