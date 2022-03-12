import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//print no of courses returned by API
		int totalCourses = js.getInt("courses.size()");

		System.out.println("no of courses = " + totalCourses);
		
		//2.Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount = " + totalAmount);
		
		//3. Print Title of the first course
		String titleFirst = js.getString("courses[0].title");
		System.out.println("Title of the first course : " + titleFirst);
		
		//4. Print All course titles and their respective Prices
		for(int i=0; i<totalCourses; i++) {
			String title = js.getString("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			System.out.println("Title of "+i+ " course is " +title + " and price of " +i+ " course is " +price);
		}
		
		//5. Print no of copies sold by RPA Course
		for(int i=0; i<totalCourses; i++) {
			String title = js.getString("courses["+i+"].title");
			int copies = js.getInt("courses["+i+"].copies");
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println("Number of copies sold by RPA courses is = " +copies);
				break;
			}
		}
		
		int totalPriceOfAllCourses = 0;
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		for(int i=0; i<totalCourses; i++) {
			String title = js.getString("courses["+i+"].title");
			int copies = js.getInt("courses["+i+"].copies");
			int price = js.getInt("courses["+i+"].price");
			totalPriceOfAllCourses = totalPriceOfAllCourses + (copies * price);			
		}
		if(totalAmount == totalPriceOfAllCourses) {
			System.out.println("Total of all courses price is matched with dashboard price");
		}
		else {
			System.out.println("Total of all courses price is not matched with dashboard price");
		}
		
		
	}

}
