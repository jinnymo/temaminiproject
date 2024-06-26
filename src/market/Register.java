package market;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import market.repoIm.UserRepoImpl;

public class Register extends JPanel implements FocusListener {
	private JTextField userName;
	private JTextField idField;
	// passwordfield 쓰면 hint도 *로 표시
	private JTextField pwdField;
	private JButton btnSubmit;
	private JButton btnCheckId;
	
	private Resource resource;
	private TextHint hint;

	private MainFrame context;
	private UserRepoImpl uImpl;
	private boolean idCheck;

	private ImageIcon idCheckIcon;
	private ImageIcon submitIcon;
	
	private JLabel logoLabel;
	private ImageIcon logoIcon;
	
	// 생성자
	public Register(MainFrame context, UserRepoImpl uImpl) {
		this.context = context;
		this.uImpl = uImpl;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		logoIcon = new ImageIcon("img/포도마켓.png");
		idCheckIcon = new ImageIcon("img/btnid중복확인.png");
		submitIcon = new ImageIcon("img/btn가입하기.png");
		
		
		logoLabel = new JLabel(logoIcon);
		
		userName = new JTextField("이름을 입력하세요.");
		idField = new JTextField("아이디를 입력하세요");
		// TODO 아이디 중복확인 , 중복금지
		pwdField = new JTextField("패스워드를 입력하세요");
		btnSubmit = new JButton(submitIcon);
		btnCheckId = new JButton(idCheckIcon);

		userName.setForeground(Color.GRAY);
		idField.setForeground(Color.GRAY);
		pwdField.setForeground(Color.GRAY);

	}

	private void setInitLayout() {
		setLayout(null);
		setSize(Resource.PANEL_FULL_SIZE_X, Resource.PANEL_FULL_SIZE_Y);
		setLocation(0, 0);
		setBackground(Color.white);
		setVisible(true);

		add(logoLabel);
		add(userName);
		add(idField);
		add(pwdField);
		add(btnSubmit);
		add(btnCheckId);

		userName.addFocusListener(this);
		idField.addFocusListener(this);
		pwdField.addFocusListener(this);

		userName.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		idField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		pwdField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		btnSubmit.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		btnCheckId.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		logoLabel.setSize(100,100);
		logoLabel.setLocation(150,90);

		userName.setLocation(125, 240);
		idField.setLocation(125, 270);
		btnCheckId.setLocation(125, 310);
		pwdField.setLocation(125, 350);
		btnSubmit.setLocation(125, 390);

		context.add(this);

		// 알림 양식
		// JOptionPane.showMessageDialog(null, "알림","알림제목",JOptionPane.WARNING_MESSAGE);

		btnCheckId.setBorderPainted(false); // 외각 투명하게
		btnCheckId.setContentAreaFilled(false); // 내용영역 채우기 없애기
		btnCheckId.setFocusPainted(false); // 선택 되었을 때 얇은 점선 테두리 없애기
		
		btnSubmit.setBorderPainted(false); // 외각 투명하게
		btnSubmit.setContentAreaFilled(false); // 내용영역 채우기 없애기
		btnSubmit.setFocusPainted(false); // 선택 되었을 때 얇은 점선 테두리 없애기
	}

	private void addEventListener() {
		btnCheckId.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 아이디 중복확인 쿼리 시작
				// 쿼리 내용 -> user에 접근해서 select*from where 해서
				// 콜백이 있으면 중복 없으면 가능
				idCheck = false;
				try {
					if (uImpl.checkuserID(idField.getText())) {
						idField.setEditable(false);
						idCheck = true;
						idField.setBackground(Color.gray);
						// 사용가능
						btnCheckId.setText("사용가능");
					} else {
						// 아이디 중복 알림
//						JOptionPane.showMessageDialog(null, "이미 사용중인 ID 입니다.", "경고", JOptionPane.WARNING_MESSAGE);
						Resource.WarnMsgDialog("이미 사용중인 ID 입니다.");
						idField.setText("중복 아이디 다시 입력");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (checkTextField() && idCheck) {
					int result = 0;
					try {
						result = uImpl.adduser(idField.getText(), userName.getText(), pwdField.getText());
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					if (result > 0) {
						// 회원가입 완료 알림
//						JOptionPane.showMessageDialog(null, " 회원가입이 완료되었습니다. ", "", JOptionPane.PLAIN_MESSAGE);
						Resource.MsgDialog(" 회원가입이 완료되었습니다. ");
						// 정상 등록 되면 회원가입창 숨기고 로그인창 열기
						setVisible(false);
						context.loginPanel.setVisible(true);

					}

				} else {
					// 회원가입 > 아이디 중복체크 x 알림
//					JOptionPane.showMessageDialog(null, " 아이디 중복확인를 해주십시요. ", "경고", JOptionPane.WARNING_MESSAGE);
					Resource.WarnMsgDialog(" 아이디 중복확인를 확인하세요. ");
				}

			}
		});

	}

	private boolean checkTextField() {

		if (userName.getText().equals("") || userName.getText().equals("이름을 입력하세요.")) {
			return false;
		} else if (pwdField.getText().equals("") || pwdField.getText().equals("패스워드를 입력하세요")) {
			System.out.println("test2");
			return false;
		} else {
			return true;
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == idField) {
			idField.setText("");
			idField.setForeground(Color.BLACK);
		} else if (e.getSource() == userName) {
			userName.setText("");
			userName.setForeground(Color.BLACK);
		} else if (e.getSource() == pwdField) {
			pwdField.setText("");
			pwdField.setForeground(Color.BLACK);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == idField) {
			if (idField.getText().isEmpty()) {
				idField.setForeground(Color.GRAY);
//				idField.setText("아이디를 입력하세요.");
				hint = new TextHint(idField, "아이디를 입력하세요.");
			}
		} else if (e.getSource() == userName) {
			if (userName.getText().isEmpty()) {
				userName.setForeground(Color.GRAY);
//				userName.setText("이름을 입력하세요.");
				hint = new TextHint(userName, "이름을 입력하세요.");
			}
		} else if (e.getSource() == pwdField) {
			if (pwdField.getText().isEmpty()) {
				pwdField.setForeground(Color.GRAY);
//				pwdField.setText("비밀번호를 입력하세요.");
				hint = new TextHint(pwdField, "비밀번호를 입력하세요.");
			}
		}
	}
}
