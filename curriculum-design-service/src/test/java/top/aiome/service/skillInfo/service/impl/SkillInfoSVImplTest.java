package top.aiome.service.skillInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import top.aiome.service.skillInfo.service.interfaces.ISkillInfoSV;

/**
 * Description: <br>
 * Copyright: Copyright (c) 2019<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author mahongyan 2019/3/12 20:14
 */
@ContextConfiguration("classpath:")
public class SkillInfoSVImplTest {

    @Autowired
    private ISkillInfoSV skillInfoSVImpl;

    @org.testng.annotations.Test
    public void testGetSkillInfoById() {
        skillInfoSVImpl.getStartTime();
    }
}