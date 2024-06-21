package market;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class myInfoPanel extends JPanel implements ActionListener {

	private MainFrame mContext;
	private UserInfoRepoIm userInfoRepoIm;

	private JButton reviceBtn; // 수정 버튼
	private JLabel reviceLbl; // "회원정보 수정" 라벨
	private JLabel reviceNameLbl; // "이름 수정" 라벨
	private JLabel revicePassLbl; // "비밀번호 수정" 라벨
	private JTextField revicePassText; // 비밀번호 입력 필드
	private JTextField reviceNameText; // 이름 입력 필드

	public myInfoPanel(MainFrame mContext) {
		this.mContext = mContext;
		this.userInfoRepoIm = new UserInfoRepoIm();
		initData();
		setInitLayout();
		addEventLisetener();
	}

	private void initData() {
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0, 50);

		reviceBtn = new JButton("수정");
		reviceLbl = new JLabel("회원정보 수정");
		reviceNameLbl = new JLabel("이름 수정");
		revicePassLbl = new JLabel("비밀번호 수정");

		reviceNameText = new JTextField("이름 입력");
		revicePassText = new JTextField("비밀번호 입력");

		reviceLbl.setSize(100, 100);
		reviceLbl.setLocation(10, 10);

		reviceBtn.setSize(100, 100);
		reviceBtn.setLocation(280, 10);

		reviceNameLbl.setSize(100, 25);
		reviceNameLbl.setLocation(110, 10);

		reviceNameText.setSize(150, 25);
		reviceNameText.setLocation(110, 35);
		reviceNameText.setBackground(Color.white);

		revicePassLbl.setSize(100, 25);
		revicePassLbl.setLocation(110, 60);

		revicePassText.setSize(150, 25);
		revicePassText.setLocation(110, 85);
		revicePassText.setBackground(Color.white);
	}

	private void setInitLayout() {
		setBackground(Color.gray);
		add(reviceLbl);
		add(reviceBtn);
		add(reviceNameLbl);
		add(reviceNameText);
		add(revicePassLbl);
		add(revicePassText);
	}

	private void addEventLisetener() {
		reviceBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source == reviceBtn) {
			String name = reviceNameText.getText();
			String Password = revicePassText.getText();
			String userId = mContext.getMyUserDTO().getUser_id();
			try {
				System.out.println("name : " + name + " Pass : " + Password + " user_id : " + userId);
				userInfoRepoIm.updateUserInfo(name, Password, userId);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
