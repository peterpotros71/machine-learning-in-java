package finalprojectwebservice.finalproject;

import java.lang.reflect.InvocationTargetException;

import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;
import smile.data.DataFrame;
import smile.data.measure.NominalScale;
import smile.data.vector.DoubleVector;
import smile.data.vector.IntVector;

import smile.plot.swing.ScatterPlot;

public class Kmeans {
	
	private static KMeans clusters;
	private static double[][] arr2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// train the model
		train_model();
		
		// get array of classes
		int[] custer = clusters.y;
		
		// predict the class of one point
		System.out.println("The predicted cluster " + clusters.predict(new double[]{1, 2}));
		
		// scatter plot of the each point versus its class
		try {
			ScatterPlot.of(arr2, custer, '.').canvas()
			//ScatterPlot.of(trainData, "Title_encoded", "Company_encoded", "Class", '*').canvas()
			.setAxisLabels("company encoded", "title encoded").setTitle("Kmeans clustering")
			.window();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// function to encode any string column to numerical value
	public static double[] encodeCategory(DataFrame df, String columnName) {
		
		String[] values = df.stringVector (columnName).distinct ().toArray (new String[]{});
		double[] pclassValues = df.stringVector (columnName).factorize (new NominalScale (values)).toDoubleArray();
		
		return pclassValues;
	}	
		

	public static DataFrame train_model() {
		
		JobDaoClass dao = new JobDaoClass ();
		DataFrame trainData = dao.read_csv("Wuzzuf_Jobs.csv"); 
		trainData = trainData.merge (DoubleVector.of ("Title_encoded", encodeCategory (trainData, "Title"))).merge(DoubleVector.of ("Company_encoded", encodeCategory (trainData, "Company")));
		System.out.println ("=======Encoding Non Numeric Data==============");
		System.out.println (trainData.select("Title", "Title_encoded","Company", "Company_encoded"));
		//System.out.println (trainData.structure ());
		//System.out.println (trainData.summary ());
		
		// convert the two columns Company and Title to 2D double array
		arr2  = trainData.select("Company_encoded", "Title_encoded").toArray();
		
		// Training the model for 20 iterations and generate clusters for each point
		clusters = PartitionClustering.run(20, () -> KMeans.fit(arr2, 3));
		
		// merge the generated clusters to the Datafrme as Class column
		trainData = trainData.merge(IntVector.of ("Class", clusters.y));
		
		return trainData;
		
	}
	

}
