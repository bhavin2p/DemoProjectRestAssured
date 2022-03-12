import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(payload.CoursePrice());
		int totalCourses = js.getInt("courses.size()");
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		int totalPriceOfAllCourses = 0;
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		for(int i=0; i<totalCourses; i++) {
			int copies = js.getInt("courses["+i+"].copies");
			int price = js.getInt("courses["+i+"].price");
			totalPriceOfAllCourses = totalPriceOfAllCourses + (copies * price);			
		}
		Assert.assertEquals(totalPriceOfAllCourses, totalAmount);
		if(totalAmount == totalPriceOfAllCourses) {
			System.out.println("Total of all courses price is matched with dashboard price");
		}
		else {
			System.out.println("Total of all courses price is not matched with dashboard price");
		}
	}

}
