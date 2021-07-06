package finalprojectwebservice.finalproject;

import java.util.LinkedHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import smile.data.DataFrame;
import smile.data.vector.DoubleVector;

@SpringBootApplication
public class FinalProjectApplication implements WebMvcConfigurer{
	
	static DataFrame df = null;
	static String most_skills = "";
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/home").setViewName("home");
	}
	
	public static void main(String[] args) {
		
		// run the web service application by spring boot
		SpringApplication.run(FinalProjectApplication.class, args);	
		
		JobDaoClass dao = new JobDaoClass();
		if(df == null) {
			df = dao.read_csv("Wuzzuf_Jobs.csv");
		}
		
		df = df.merge (DoubleVector.of ("YearsExp_encoded", Kmeans.encodeCategory(df, "YearsExp")));
		
		System.out.println(df);
        System.out.println(df.structure());
        System.out.println(df.summary());
        System.out.println(df.nrows());
        
        LinkedHashMap<String, Long> sortedMap = dao.get_most_skills("Wuzzuf_Jobs.csv", "Skill" , 30);
        most_skills = "";
        sortedMap.forEach((key, value) ->
        System.out.println("skill: " + key + "      no of jobs: " + value)
        );
        
	}
	

}
