package cn.xuyi.shoot;
/** 奖励 */
public interface Award {
	public int DOUBLE_FIRE = 0; //火力值
	public int LIFE = 1; //命
	/** 获取奖励类型  0为火力值 1为命 */
	public int getType();
}
