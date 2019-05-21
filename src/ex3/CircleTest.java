package ex3;


public class CircleTest {
	public static void main(String[] args) {
		Circle c1 = new Circle(0, 0, 1);
		Circle c2 = new Circle(3, 4, 2);
		System.out.println("c1的属性为：" + c1);
		System.out.println("c2的属性为：" + c2);
		System.out.println("c1与c2的距离为:" + c1.getDistance(c2));
	}
}
