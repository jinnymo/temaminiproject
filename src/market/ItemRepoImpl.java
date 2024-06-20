package market;

import java.sql.SQLException;

public class ItemRepoImpl implements ItemRepo{

	@Override
	public int addItem(String productName, int price, String state, String content, int myUserNum, int categoryId)
			throws SQLException {
		
		return 0;
	}  

	@Override
	public int addImage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
