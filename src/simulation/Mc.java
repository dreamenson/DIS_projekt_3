package simulation;

import OSPABA.*;

public class Mc extends OSPABA.IdList
{
	//meta! userInfo="Generated code: do not modify", tag="begin"
	public static final int makeOrder = 1006;
	public static final int makeProduct = 1007;
	public static final int prepareAndCut = 1008;
	public static final int assembly = 1009;
	public static final int mordantAndVarnish = 1010;
	public static final int releasePlace = 1016;
	public static final int init = 1002;
	public static final int orderArrive = 1003;
	public static final int assignPlace = 1005;
	public static final int productStarted = 1019;
	public static final int armourA = 1017;
	public static final int armourC = 1018;
	//meta! tag="end"

	// 1..1000 range reserved for user
	public static final int newOrder = 1;
	public static final int prepareEnd = 2;
	public static final int cutEnd = 3;
	public static final int mordantEnd = 4;
	public static final int varnishEnd = 5;
	public static final int assemblyEnd = 6;
	public static final int armourCEnd = 7;
	public static final int armourAEnd = 8;
	public static final int newProcEnd = 9;
}
