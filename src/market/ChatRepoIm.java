package market;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatRepoIm implements ChatRepo {

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

	@Override
	public int newChatRoom(String chatroom_id, int myNum, int sellerNum) throws SQLException {

		String query1 = " insert into chatroomidlist(chatroom_id) value (?) ";
		String query2 = " select id from chatroomidlist where chatroom_id = ? ";
		String query3 = " insert into chatroomuser(chatroomid,user_num) value (?,?) ";
		
		int chatroomId =0;
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(query1);
				PreparedStatement pstmt2 = conn.prepareStatement(query2);
				PreparedStatement pstmt3 = conn.prepareStatement(query3)) {

			pstmt1.setString(1, chatroom_id);
			pstmt1.executeUpdate();

			pstmt2.setString(1, chatroom_id);
			ResultSet rs = pstmt2.executeQuery();
			
			if (rs.next()) {
				chatroomId = rs.getInt("id");
			}
			
			pstmt3.setInt(1, chatroomId);
			pstmt3.setInt(2, myNum);
			pstmt3.executeUpdate();

			pstmt3.setInt(1, chatroomId);
			pstmt3.setInt(2, sellerNum);
			pstmt3.executeUpdate();
		}

		// result 가 3이면 정상 작동
		return chatroomId;
	} 

	@Override
	public int addUserByChatRoom(String chatroom_id, int user) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
   
	@Override
	public int checkChatRoom(String chatroom_id) throws SQLException {
		String query1 = " select * from chatroomidlist where chatroom_id = ? ";
		
		try (Connection conn = DBConnectionManager.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query1)) {

			pstmt.setString(1, chatroom_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//기존에 방이 존재함 
				System.out.println("있음");
				return rs.getInt("id");
			}else {
				//기존에 방이 없음 새로 만드셈
				System.out.println("없음");
			}
		}
		return 0;
	}

}
