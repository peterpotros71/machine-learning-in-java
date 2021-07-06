package finalprojectwebservice.finalproject;

public class Consumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		apis consumer = new apis();
		
		// test each route
		consumer.readData("mostcompanies");
		consumer.readData("mosttitles");
		consumer.readData("mostareas");
		consumer.readData("get_skill");
		consumer.readData("mostcompaniespiechart");
		consumer.readData("mosttitlesbarchart");
		consumer.readData("mostareasbarchart");
		consumer.readData("kmeansclustering");	
		
	}

}
