package market.repo;

import java.sql.SQLException;
import java.util.List;

import market.DTO.ChatDTO;
import market.DTO.ChatListDTO;
 
public interface ChatRepo {
	
	int checkChatRoom(String chatroom_id)throws SQLException;

	int newChatRoom(String chatroom_id, int myNum,int sellerNum) throws SQLException;

	int addUserByChatRoom(String chatroom_id, int user) throws SQLException;
	
	List<ChatListDTO> ChatingList(int myUserId) throws SQLException;

	List<ChatDTO> SearchChatList(int user_num) throws SQLException;

	List<ChatDTO> getChat(int roomId) throws SQLException;
	
	int addChat(int roomId,String content) throws SQLException;
	
	
}
 