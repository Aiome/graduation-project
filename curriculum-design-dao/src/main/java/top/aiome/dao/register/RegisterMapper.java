package top.aiome.dao.register;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import top.aiome.dao.register.entity.Register;
import top.aiome.dao.register.entity.RegisterExample;

public interface RegisterMapper {
    long countByExample(RegisterExample example);

    int deleteByExample(RegisterExample example);

    Long deleteByPrimaryKey(Long id);

    int insert(Register record);

    int insertSelective(Register record);

    List<Register> selectByExample(RegisterExample example);

    Register selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Register record, @Param("example") RegisterExample example);

    int updateByExample(@Param("record") Register record, @Param("example") RegisterExample example);

    int updateByPrimaryKeySelective(Register record);

    int updateByPrimaryKey(Register record);
}