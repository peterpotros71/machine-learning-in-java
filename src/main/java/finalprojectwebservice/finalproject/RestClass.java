package finalprojectwebservice.finalproject;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;

import smile.data.DataFrame;

@Controller
public class RestClass {
	
	
	private String most_companies = "";
	private String most_titles = "";
	private String most_areas = "";
	private String most_skills = "";
	
	JobDaoClass dao = new JobDaoClass();
	
	// show the data of most demanding companies for jobs in table
	@RequestMapping("/mostcompanies")
	@ResponseBody
	public String mostcompanies(@RequestParam(value="limit",defaultValue="20") int limit) throws IOException {
		
		// get sorted map of most demanding companies for jobs 
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Company", limit); 
		
		// create table by HTML tags to show the data
        most_companies ="<table border=\"1\" width=\"500px\" height=\"auto\">"
				+ "  <tr>\r\n"
				+ "    <th>Company</th>\r\n"
				+ "    <th colspan=\"2\">No. of jobs</th>\r\n"
				+ "  </tr>\r\n";
        
        sortedMap.forEach((key, value) ->
        most_companies +=
		"  <tr>\r\n"
		+ "    <td>"+ key+"</td>\r\n"
		+ "    <td>"+ value+"</td>\r\n"
        		);
        
       return most_companies;
	}
	
	// show the data of most demanding companies for jobs in pie chart
	@RequestMapping("/mostcompaniespiechart")
	public String mostcompaniespiechart(Model model, @RequestParam(value="limit",defaultValue="10") int limit) throws IOException {
		
		// get sorted map of most demanding companies for jobs 
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Company", limit);
 
        JSONArray ja = new JSONArray();
        sortedMap.forEach((key, value) ->{
           JSONObject jo = new JSONObject();
    	   jo.put("name", key);
    	   jo.put("y", value);
    	   ja.add(jo); 
    	   }
        );
        
        // pass JSONArray to HTML file using Model to load the data using javascript
        model.addAttribute("ja", ja);
        model.addAttribute("title", "Most demanding companies for jobs");
        return "pieChart";		
	}
	
	// show the data of the most job titles in table
	@RequestMapping("/mosttitles")
	@ResponseBody
	public String mosttitles(@RequestParam(value="limit",defaultValue="20") int limit) throws IOException {
		
		// get sorted map of most job titles
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Title", limit);
		
		// create table by HTML tags to show the data
		most_titles ="<table border=\"1\" width=\"500px\" height=\"auto\">"
				+ "  <tr>\r\n"
				+ "    <th>Title</th>\r\n"
				+ "    <th colspan=\"2\">No. of jobs</th>\r\n"
				+ "  </tr>\r\n";
        
        sortedMap.forEach((key, value) ->
        most_titles +=
		"  <tr>\r\n"
		+ "    <td>"+ key+"</td>\r\n"
		+ "    <td>"+ value+"</td>\r\n"
        		);
        
       return most_titles;
	}
	
	// show the data of the most job titles in bar chart
	@RequestMapping("/mosttitlesbarchart")
	public String mosttitlesbarchart(Model model, @RequestParam(value="limit",defaultValue="20") int limit) throws IOException {
		
		// get sorted map of most job titles
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Title", limit);
        
		// pass JSONArray to HTML file using Model to load the data using javascript
		model.addAttribute("sortedMap", sortedMap);
        model.addAttribute("title", "Most Job Titles");
        model.addAttribute("x_axis", "Title");
		
        return "barGraph";
	}
	
	// show the data of the most job locations in table
	@RequestMapping("/mostareas")
	@ResponseBody
	public String mostareas(@RequestParam(value="limit",defaultValue="20") int limit) throws IOException {
		
		// get sorted map of most job locations
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Location", limit);
		
		// create table by HTML tags to show the data
		most_areas ="<table border=\"1\" width=\"500px\" height=\"auto\">"
				+ "  <tr>\r\n"
				+ "    <th>Area</th>\r\n"
				+ "    <th colspan=\"2\">No. of jobs</th>\r\n"
				+ "  </tr>\r\n";
        
        sortedMap.forEach((key, value) ->
        most_areas +=
		"  <tr>\r\n"
		+ "    <td>"+ key+"</td>\r\n"
		+ "    <td>"+ value+"</td>\r\n"
        		);
		
       return most_areas;
	}
	
	// show the data of the most job locations in bar chart
	@RequestMapping("/mostareasbarchart")
	public String mostareasbarchart(Model model, @RequestParam(value="limit",defaultValue="10") int limit) throws IOException {
		
		// get sorted map of most job locations
		LinkedHashMap<String, Long> sortedMap =  dao.get_most_frequent("Wuzzuf_Jobs.csv", "Location", limit);
       
		// pass JSONArray to HTML file using Model to load the data using javascript
		model.addAttribute("sortedMap", sortedMap);
        model.addAttribute("title", "Most Job Locations");
        model.addAttribute("x_axis", "Location");
		
        return "barGraph";
	}
	
	// show the data of the most job skills in table
	@RequestMapping("/get_skill")
	@ResponseBody
	public String skills(@RequestParam(value="limit",defaultValue="20") int limit) throws IOException {
		
		// get sorted map of most job locations
	    LinkedHashMap<String, Long> sortedMap =  dao.get_most_skills("Wuzzuf_Jobs.csv", "Skill", limit);
				
		//Set<Entry<String, Long>> entries = sortedMap.entrySet();
        //Map.Entry<String, Long>[] entryArray = entries.toArray( new Map.Entry[entries.size()] );
        
        most_skills ="<table border=\"1\" width=\"500px\" height=\"auto\">"
				+ "  <tr>\r\n"
				+ "    <th>Skill</th>\r\n"
				+ "    <th colspan=\"2\">Count</th>\r\n"
				+ "  </tr>\r\n";
        
        sortedMap.forEach((key, value) ->
        most_skills +=
			"  <tr>\r\n"
			+ "    <td>"+ key+"</td>\r\n"
			+ "    <td>"+ value+"</td>\r\n"
        		);
        
		return most_skills;
	}
	
	// show kmeans clustering results 
	@RequestMapping("/kmeansclustering")
	public String kmeansclustering(Model model) {
		DataFrame trainData = Kmeans.train_model();
		double[][] arr_class0 = DataFrame.of(trainData.stream().filter(t -> t.get("Class").equals(0))).select("Company_encoded", "Title_encoded").toArray();
		System.out.println("class0: " + arr_class0.length);
		double[][] arr_class1 = DataFrame.of(trainData.stream().filter(t -> t.get("Class").equals(1))).select("Company_encoded", "Title_encoded").toArray();
		System.out.println("class1: " + arr_class1.length);
		double[][] arr_class2 = DataFrame.of(trainData.stream().filter(t -> t.get("Class").equals(2))).select("Company_encoded", "Title_encoded").toArray();
		System.out.println("class2: " + arr_class2.length);
		
		JSONArray ja = new JSONArray();
        for(int i=0; i<=2; i++) {
        	JSONObject jo = new JSONObject();
        	if(i==0) {
        		jo.put("name", "class 0");
        		jo.put("color", "rgba(21, 20, 150, .5)");
            	jo.put("data", arr_class0);
        	   }
        	if(i==1) {
        		jo.put("name", "class 1");
        		jo.put("color", "rgba(175, 37, 32, .5)");
            	jo.put("data", arr_class1);
        	}
        	if(i==2) {
        		jo.put("name", "class 2");
        		jo.put("color", "rgba(78, 130, 0, .5)");
            	jo.put("data", arr_class2);
        	}
        	ja.add(jo); 
    		}
     
        model.addAttribute("ja", ja);
        model.addAttribute("title", "Most demanding companies for jobs");
        return "sctterplot";		
	}
	
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello peter";
	}

	
}
