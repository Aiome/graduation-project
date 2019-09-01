package top.aiome.service.register.service.interfaces;

import java.util.List;
import top.aiome.dao.register.entity.Register;
import top.aiome.dao.register.entity.RegisterExample;

/**
 * 	登记信息管理
 */
public interface IRegisterSV {
	public Boolean all(String phone,String smsCode, String tag, Integer target) throws Exception;
	/**
	 * 根据ID查询登记信息信息
	 * @param id 登记信息ID
	 * @return 登记信息实体
	 */
	public Register getRegisterById(Long id);

	/**
	 * 查询登记信息信息
	 * @param registerExample 查询条件
	 * @return 登记信息列表
	 */
	public List<Register> getRegisters(RegisterExample registerExample);

	/**
	 * 根据ID更新登记信息信息
	 * @param register 登记信息信息
	 * @return 更新数量
	 */
	public int updateRegisterById(Register register); 
	
	/**
	 * 根据条件更新登记信息信息
	 * @param register  登记信息信息
	 * @param registerExample  查询条件
	 * @return 更新数量
	 */
	public int updateRegister(Register register, RegisterExample registerExample); 
	
	/**
	 * 根据ID删除登记信息信息
	 * @param id 登记信息ID
	 * @return 删除数量
	 */
	public Long deleteRegisterById(Long id);
	
	/**
	 * 根据条件删除登记信息信息
	 * @param registerExample 查询条件
	 * @return 删除数量
	 */
	public int deleteRegister(RegisterExample registerExample);
	
	/**
	 * 新增登记信息信息
	 * @param register 登记信息信息
	 * @return 新增数量
	 */
	public int insertRegister(Register register);
	
}


