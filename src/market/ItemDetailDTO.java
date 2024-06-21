package market;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDetailDTO {
	// i.product_id, product_name, price, state, date, product_info, u.user_num,
	// u.user_id

	private int product_id;
	private String product_name;
	private String price;
	private String state;
	private String date;
	private String product_info;
	private int user_num;
	private String user_id;

	private List<byte[]> sImageList;
	private List<byte[]> bImageList;

}