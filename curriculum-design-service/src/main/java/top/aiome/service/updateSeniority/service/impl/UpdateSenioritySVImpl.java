package top.aiome.service.updateSeniority.service.impl;

import java.util.List;

import top.aiome.service.updateSeniority.service.interfaces.IUpdateSenioritySV;
import top.aiome.service.updateSeniority.service.interfaces.UpdateSeniority;

/**
 * Created by mahongyan on 2018/3/24
 */
public class UpdateSenioritySVImpl implements IUpdateSenioritySV{
    @Override
    public List<UpdateSeniority> getMysqlData() {
        return null;
    }

    @Override
    public List<UpdateSeniority> getRedisData() {
        return null;
    }

    @Override
    public int setRedisDate(List<UpdateSeniority> list) {
        return 0;
    }

    @Override
    public int setMysqlDate(List<UpdateSeniority> list) {
        return 0;
    }
}
