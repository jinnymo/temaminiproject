package market;

import java.awt.Color;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class myInfoPanel extends JPanel implements Runnable{
	
	// 화면 구성
	// 이름 , id
	// 회원정보 수정 이름과 비밀번호 변경
	// 내가 판매한 상품 목록 

	// 회원정보 수정 -> panel로 만들어서 text 사용해서 버튼 누르면 -> 버튼(수정) -> , text
	// item -> panel  , draw
	
	
	private JPanel mypagePanel;
	private JButton reviceBtn; //수정
	private JLabel reviceLbl;
	private JPanel itemListPanel; // 라벨
	private TextField updatePassword;
	private TextField updateName;
	

	public myInfoPanel() {

		initData();
		setInitLayout();

	}

	private void initData() {
		
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0,50);
		
		mypagePanel = new JPanel();
		reviceBtn = new JButton("수정");
		reviceLbl = new JLabel("회원정보 수정");
		
		mypagePanel.setSize(getPreferredSize());
		
		reviceLbl.setLocation(10, 10);
		reviceBtn.setLocation(300, 10);
		
		
	}

	private void setInitLayout() {
		setBackground(Color.gray);
		add(reviceLbl);
		add(reviceBtn);
		add(mypagePanel);


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}





}
