package top.aiome.service.updateSeniority.service.interfaces;

import java.util.List;

/**
 * Created by mahongyan on 2018/3/24
 */
public interface IUpdateSenioritySV {
    public List<UpdateSeniority> getMysqlData();
    public List<UpdateSeniority> getRedisData();
    public int setRedisDate(List<UpdateSeniority> list);
    public int setMysqlDate(List<UpdateSeniority> list);

}
