package top.aiome.service.groupPurchase.service.interfaces;

import java.util.List;

/**
 * Created by mahongyan on 2018/3/24
 */
public interface IGroupPurchaseSV {
    public List<GroupInfo> getGroupInfo(int itemId);
    public void buyByGroupId(int groupId);
}
