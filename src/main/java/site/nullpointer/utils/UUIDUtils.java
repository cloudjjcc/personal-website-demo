package site.nullpointer.utils;

import org.hibernate.id.UUIDGenerator;

import java.util.UUID;

/**
 * @author wangjc
 * @Title: UUIDUtils
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/1712:04
 */
public class UUIDUtils {
    /**
     * 获取uuid
     * @return
     */
    public static String getUUIDStr(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取uuid没有横线
     * @return
     */
    public static String getUUIDStrNoLine(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
