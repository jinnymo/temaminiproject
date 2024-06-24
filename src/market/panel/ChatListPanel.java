package market.panel;

import java.awt.Color;

import javax.swing.JPanel;

import market.Resource;

public class ChatListPanel extends JPanel implements Runnable {


	public ChatListPanel() {

		initData();
		setInitLayout();

	}

	private void initData() {
		
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0,50);
		
	}

	private void setInitLayout() {
		setBackground(Color.red);
		

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}



}
