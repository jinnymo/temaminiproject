package market;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JPanel {
	private JTextField userName;
	private JTextField idField;
	private JTextField pwdField;
	private JButton btnSubmit;
	
	private MainFrame context;
	// TODO 아이디 확인
	
	// 생성자
	public Register(MainFrame context) {
		this.context = context;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		userName = new JTextField("이름을 입력하세요.");
		idField = new JTextField("아이디를 입력하세요");
		// TODO 아이디 중복확인 , 중복금지
		pwdField = new JTextField("패스워드를 입력하세요");
		btnSubmit = new JButton("가입하기");
		
		

	}

	private void setInitLayout() {
		setLayout(null);
		setSize(Resource.PANEL_FULL_SIZE_X, Resource.PANEL_FULL_SIZE_Y);
		setLocation(0, 0);
		setBackground(Color.yellow);
		setVisible(true);
		add(userName);
		add(idField);
		// TODO 아이디 확인
		add(pwdField);
		add(btnSubmit);
		userName.setSize(Resource.LOGIN_COMP_X,Resource.LOGIN_COMP_Y);
		idField.setSize(Resource.LOGIN_COMP_X,Resource.LOGIN_COMP_Y);
		// TODO 아이디 확인
		pwdField.setSize(Resource.LOGIN_COMP_X,Resource.LOGIN_COMP_Y);
		btnSubmit.setSize(Resource.LOGIN_COMP_X ,Resource.LOGIN_COMP_Y );
		
		userName.setLocation(100,70);
		idField.setLocation(100,100);
		// TODO 아이디 확인 
		
		pwdField.setLocation(100,140);
		btnSubmit.setLocation(125,180);

		context.add(this);
	}

	private void addEventListener() {

			
		
	}
	
	
}
