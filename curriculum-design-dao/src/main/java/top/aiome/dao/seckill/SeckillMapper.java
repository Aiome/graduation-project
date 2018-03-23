package top.aiome.dao.seckill;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.dao.seckill.entity.SeckillExample;

public interface SeckillMapper {
    long countByExample(SeckillExample example);

    int deleteByExample(SeckillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Seckill record);

    int insertSelective(Seckill record);

    List<Seckill> selectByExample(SeckillExample example);

    Seckill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Seckill record, @Param("example") SeckillExample example);

    int updateByExample(@Param("record") Seckill record, @Param("example") SeckillExample example);

    int updateByPrimaryKeySelective(Seckill record);

    int updateByPrimaryKey(Seckill record);
}