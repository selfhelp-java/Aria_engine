package Util;

/**
 * ʱ����
 */
public class Time {
    public static float timeStarted = System.nanoTime();

    /**
     * ��ȡʱ��
     * @return ��Ӧ�ÿ�ʼ�����ڵ�ʱ������
     */
    public static float getTime(){
        return (float) ((System.nanoTime() - timeStarted)*1E-9);
    }
}
