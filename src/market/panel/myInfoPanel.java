package market.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

		reviceBtn = new JButton("수정");
		reviceLbl = new JLabel("회원정보 수정");
		reviceNameLbl = new JLabel("이름 수정");
		revicePassLbl = new JLabel("비밀번호 수정");

		reviceNameText = new JTextField();
		new TextHint(reviceNameText, mContext.getMyUserDTO().getName());
		revicePassText = new JPasswordField();
		new TextHint(revicePassText, mContext.getMyUserDTO().getPassword());
	}

	private void setInitLayout() {
		setBackground(Color.gray);
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
