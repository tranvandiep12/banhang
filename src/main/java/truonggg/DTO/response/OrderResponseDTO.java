package truonggg.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truonggg.response.PaymentMethod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
	private Integer id;
	private Integer userId;
	private String shippingAddress;
	private LocalDateTime orderDate;
	private Double totalAmount;
	private String status;
	private PaymentMethod paymentMethod;
	private List<OrderItemResponseDTO> items;
}
