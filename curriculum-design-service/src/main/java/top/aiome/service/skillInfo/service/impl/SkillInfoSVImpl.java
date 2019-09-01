package top.aiome.service.skillInfo.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import top.aiome.dao.skillInfo.SkillInfoMapper;
import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.dao.skillInfo.entity.SkillInfoExample;
import top.aiome.service.skillInfo.service.interfaces.ISkillInfoSV;

@Service
public class SkillInfoSVImpl implements ISkillInfoSV{
	@Resource
	private SkillInfoMapper skillInfoMapper;

	@Override
	public SkillInfo getSkillInfoById(Long id) {
		return skillInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SkillInfo> getSkillInfos(SkillInfoExample skillInfoExample) {
		return skillInfoMapper.selectByExample(skillInfoExample);
	}

	@Override
	public List<SkillInfo> get(SkillInfoExample skillInfoExample) {
		return skillInfoMapper.get(skillInfoExample);
	}

	@Override
	public int updateSkillInfoById(SkillInfo skillInfo) {
		return skillInfoMapper.updateByPrimaryKeySelective(skillInfo);
	}
	
	@Override
	public int updateSkillInfo(SkillInfo skillInfo, SkillInfoExample skillInfoExample) {
		return skillInfoMapper.updateByExampleSelective(skillInfo, skillInfoExample);
	}

	@Override
	public int insertSkillInfo(SkillInfo skillInfo) {
		return skillInfoMapper.insertSelective(skillInfo);
	}

	@Override
	public String getStartTime() {
		return skillInfoMapper.getStartTime();
	}

	@Override
	public void setStartTime(String time) {
		skillInfoMapper.setStartTime(time);
	}

	@Override
	public Long deleteSkillInfoById(Long id) {
		return skillInfoMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteSkillInfo(SkillInfoExample skillInfoExample) {
		return skillInfoMapper.deleteByExample(skillInfoExample);
	}
}
