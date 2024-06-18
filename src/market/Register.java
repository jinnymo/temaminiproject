package market;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JPanel {
	private JTextField userName;
	private JTextField idField;
	private JTextField pwdField;
	private JButton btnSubmit;
	private JButton btnCheckId;

	private MainFrame context;
	private UserRepoImpl uImpl;
	private boolean idCheck;

	// 생성자
	public Register(MainFrame context, UserRepoImpl uImpl) {
		this.context = context;
		this.uImpl = uImpl;
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
		btnCheckId = new JButton("아이디 중복 확인");

	}

	private void setInitLayout() {
		setLayout(null);
		setSize(Resource.PANEL_FULL_SIZE_X, Resource.PANEL_FULL_SIZE_Y);
		setLocation(0, 0);
		setBackground(Color.yellow);
		setVisible(true);

		add(userName);
		add(idField);
		add(pwdField);
		add(btnSubmit);
		add(btnCheckId);

		userName.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		idField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		pwdField.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		btnSubmit.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);
		btnCheckId.setSize(Resource.LOGIN_COMP_X, Resource.LOGIN_COMP_Y);

		userName.setLocation(100, 70);
		idField.setLocation(100, 100);
		btnCheckId.setLocation(100, 140);
		pwdField.setLocation(100, 180);
		btnSubmit.setLocation(125, 220);

		context.add(this);
	}

	private void addEventListener() {
		btnCheckId.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// todo
				// 아이디 중복확인 쿼리 시작
				// 쿼리 내용 -> user에 접근해서 select*from where 해서
				// 콜백이 있으면 중복 없으면 가능
				idCheck = false;
				try {
					if (uImpl.checkuserID(idField.getText())) {
						// 여기에 저장하는 이유는 회원가입 버튼누를떄 중복확인하고
						// 또 아이디 부분이 변경 되었을 경우를 가정해서 추후 확인하기 위해
						// 임시 위치에 저장
						// idReCheck = idField.getText();
						// 위에 우려 사항을 그냥 수정 못하게 막
						idField.setEditable(false);
						idCheck = true;
						idField.setBackground(Color.gray);
						// 사용가능
						btnCheckId.setText("사용가능");
					} else {
						// TODO 여기는 중복아이디 일시 실행되는 부분
						//여기서도 사용자에게 알람 띄우기!!!!! 꼭!!
						
						idField.setText("중복 아이디 다시 입력");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				if (checkTextField() && idCheck) {
					int result = 0;
					try {
						result = uImpl.adduser(idField.getText(), userName.getText(), pwdField.getText());
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					if (result > 0) {
						// TODO db에 유저 정보 정상등록 된 상황
						// 유저에게 알람 띄우기 메세지 = 회원가입 성공
						
						//정상 등록 되면 회원가입창 숨기고 로그인창 열기 
						setVisible(false);
						context.loginPanel.setVisible(true);
						
					}
					
				} else {
					// TODO 빈값이 있거나 기본 데이터에서 수정 하지 않은 필드가 있다는 뜻
					// 여기서 알림차 띄우기 !!!!
					//또는 id 중복 체크 하지 않았다는 거
					//사용자에가 알람을띄우는데 무었을 안했는지 알려주는게
					// 좋겠지요??/????
					System.out.println("test- 무언갈 안함!!!");
				}

			}
		});

	}

	private boolean checkTextField() {

		if (userName.getText().equals("") || userName.getText().equals("이름을 입력하세요.")) {
			System.out.println("test1");
			return false;
		}else if (pwdField.getText().equals("") || pwdField.getText().equals("패스워드를 입력하세요")) {
			System.out.println("test2");
			return false;
		}else {
			return true;
		}
		
		
	}

}
