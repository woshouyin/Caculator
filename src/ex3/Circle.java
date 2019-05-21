package ex3;

public class Circle {
	private double posX;
	private double posY;
	private double radius;
	
	public Circle(double posX, double posY) {
		setPosition(posX, posY);
	}
	
	public Circle(double radius) {
		setRadius(radius);
	}
	
	public Circle(double posX, double posY, double radius) {
		setPosition(posX, posY);
		setRadius(radius);
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void setPosition(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public double getDistance(Circle c) {
		return Math.sqrt( Math.pow(posX - c.posX, 2) + Math.pow(posY - c.posY, 2));
	}
	
	public double getArea() {
		return Math.PI * Math.pow(radius, 2);
	}
	
	public String toString() {
		return "posX:" + posX + " posY:" + posY + " radius:" + radius + " Area:" + getArea();
	}
	
}
