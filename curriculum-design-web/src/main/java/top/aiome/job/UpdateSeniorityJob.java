package top.aiome.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import top.aiome.service.updateSeniority.service.interfaces.IUpdateSenioritySV;
import top.aiome.service.updateSeniority.service.interfaces.UpdateSeniority;

public class UpdateSeniorityJob{
	
	protected final transient Logger log = LoggerFactory.getLogger(UpdateSeniorityJob.class);

	public static boolean isRuning = false;

	@Autowired
	private IUpdateSenioritySV updateSenioritySVImpl;

	protected void executeInternal(){
		log.info("更新首页商品开始");

		//获取mysql商品销量信息
		List<UpdateSeniority> mysqlResult = updateSenioritySVImpl.getMysqlData();
		//获取redis商品点击次数
		List<UpdateSeniority> redisResult = updateSenioritySVImpl.getRedisData();
		//以mysql数据为基准计算商品分数
		for (UpdateSeniority mysql:mysqlResult){
			for (UpdateSeniority redis:redisResult){
				if (mysql.getId() == redis.getId()){
					mysql.setScore(mysql.getCount()*0.3 + redis.getCount()*0.7);
				}
			}
		}
		//将结果排序
		Collections.sort(mysqlResult, new Comparator<UpdateSeniority>() {
			@Override
			public int compare(UpdateSeniority o1, UpdateSeniority o2) {
				if (o1.getScore() < o2.getScore()){ return -1;
				}else if (o1.getScore() > o2.getScore()){
					return 1;
				}else{
					return 0;
				}
			}
		});
		//将结果刷入Redis缓存
		int redisCount = updateSenioritySVImpl.setRedisDate(mysqlResult);
		//将结果实例至Mysql
		int mysqlCount = updateSenioritySVImpl.setMysqlDate(mysqlResult);
		log.info("更新redis数据："+ redisCount + "条 mysql数据：" + mysqlCount + "条");
		log.info("更新首页商品结束");

	}
}
