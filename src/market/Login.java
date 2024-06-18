package market;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel {

	
	private JTextField idField;
	private JTextField pwdField;
	private JButton btnLogin;
	private JButton btnJoin;

	// 생성자
	public Login() {
		
		idField = new JTextField("아이디 입력.");
		pwdField = new JTextField("패스워드 입력.");
		btnLogin = new JButton();
		
		
		
		initData();
		setInitLayout();
		addEventListener();
		
	}

	private void addEventListener() {
		// TODO Auto-generated method stub
		
	}

	private void setInitLayout() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}
	
}
