package ItemManager;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// 싱글톤 패턴 - 단 하나의 객체만 필요함을 보장해야 된다면
// 싱글톤 패턴으로 설계 할 수 있다.
public class DBConnationManager {

	// 자기 자신의 참조 주소값을 담을 변수 생성 단. private
	private static DBConnationManager instance;
	private HikariDataSource dataSource;

	// 생성자를 <---- 외부에서 생성자를 호출 못 하게 막야아 한다.
	private DBConnationManager() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/studentdb?serverTimezone=Asia/Seoul");
		config.setUsername("root");
		config.setPassword("asd123");
		config.setMaximumPoolSize(10);
		dataSource = new HikariDataSource(config);
	}

	// 외부에서 클래스 이름.getxxx 메서들르 만들어 주면 된다.
	// 한 번에 하나의 스레드만 접근하도록 동기화 적용
	public synchronized static DBConnationManager getInstance() {
		if (instance == null) {
			instance = new DBConnationManager();
		}
		return instance;
	}

	// Connection 객체를 반환 (구현체 - HikariCP 이다)
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
