package ex4_1;

public class CircleTest {
	public static void main(String[] args) {
		Circle cl = new Circle();
		cl.radius = 2;
		double area = cl.getArea();
		System.out.printf("半径为:%.1f, 面积为:%.3f", cl.radius, area);
	}
}
