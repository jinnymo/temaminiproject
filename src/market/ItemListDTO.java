package market;

import com.mysql.cj.jdbc.Blob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemListDTO {
	private String name;
	private String price;
	private byte[] image;
}
