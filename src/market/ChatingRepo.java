package market;


import java.util.List;

public interface ChatingRepo {

	int addChatRoom(ChatDTO dto);

	List<ChatDTO> ChatingList();

	int joinChatRoom(int id);

}
