package Util;

/**
 * 时间类
 */
public class Time {
    public static float timeStarted = System.nanoTime();

    /**
     * 获取时间
     * @return 从应用开始到现在的时间流逝
     */
    public static float getTime(){
        return (float) ((System.nanoTime() - timeStarted)*1E-9);
    }
}
