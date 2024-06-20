package market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatRepoIm implements ChatRepo {

	@Override
	public int CreateChatRoom(ChatDTO chatdto, ItemDTO itemdto) throws SQLException {
		int userNum1 = chatdto.getUser_id();
		int userNum2 = itemdto.getProduct_id();
		int index = 0;
		if (userNum1 > userNum2) {
			index = userNum1;
			userNum1 = userNum2;
			userNum2 = index;
		}
		String roomname = userNum1 + "/" + userNum2;
		String query = " CREATE TABLE " + roomname + "    id INT AUTO_INCREMENT, " + "    chat TEXT NOT NULL, "
				+ "    user_id INT, " + "    FOREIGN KEY (user_id) " + "        REFERENCES user (user_num " + "); ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, itemdto.getProduct_id());
			pstmt.executeUpdate();
		}
		return 0;
	}

	@Override
	public List<ChatDTO> ChatingList() throws SQLException {
		List<ChatDTO> list = new ArrayList<>();
		String query = " SELECT * FROM chatLIST ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ChatDTO dto = new ChatDTO().builder().id(rs.getString("chatroom_id")).user_id(rs.getInt("user_num"))
						.build();
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public List<ChatDTO> SearchChatList(int user_num) throws SQLException {
		List<ChatDTO> list = new ArrayList<>();
		String query = " SELECT * FROM chatList WHRER user_num = ? ";
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, user_num);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ChatDTO dto = new ChatDTO().builder().id(rs.getString("chatroom_id")).user_id(rs.getInt("user_num"))
						.build();
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public int joinChatRoom(int id) throws SQLException {
		String query = "  ";
		return 0;
	}

}
