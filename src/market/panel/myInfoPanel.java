package market.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import market.MainFrame;
import market.Resource;
import market.TextHint;
import market.DTO.ItemListDTO;
import market.repoIm.ItemRepoImpl;
import market.repoIm.UserInfoRepoIm;

public class myInfoPanel extends JPanel implements ActionListener {

	private MainFrame mContext;
	private UserInfoRepoIm userInfoRepoIm;
	private ItemRepoImpl itemRepoImpl;

	private JList<ItemListDTO> listItemDTO;
	private JButton reviceBtn;
	private JLabel reviceLbl;
	private JLabel reviceNameLbl;
	private JLabel revicePassLbl;
	private JPasswordField revicePassText;
	private JTextField reviceNameText;

	private JButton updateBtn;
	private JButton deleteBtn;
	private JScrollPane jScrollPane;
	
	private ImageIcon reviceicon;

	public myInfoPanel(MainFrame mContext, PanelAdapter panelAdapter) {
		this.mContext = mContext;
		this.userInfoRepoIm = new UserInfoRepoIm();
		this.itemRepoImpl = mContext.getItemRepoImpl();
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0, 50);

		reviceicon = new ImageIcon("img/btn수정하기.png");
		reviceBtn = new JButton(reviceicon);
		reviceLbl = new JLabel("회원정보 수정");
		reviceNameLbl = new JLabel("이름 수정");
		revicePassLbl = new JLabel("비밀번호 수정");

		reviceNameText = new JTextField();
		new TextHint(reviceNameText, mContext.getMyUserDTO().getName());
		revicePassText = new JPasswordField();
		new TextHint(revicePassText,mContext.getMyUserDTO().getPassword());
		
		reviceBtn.setBorderPainted(false); // 외각 투명하게
		reviceBtn.setContentAreaFilled(false); // 내용영역 채우기 없애기
		reviceBtn.setFocusPainted(false); // 선택 되었을 때 얇은 점선 테두리 없애기
	}

	private void setInitLayout() {
		setBackground(Color.white);
		setLayout(null);

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

		add(reviceLbl);
		add(reviceBtn);
		add(reviceNameLbl);
		add(reviceNameText);
		add(revicePassLbl);
		add(revicePassText);
	}

	private void addEventListener() {
		reviceBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source == reviceBtn) {
			String name = reviceNameText.getText();
			String password = new String(revicePassText.getPassword());
			String userId = mContext.getMyUserDTO().getUser_id();
			String originName = mContext.getMyUserDTO().getName();
			String originPw = mContext.getMyUserDTO().getPassword();
			try {
				if (!originName.equals(name)) {
					if (!originPw.equals(password)) {
						userInfoRepoIm.updateUserInfo(name, password, userId);
						Resource.MsgDialog("사용자 정보 변경 !!");
						mContext.getMyUserDTO().setName(name);
						mContext.getMyUserDTO().setPassword(password);
					} else if (originPw.equals(password)) {
						Resource.WarnMsgDialog("입력하신 비밀번호는 기존과 다른것을 입력하세요.");
					}
				} else {
					Resource.WarnMsgDialog("변경하실 이름이 같습니다 !!");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
