package top.aiome.service.order.service.interfaces;

import java.util.List;
import top.aiome.dao.order.entity.Order;
import top.aiome.dao.order.entity.OrderExample;

/**
 * 	订单管理
 */
public interface IOrderSV {
	/**
	 * 根据ID查询订单信息
	 * @param id 订单ID
	 * @return 订单实体
	 */
	public Order getOrderById(Integer id);

	/**
	 * 查询订单信息
	 * @param orderExample 查询条件
	 * @return 订单列表
	 */
	public List<Order> getOrders(OrderExample orderExample);

	/**
	 * 根据ID更新订单信息
	 * @param order 订单信息
	 * @return 更新数量
	 */
	public int updateOrderById(Order order); 
	
	/**
	 * 根据条件更新订单信息
	 * @param order  订单信息
	 * @param orderExample  查询条件
	 * @return 更新数量
	 */
	public int updateOrder(Order order, OrderExample orderExample); 
	
	/**
	 * 根据ID删除订单信息
	 * @param id 订单ID
	 * @return 删除数量
	 */
	public int deleteOrderById(Integer id);
	
	/**
	 * 根据条件删除订单信息
	 * @param orderExample 查询条件
	 * @return 删除数量
	 */
	public int deleteOrder(OrderExample orderExample);
	
	/**
	 * 新增订单信息
	 * @param order 订单信息
	 * @return 新增数量
	 */
	public int insertOrder(Order order);
	
}


