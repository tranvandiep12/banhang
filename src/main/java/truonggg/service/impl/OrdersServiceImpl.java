package truonggg.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.OrderRequestDTO;
import truonggg.DTO.response.OrderResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.OrdersMapper;
import truonggg.model.OrderItem;
import truonggg.model.Orders;
import truonggg.model.Product;
import truonggg.model.User;
import truonggg.repository.OrdersRepository;
import truonggg.repository.ProductRepository;
import truonggg.repository.UserRepository;
import truonggg.service.OrdersService;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

	private final OrdersRepository ordersRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrdersMapper ordersMapper;

	@Override
	@Transactional
	public OrderResponseDTO createOrder(OrderRequestDTO request) {
		// Lấy thông tin user
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Map từng OrderItem từ request -> entity
		List<OrderItem> orderItems = request.getItems().stream().map(item -> {
			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			return OrderItem.builder().product(product).quantity(item.getQuantity()).price(product.getPrice()).build();
		}).collect(Collectors.toList());

		// Tạo Orders từ DTO
		Orders order = ordersMapper.toModel(request);
		order.setUser(user);
		order.setOrderItems(orderItems);
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("PENDING");
		order.setPaymentMethod(request.getPaymentMethod());
		// Tính tổng tiền
		double totalAmount = orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		order.setTotalAmount(totalAmount);

		// Gắn đơn hàng vào từng orderItem
		orderItems.forEach(item -> item.setOrder(order));

		// Lưu vào DB
		ordersRepository.save(order);

		// Trả về DTO
		return ordersMapper.toDTO(order);
	}

	@Override
	public List<OrderResponseDTO> getMyOrders(Integer userId) {
		List<Orders> orders = ordersRepository.findByUserId(userId);
		return orders.stream().map(ordersMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public void cancelOrderByUser(Integer orderId, Integer userId) {
		Orders order = ordersRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng."));

		if (!order.getUser().getId().equals(userId)) {
			throw new RuntimeException("Bạn không có quyền hủy đơn hàng này.");
		}

		if (!order.getStatus().equals("PENDING")) {
			throw new RuntimeException("Chỉ đơn hàng đang chờ xử lý (PENDING) mới có thể hủy.");
		}

		order.setStatus("CANCELED");
		ordersRepository.save(order);
	}

	@Override
	public OrderResponseDTO getOrderById(Integer id) {
		Orders order = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
		return ordersMapper.toDTO(order);
	}

	@Override
	public List<OrderResponseDTO> getAllOrders() {
		return ordersRepository.findAll().stream().map(ordersMapper::toDTO).toList();
	}

	@Override
	public OrderResponseDTO confirmOrder(Integer id) {
		Orders order = findOrderById(id);
		order.setStatus("CONFIRMED");
		return ordersMapper.toDTO(ordersRepository.save(order));
	}

	@Override
	public OrderResponseDTO shipOrder(Integer id) {
		Orders order = findOrderById(id);
		order.setStatus("SHIPPED");
		return ordersMapper.toDTO(ordersRepository.save(order));
	}

	@Override
	public OrderResponseDTO cancelOrder(Integer id) {
		Orders order = findOrderById(id);
		order.setStatus("CANCELED");
		return ordersMapper.toDTO(ordersRepository.save(order));
	}

	private Orders findOrderById(Integer id) {
		return ordersRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
	}

	@Override
	public OrderResponseDTO cancelOrderByAdmin(Integer id) {
		Orders order = ordersRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng."));

		if (order.getStatus().equalsIgnoreCase("CANCELED") || order.getStatus().equalsIgnoreCase("DELIVERED")) {
			throw new NotFoundException("Đơn hàng đã hoàn tất hoặc bị huỷ trước đó.");
		}

		order.setStatus("CANCELED");
		ordersRepository.save(order);
		return ordersMapper.toDTO(order);
	}

}
