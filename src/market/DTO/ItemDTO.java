package market.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ItemDTO {

	private int product_id;
	private String product_name;
	private int price;
	private String state;
	private String date;
	private String text;

	private int user_id;
	private int category_id;

}
