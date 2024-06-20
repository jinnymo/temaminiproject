package market;

import java.sql.SQLException;
import java.util.List;

public interface ChatingRepo {

	int addChatRoom(ChatDTO dto, ItemDTO itemdot) throws SQLException;

	List<ChatDTO> ChatingList() throws SQLException;

	List<ChatDTO> SearchChatList(int user_num) throws SQLException;

	int joinChatRoom(int id) throws SQLException;

}
