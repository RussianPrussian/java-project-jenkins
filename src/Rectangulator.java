public class Rectangulator{
	
	public static void main(String [] args){
		
		int length = Integer.parseInt(args[0]);
		int width = Integer.parseInt(args[1]);
		
		Rectangle rec= new Rectangle(length, width);
		String output = String.format("You rectangle: \n\nlength: %d \n\nwidth: %d\n\narea: %d \n\nperimeter: %d",
				length, width, rec.getArea(), rec.getPerimeter());
		System.out.println(output);
	}
}