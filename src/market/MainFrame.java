package market;

import java.awt.Color;

import javax.swing.JFrame;

import lombok.Data;

public class MainFrame extends JFrame{
	Login a;

	public MainFrame() {
	a = new Login(this);
	
	initData();
	setInitLayout();
		
	}
	
	private void initData() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Resource.FRAMESIZE_X,Resource.FRAMESIZE_Y);
		//setResizable(false);
		setBackground(Color.black);
		
	} 

	private void setInitLayout() {
		
		setLayout(null); //좌표기준으로만
		setLocation(0,0);
		setVisible(true);
		
		add(a);

	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
