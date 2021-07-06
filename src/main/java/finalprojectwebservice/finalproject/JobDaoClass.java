package finalprojectwebservice.finalproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;

public class JobDaoClass implements JobDaoInterface{
	
	private DataFrame df = null;
	
	// function to read the data from csv file
	@Override
	public DataFrame read_csv(String filename) {
    	try {
    		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
    		df = Read.csv(filename, format);
    		df = df.omitNullRows();
    		df = DataFrame.of(df.stream().distinct());
    	    //System.out.println(df);
    		}
    		catch(Exception ex){	
    		}
    	return df;
	}
	
	// function to get sorted map of any column in DataFrame
	@Override
	public LinkedHashMap<String, Long> get_most_frequent(String file_name, String cloumn_name, int limit) {
		
		// check if the data is already loaded or not if not then read it from csv file
		if(df == null) {
			df = read_csv(file_name);
		}
		
		// grouping the column and counting their frequencies
		Map<String,Long> map = df.stream().collect(Collectors.groupingBy(t -> t.getString(cloumn_name), Collectors.counting()));
		
		// sort the map by number of jobs
		LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(limit)
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
   
        return sortedMap;
	}
	
	// function to get sorted map of Skill column
	@Override
	public LinkedHashMap<String, Long> get_most_skills(String file_name, String cloumn_name, int limit){

		// check if the data is already loaded or not if not then read it from csv file
		if(df == null) {
			df = read_csv(file_name);
		}
		
		DataFrame skill_df= df.select("Skills");

		// iterate over Skill column and split the skills from each row then merge all of them in one list
		Iterator<Tuple> k = skill_df.stream().iterator();
		List <String> all_sk = new ArrayList<>();
		
		for(int i=0;i<skill_df.nrows();i++) {
			String skill=k.next().getString("Skills");
			String[] sk=skill.split(",");
			for(String j:sk) {
				all_sk.add(j);
			}	
		}
		
		// grouping the column and counting their frequencies
		Map<String,Long> map = all_sk.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
		
		// sort the map by number of jobs
		LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(limit)
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        
        return sortedMap;
	}
	

}
