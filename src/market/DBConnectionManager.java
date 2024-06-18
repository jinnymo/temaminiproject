package market;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * 커넥션 풀을 활용하는 예제로 수정해 보자.
 *  HikariCP-5.1.0.jar lib 설정
 */

public class DBConnectionManager {

	private static DBConnectionManager instance;
	private static HikariDataSource dataSource;

	// 준비물
	private static final String URL = "jdbc:mysql://jinnymo.iptime.org:3306/miniproject?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";

	private DBConnectionManager() {

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);

		config.setMaximumPoolSize(10); // 최대 연결 수 설정 10
		dataSource = new HikariDataSource(config);

	}

	public synchronized static DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
//	public static Connection getConnection() throws SQLException{
//		System.out.println("HikariCP을 사용한 Hata Source 활용");
//		return dataSource.getConnection();
//	}

	// 테스트 코드

} // end of main
