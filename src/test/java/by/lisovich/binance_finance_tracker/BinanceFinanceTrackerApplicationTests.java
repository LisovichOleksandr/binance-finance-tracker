package by.lisovich.binance_finance_tracker;

import by.lisovich.binance_finance_tracker.controller.PriceController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BinanceFinanceTrackerApplicationTests {

	@Autowired private PriceController priceController;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(priceController).isNotNull();
	}

}
