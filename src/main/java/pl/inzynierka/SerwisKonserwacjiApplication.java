package pl.inzynierka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.inzynierka.FuzzyLogic.Service.FuzzyLogicService;

@SpringBootApplication
@EnableScheduling
public class SerwisKonserwacjiApplication {

	@Autowired
	FuzzyLogicService fuzzyLogicService;

	public static void main(String[] args) {
		SpringApplication.run(SerwisKonserwacjiApplication.class, args);
	}

	@Scheduled(fixedRate = 500000, initialDelay = 2000)
//	@Scheduled(cron = "0 0 6 * * *")
	public void refreshFuzzyLogic(){
		fuzzyLogicService.refreshFuzzyLogic();
	}
}
