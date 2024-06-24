package market.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemListDTO {
	private int userNum;
	private int productId;
	private String productName;
	private String price;
	private byte[] image;
}
