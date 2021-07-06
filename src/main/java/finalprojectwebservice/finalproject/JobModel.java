package finalprojectwebservice.finalproject;

public class JobModel {
	
	private String title;
	private String level;
	private String exp;
	private String location;
	private String country;
	private String type;
	private String skills;
	private String company;
	
	public JobModel(String company, String title, String level, String exp, String location, String country, String type,
			String skills) {
		super();
		this.company = company;
		this.title = title;
		this.level = level;
		this.exp = exp;
		this.location = location;
		this.country = country;
		this.type = type;
		this.skills = skills;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
}
