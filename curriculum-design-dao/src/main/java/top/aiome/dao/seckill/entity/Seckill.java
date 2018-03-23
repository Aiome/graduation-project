package top.aiome.dao.seckill.entity;

import java.util.Date;

public class Seckill {
    private Integer id;

    private Integer itemId;

    private Date start;

    private Date end;

    private Integer seckillSwitch;

    private Integer seckillCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getSeckillSwitch() {
        return seckillSwitch;
    }

    public void setSeckillSwitch(Integer seckillSwitch) {
        this.seckillSwitch = seckillSwitch;
    }

    public Integer getSeckillCount() {
        return seckillCount;
    }

    public void setSeckillCount(Integer seckillCount) {
        this.seckillCount = seckillCount;
    }
}