package finalprojectwebservice.finalproject;

import java.util.LinkedHashMap;

import smile.data.DataFrame;

public interface JobDaoInterface {
	
	public DataFrame read_csv(String filename);
	
	public LinkedHashMap<String, Long> get_most_frequent(String file_name, String cloumn_name, int limit);
	
	public LinkedHashMap<String, Long> get_most_skills(String file_name, String cloumn_name, int limit);

}
