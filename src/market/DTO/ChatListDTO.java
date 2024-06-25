package market.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class ChatListDTO {
	private String roomName;
	private int roomId;
	
	public String toString() {
		
		return roomName;
		
	}
}

