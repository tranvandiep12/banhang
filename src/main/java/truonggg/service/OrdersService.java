package truonggg.service;

import java.util.List;

import truonggg.DTO.request.OrderRequestDTO;
import truonggg.DTO.response.OrderResponseDTO;

public interface OrdersService {
	OrderResponseDTO createOrder(OrderRequestDTO request);

	List<OrderResponseDTO> getMyOrders(Integer userId);

	void cancelOrderByUser(Integer orderId, Integer userId);

	OrderResponseDTO getOrderById(Integer id);

	List<OrderResponseDTO> getAllOrders();

	// OrderResponseDTO getOrderById(Integer id);

	OrderResponseDTO confirmOrder(Integer id);

	OrderResponseDTO shipOrder(Integer id);

	OrderResponseDTO cancelOrder(Integer id);

	OrderResponseDTO cancelOrderByAdmin(Integer id);

}
