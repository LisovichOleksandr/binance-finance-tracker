package by.lisovich.binance_finance_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BinanceFinanceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinanceFinanceTrackerApplication.class, args);
	}

}
