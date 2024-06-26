package market;

import java.awt.Color;

import javax.swing.JFrame;

import lombok.Data;
import lombok.Getter;
import market.DTO.UserDTO;
import market.panel.LoginPanel;
import market.panel.PanelAdapter;
import market.repoIm.ItemRepoImpl;

@Data
public class MainFrame extends JFrame {
 
	public LoginPanel loginPanel;
	private PanelAdapter panelAdapter;
	private UserDTO myUserDTO;
	private ItemRepoImpl itemRepoImpl;

	public MainFrame() {
		loginPanel = new LoginPanel(this);
		itemRepoImpl = new ItemRepoImpl();
		initData();
		setInitLayout();
	}

	private void	 initData() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Resource.FRAMESIZE_X, Resource.FRAMESIZE_Y);
		setBackground(Color.black);
	}
  
	private void setInitLayout() {
		setLayout(null); // 좌표 기준으로 설정
		setLocation(0, 0);

		add(loginPanel);
		loginPanel.setVisible(true);

		setVisible(true);
	}

	public void startMainSystem(UserDTO MyUserDTO) {
		this.myUserDTO = MyUserDTO;
		panelAdapter = new PanelAdapter(this);
		panelAdapter.setSize(Resource.FRAMESIZE_X, Resource.FRAMESIZE_Y); // 패널 크기 설정
		panelAdapter.setLocation(0, 0); // 패널 위치 설정

		add(panelAdapter);
		panelAdapter.setVisible(true); // 패널 가시성 설정

		// 기존 로그인 패널 제거
		loginPanel.setVisible(false);
		remove(loginPanel);
	}

	public static void main(String[] args) {
		new MainFrame();
		
	}

}
