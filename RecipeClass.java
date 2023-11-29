package calculMPackage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecipeClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6587565349980645871L;

	String name;
	int price;
	String[] item;
	int[] cost;
	int[] reference;
	transient int costTotal;
	transient int costVariable; 
	transient int marge;
	transient BigDecimal[] costInPercent;
	transient BigDecimal margeInPercent;
	transient BigDecimal margeVariableInPercent;
	transient int mediumCost;
	transient int[] MediumCostDiff;
	transient int tax;
	transient int priceWithoutTax;

	// use in save and compute the every recipe
	RecipeClass(String name, int price, int[] reference, String[] item, int[] cost) {
		this.name = name;
		this.price = price;
		this.reference = reference;
		this.item = item;
		this.cost = cost;
	}

	public void initialize() {
		tax = 10;
		priceWithoutTax = price - price / tax;
		costTotal = 0;
		for (int x : cost) {
			costTotal += x;
		}
		marge = priceWithoutTax - costTotal;
		costInPercent = new BigDecimal[cost.length];
		for (int i = 0; i < cost.length; i++) {
			BigDecimal a = new BigDecimal("100");
			BigDecimal b = new BigDecimal(cost[i]);
			BigDecimal c = new BigDecimal(costTotal);
			costInPercent[i] = a.multiply(b);
			costInPercent[i] = costInPercent[i].divide(c, 2, RoundingMode.HALF_EVEN);
		}
		BigDecimal a = new BigDecimal("100");
		BigDecimal b = new BigDecimal(marge);
		BigDecimal c = new BigDecimal(priceWithoutTax);
		margeInPercent = a.multiply(b);
		margeInPercent = margeInPercent.divide(c, 2, RoundingMode.HALF_EVEN);
		if (cost.length > 0) {
			mediumCost = costTotal / cost.length;
			MediumCostDiff = new int[cost.length];
			for (int i = 0; i < cost.length; i++) {
				MediumCostDiff[i] = cost[i] - mediumCost;
			}
		}
		for (int i = 0; i < reference.length; i++) {
			costVariable += cost[i];
		}
		int x = priceWithoutTax - costVariable;
		b = new BigDecimal(x);
		margeVariableInPercent = a.multiply(b);
		margeVariableInPercent = margeVariableInPercent.divide(c, 2, RoundingMode.HALF_EVEN);
	}
}
