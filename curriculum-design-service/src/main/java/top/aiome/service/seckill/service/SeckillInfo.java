package top.aiome.service.seckill.service;

/**
 * Created by mahongyan on 2018/3/23
 */
public class SeckillInfo {
    private Integer seckillId;
    private Integer itemId;
    private Integer userId;


    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SeckillInfo{" +
                "seckillId=" + seckillId +
                ", itemId=" + itemId +
                ", userId=" + userId +
                '}';
    }
}
