package com.Sct.gamebone;

public class EvaluationSystem {

	public static int evaluate(int level, int cost, int rest_time) {
		if (cost < 100)
			return 3;
		if (cost < 200)
			return 2;
		return 1;
	}
}
