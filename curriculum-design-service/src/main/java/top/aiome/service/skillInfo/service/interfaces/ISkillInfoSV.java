package top.aiome.service.skillInfo.service.interfaces;

import java.util.List;
import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.dao.skillInfo.entity.SkillInfoExample;

/**
 * 	状态等信息管理
 */
public interface ISkillInfoSV {
	/**
	 * 根据ID查询状态等信息信息
	 * @param id 状态等信息ID
	 * @return 状态等信息实体
	 */
	public SkillInfo getSkillInfoById(Long id);

	/**
	 * 查询状态等信息信息
	 * @param skillInfoExample 查询条件
	 * @return 状态等信息列表
	 */
	public List<SkillInfo> getSkillInfos(SkillInfoExample skillInfoExample);

	public List<SkillInfo> get(SkillInfoExample skillInfoExample);

	/**
	 * 根据ID更新状态等信息信息
	 * @param skillInfo 状态等信息信息
	 * @return 更新数量
	 */
	public int updateSkillInfoById(SkillInfo skillInfo); 
	
	/**
	 * 根据条件更新状态等信息信息
	 * @param skillInfo  状态等信息信息
	 * @param skillInfoExample  查询条件
	 * @return 更新数量
	 */
	public int updateSkillInfo(SkillInfo skillInfo, SkillInfoExample skillInfoExample); 
	
	/**
	 * 根据ID删除状态等信息信息
	 * @param id 状态等信息ID
	 * @return 删除数量
	 */
	public Long deleteSkillInfoById(Long id);
	
	/**
	 * 根据条件删除状态等信息信息
	 * @param skillInfoExample 查询条件
	 * @return 删除数量
	 */
	public int deleteSkillInfo(SkillInfoExample skillInfoExample);
	
	/**
	 * 新增状态等信息信息
	 * @param skillInfo 状态等信息信息
	 * @return 新增数量
	 */
	public int insertSkillInfo(SkillInfo skillInfo);

	String getStartTime();

	void setStartTime(String time);
	
}


