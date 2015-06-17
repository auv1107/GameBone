package com.Sct.gamebone;

import com.Sct.gamebone.StageData.MapInfo;

public class EvaluationSystem {

	public static int evaluate(int level, int coin, int rest_time) {
		MapInfo minfo = StageData.getInstance().getMapInfo();
		if (coin >= minfo.star3)
			return 3;
		if (coin >= minfo.star2)
			return 2;
		return 1;
	}
}
