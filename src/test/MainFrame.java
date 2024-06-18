package test;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
 
	public MainFrame() {
	initData();
	setInitLayout();
		
	}

	private void initData() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Resource.FRAMESIZE_X,Resource.FRAMESIZE_Y);
		setResizable(false);

		
	}

	private void setInitLayout() {
		
		setLayout(null); //좌표기준으로만
		setLocation(0,0);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
