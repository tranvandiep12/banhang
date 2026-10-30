package truonggg.DTO.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class OrderRequestDTO {
	@NotNull(message = "User ID is required")
	private Integer userId;

	@NotBlank(message = "Shipping address is required")
	private String shippingAddress;

	@NotBlank(message = "Payment method is required")
	@NotNull(message = "Phương thức thanh toán không được để trống")
	private PaymentMethod paymentMethod;

	@NotEmpty(message = "Order must contain at least one item")
	private List<OrderItemRequestDTO> items;
}
