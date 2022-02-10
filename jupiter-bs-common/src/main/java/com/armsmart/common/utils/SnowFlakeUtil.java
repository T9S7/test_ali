package com.armsmart.common.utils;

import java.util.UUID;


public class SnowFlakeUtil {
    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;
    /**
     * 序列号占用的位数  每一部分占用的位数，就三个  最大值为:2^12 = 4095
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数 最大值为:2^5 = 31
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数  最大值为:2^5 = 31
     */
    private final static long DATA_CENTER_BIT = 5;
    /**
     * 每一部分最大值
     */
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移 12
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    /**
     * 数据中心左边占位数  SEQUENCE_BIT = 12  MACHINE_BIT = 5  总共 17
     */
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /**
     * 时间戳左边的长度 = 数据中心长度 + 数据中心比特位 =  17 + 5
     */
    private final static long TIMESTMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;
    /**
     * 数据中心
     */
    private long dataCenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;

    public SnowFlakeUtil(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currentTimeMillis = getCurrentTimeMillis();
        if (currentTimeMillis < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currentTimeMillis == lastStmp) {
            //if条件里表示当前调用和上一次调用落在了相同毫秒内，只能通过第三部分，序列号自增来判断为唯一，所以+1.
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大，只能等待下一个毫秒
            if (sequence == 0L) {
                currentTimeMillis = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            //执行到这个分支的前提是currTimestamp > lastTimestamp，说明本次调用跟上次调用对比，已经不再同一个毫秒内了，这个时候序号可以重新回置0了。
            sequence = 0L;
        }

        lastStmp = currentTimeMillis;
        //就是用相对毫秒数、机器ID和自增序号拼接
        return (currentTimeMillis - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT      //数据中心部分
                | machineId << MACHINE_LEFT            //机器标识部分
                | sequence;                            //序列号部分
    }

    private long getNextMill() {
        long mill = getCurrentTimeMillis();
        while (mill <= lastStmp) {
            mill = getCurrentTimeMillis();
        }
        return mill;
    }

    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(1,1);
        SnowFlakeUtil snowFlakeUtil2 = new SnowFlakeUtil(31,31);
        System.out.println(snowFlakeUtil.nextId());
        System.out.println(snowFlakeUtil2.nextId());
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }
}
