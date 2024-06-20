package market;

import java.util.List;

public class ChatRepoIm implements ChatingRepo {

	@Override
	public int addChatRoom(ChatDTO dto) {
		int userNum1 = dto.getNameNum();
		int userNum2 = dto.getNameNum();
		int index = 0;
		if (userNum1 > userNum2) {
			index = userNum1;
			userNum1 = userNum2;
			userNum2 = index;
		}
		String query = "INSERT INTO chatlist VALUES (?)";
		return 0;
	}

	@Override
	public List<ChatDTO> ChatingList() {
		String query = "";
		return null;
	}

	@Override
	public int joinChatRoom(int id) {
		String query = "";
		return 0;
	}

}
