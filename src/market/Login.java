package market;

import java.awt.Color;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel {

	private JTextField idField;
	private JTextField pwdField;
	private JButton btnLogin;
	private JButton btnJoin;
	private Register register;
	private MainFrame context;
	// 생성자
	public Login(MainFrame context) {
		this.context = context;
		initData();
		setInitLayout();
		addActionListener();

	}

	private void initData() {

		idField = new JTextField("아이디 입력.");
		pwdField = new JTextField("패스워드 입력.");
		btnLogin  = new JButton ("LOGIN");
		btnJoin = new JButton();

	}

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
		
		idField.setSize(Resource.LOGIN_COMP_X,Resource.LOGIN_COMP_Y);
		pwdField.setSize(Resource.LOGIN_COMP_X,Resource.LOGIN_COMP_Y);
		btnLogin.setSize(Resource.LOGIN_COMP_X - 50,Resource.LOGIN_COMP_Y );
		btnJoin.setSize(Resource.LOGIN_COMP_X - 50,Resource.LOGIN_COMP_Y);
		
		idField.setLocation(100,100);
		pwdField.setLocation(100,140);
		btnLogin.setLocation(125,180);
		btnJoin.setLocation(125,220);

	}

	private void addActionListener() {
	// 로그인 버튼 -> 메인페이지 or RETRY
	btnLogin.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			
			super.mousePressed(e);
		}
		 
	});
	// 회원가입 버튼 -> 회원가입 상세
	btnJoin.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			 register = new Register(context);
			 setVisible(false);
			
		}
		
	});
	
		
	}

}
