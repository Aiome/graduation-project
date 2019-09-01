package top.aiome.dao.skillInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import top.aiome.dao.skillInfo.entity.SkillInfo;
import top.aiome.dao.skillInfo.entity.SkillInfoExample;

public interface SkillInfoMapper {
    long countByExample(SkillInfoExample example);

    int deleteByExample(SkillInfoExample example);

    Long deleteByPrimaryKey(Long id);

    int insert(SkillInfo record);

    int insertSelective(SkillInfo record);

    List<SkillInfo> selectByExample(SkillInfoExample example);

    List<SkillInfo> get(SkillInfoExample example);

    SkillInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SkillInfo record, @Param("example") SkillInfoExample example);

    int updateByExample(@Param("record") SkillInfo record, @Param("example") SkillInfoExample example);

    int updateByPrimaryKeySelective(SkillInfo record);

    int updateByPrimaryKey(SkillInfo record);

    String getStartTime();

    void setStartTime(String time);
}