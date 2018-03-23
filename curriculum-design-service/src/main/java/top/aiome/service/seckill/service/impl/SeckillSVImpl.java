package top.aiome.service.seckill.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import top.aiome.dao.seckill.SeckillMapper;
import top.aiome.dao.seckill.entity.Seckill;
import top.aiome.dao.seckill.entity.SeckillExample;
import top.aiome.service.seckill.service.interfaces.ISeckillSV;

@Service
public class SeckillSVImpl implements ISeckillSV {
	@Resource
	private SeckillMapper seckillMapper;

	@Override
	public Seckill getSeckillById(Integer id) {
		return seckillMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Seckill> getSeckills(SeckillExample seckillExample) {
		return seckillMapper.selectByExample(seckillExample);
	}

	@Override
	public int updateSeckillById(Seckill seckill) {
		return seckillMapper.updateByPrimaryKeySelective(seckill);
	}
	
	@Override
	public int updateSeckill(Seckill seckill, SeckillExample seckillExample) {
		return seckillMapper.updateByExampleSelective(seckill, seckillExample);
	}

	@Override
	public int insertSeckill(Seckill seckill) {
		return seckillMapper.insertSelective(seckill);
	}

	@Override
	public int deleteSeckillById(Integer id) {
		return seckillMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteSeckill(SeckillExample seckillExample) {
		return seckillMapper.deleteByExample(seckillExample);
	}
}
