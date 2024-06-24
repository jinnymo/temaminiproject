package market.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import market.panel.DBConnectionManager;

public class testUser {
	private static final String ADD_USER = " insert into user(user_id, name, password) values(?, ?, ?)  ";

	public static void main(String[] args) {
		testUser tester = new testUser();
		try {
			int insertedRows = tester.userInsert();
			System.out.println("Inserted rows: " + insertedRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int userInsert() throws SQLException {

		String[] names = { "김민준", "이서준", "박도윤", "최예준", "정시우", "강하준", "조준우", "윤주원", "한우진", "오윤우", "서서연", "유지우", "문서현",
				"백하윤", "장민서", "이지유", "김수아", "박지민", "최은서", "정다은", "강예서", "조서윤", "윤하은", "한지아", "오소율", "서지윤", "유예진", "문채원",
				"백가은", "장사랑", "이유진", "김아린", "박수빈", "최연우", "정유나", "강하린", "조아윤", "윤다인", "한서우", "오서율", "서은채", "유지안", "문채은",
				"백하린", "장윤서", "이태윤", "김시윤", "박하람", "최민기", "정건우", "강도현", "조주호", "윤현우", "한지후", "오도현", "서지후", "유주아", "문유정",
				"백지호", "장주현", "이동현", "김지환", "박수호", "최시원", "정승현", "강태훈", "조영민", "윤성민", "한지훈", "오민재", "서승우", "유지환", "문정우",
				"백정민", "장태민", "이성현", "김영훈", "박민수", "최지성", "정성준", "강주원", "조윤성", "윤민혁", "한성호", "오태현", "서승현", "유성준", "문시훈",
				"백승민", "장태우", "이영수", "김주환", "박민호", "최성우", "정지훈" };

		String[] id = createIdList();

		String[] pwd = createPasswordList();

		int resultRowCount = 0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(ADD_USER);
			for (int i = 0; i < 100; i++) {
				pstmt.setString(1, id[i]);
				pstmt.setString(2, names[i]);
				pstmt.setString(3, pwd[i]);
				pstmt.executeUpdate();
				resultRowCount++;
			}
		}
		return resultRowCount;
	}

	private String[] createIdList() {
		String[] ids = new String[100];
		Random random = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 100; i++) {
			StringBuilder id = new StringBuilder();
			int length = random.nextInt(5) + 8; // ID 길이는 8에서 12 사이
			for (int j = 0; j < length; j++) {
				id.append(characters.charAt(random.nextInt(characters.length())));
			}
			ids[i] = id.toString();
		}
		return ids;
	}

	private String[] createPasswordList() {
		String[] passwords = new String[100];
		Random random = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+<>?";

		for (int i = 0; i < 100; i++) {
			StringBuilder password = new StringBuilder();
			int length = random.nextInt(5) + 8; // 비밀번호 길이는 8에서 12 사이
			for (int j = 0; j < length; j++) {
				password.append(characters.charAt(random.nextInt(characters.length())));
			}
			passwords[i] = password.toString();
		}
		return passwords;
	}

}
