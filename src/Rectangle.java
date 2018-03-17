public class Rectangle{
	private int length;
	private int width;
	public Rectangle(int length, int width) {
		super();
		this.length = length;
		this.width = width;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getArea(){
		return length * width;
	}
	
	public int getPerimeter(){
		return 2*length + 2*width;
	}
}