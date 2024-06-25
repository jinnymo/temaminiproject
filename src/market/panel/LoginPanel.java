package market.panel;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import market.MainFrame;
import market.Register;
import market.Resource;
import market.TextHint;
import market.DTO.UserDTO;
import market.repoIm.UserRepoImpl;

public class LoginPanel extends JPanel implements FocusListener {

	private JTextField idField;
	private JTextField pwdField;
	private JButton btnLogin;
	private JButton btnJoin;
	private Register register;
	private MainFrame context;
	private UserRepoImpl uImpl;
	private UserDTO myUserDTO;
	private TextHint hint;
	private Resource resource;

	// 생성자
	public LoginPanel(MainFrame context) {
		this.context = context;
		uImpl = new UserRepoImpl();
		initData();
		setInitLayout();
		addActionListener();

	}

	private void initData() {

		idField = new JTextField();
		hint = new TextHint(idField, "아이디 입력");
		pwdField = new JTextField();
		hint = new TextHint(pwdField, "패스워드 입력");
		btnLogin = new JButton("LOGIN");
		btnJoin = new JButton("회원가입");

		// --------------------------------------------------------------------

		// 힌트 텍스트 색상 설정 (회색)
		idField.setForeground(Color.GRAY);
		pwdField.setForeground(Color.GRAY);

	}

	// --------------------------------------------------------------------

	private void setInitLayout() {
		setLayout(null);
		setSize(Resource.PANEL_FULL_SIZE_X, Resource.PANEL_FULL_SIZE_Y);
		setLocation(0, 0);
		setBackground(Color.green);
		setVisible(true);

		add(idField);
		add(pwdField);
		add(btnLogin);
		add(btnJoin);

		idField.addFocusListener(this);
		pwdField.addFocusListener(this);

		idField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		pwdField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		btnLogin.setSize(Resource.LOGIN_COMP_X - 50, Resource.LOGIN_COMP_Y);
		btnJoin.setSize(Resource.LOGIN_COMP_X - 50, Resource.LOGIN_COMP_Y);

		idField.setLocation(100, 100);
		pwdField.setLocation(100, 140);
		btnLogin.setLocation(125, 180);
		btnJoin.setLocation(125, 220);

	}

	private void addActionListener() {
		// 로그인 버튼 -> 메인페이지 or RETRY
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				String id = idField.getText();
				String pwd = pwdField.getText();

				try {
					if (uImpl.checkuserID(id)) {
						//아이디 입력 오류일떄 실행문
						Resource.MsgDialog("아이디 입력 오류 !!");
						System.out.println("아이디 입력 오류");

					} else {
						//입력한 아이디가 db에 존재 할때 실행되는 부분!!!
						if ((myUserDTO = uImpl.checkUserPwd(id, pwd)) != null) {
							// UserDTO myUserDTO = uImpl.checkUserPwd(id, pwd) != null
							//비밀번호가 맞을떄 실행되는 부분
							Resource.MsgDialog("로그인 성공 !!");
							System.out.println("로그인 성공!!");

							context.startMainSystem(myUserDTO);
							setVisible(false);

						} else {
							//비밀번호 틀렸을떄 실행되는 부분!!!
							// 여기는 비밀번호 틀렸을떄 실행되는 부분!!!
							// 여기도 당연히 알람 메세지 출력 꼭 해야 겠죠???
							Resource.MsgDialog("비밀번호 입력 오류 !!");
							System.out.println("로그인 실패 !!");
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 회원가입 버튼 -> 회원가입 상세
		btnJoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				register = new Register(context, uImpl);
				setVisible(false);

			}

		});

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == idField) {
			idField.setText("");
			idField.setForeground(Color.BLACK);
		} else if (e.getSource() == pwdField) {
			pwdField.setText("");
			pwdField.setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == idField) {
			if (idField.getText().isEmpty()) {
				idField.setForeground(Color.GRAY);
				idField.setText("아이디 입력.");
			}

		} else if (e.getSource() == pwdField) {
			if (pwdField.getText().isEmpty()) {
				pwdField.setForeground(Color.GRAY);
				pwdField.setText("패스워드 입력.");
			}
		}

	}

}
