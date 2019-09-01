package top.aiome.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import top.aiome.service.skillInfo.service.interfaces.ISkillInfoSV;

public class UpdateTime {
	
	protected final transient Logger log = LoggerFactory.getLogger(UpdateTime.class);

	public static boolean isRuning = false;

	@Autowired
	private ISkillInfoSV skillInfoSVImpl;

	protected void executeInternal(){
		log.info("更新抢券时间开始");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 200);

		skillInfoSVImpl.setStartTime(cal.getTime().getTime()+"");

		log.info("更新抢券时间开始结束 " + cal.getTime().getTime());
	}
}
