package top.aiome.service.seckill.service.interfaces;

import java.util.List;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.dao.seckill.entity.SeckillExample;

/**
 * 	秒杀管理
 */
public interface ISeckillSV {
	/**
	 * 根据ID查询秒杀信息
	 * @param id 秒杀ID
	 * @return 秒杀实体
	 */
	public Seckill getSeckillById(Integer id);

	/**
	 * 查询秒杀信息
	 * @param seckillExample 查询条件
	 * @return 秒杀列表
	 */
	public List<Seckill> getSeckills(SeckillExample seckillExample);

	/**
	 * 根据ID更新秒杀信息
	 * @param seckill 秒杀信息
	 * @return 更新数量
	 */
	public int updateSeckillById(Seckill seckill); 
	
	/**
	 * 根据条件更新秒杀信息
	 * @param seckill  秒杀信息
	 * @param seckillExample  查询条件
	 * @return 更新数量
	 */
	public int updateSeckill(Seckill seckill, SeckillExample seckillExample); 
	
	/**
	 * 根据ID删除秒杀信息
	 * @param id 秒杀ID
	 * @return 删除数量
	 */
	public int deleteSeckillById(Integer id);
	
	/**
	 * 根据条件删除秒杀信息
	 * @param seckillExample 查询条件
	 * @return 删除数量
	 */
	public int deleteSeckill(SeckillExample seckillExample);
	
	/**
	 * 新增秒杀信息
	 * @param seckill 秒杀信息
	 * @return 新增数量
	 */
	public int insertSeckill(Seckill seckill);


	public boolean reduceInventory(Seckill seckill);
}


