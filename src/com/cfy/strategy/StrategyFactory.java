package com.cfy.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StrategyFactory {

	private Map<Integer, Strategy> map;

	public StrategyFactory() {

		List<Strategy> strategies = new ArrayList<>();

		strategies.add(new OrdinaryStrategy());
		strategies.add(new SilverStrategy());
		strategies.add(new GoldStrategy());
		strategies.add(new PlatinumStrategy());

		map = strategies.stream().collect(Collectors.toMap(Strategy::getType, strategy -> strategy));
	}

	public static class Holder {
		public static StrategyFactory instance = new StrategyFactory();

		private Holder() {
			super();
		}
	}

	public static StrategyFactory getInstance() {
		return Holder.instance;
	}

	public Strategy get(Integer type) {
		return map.get(type);
	}
}