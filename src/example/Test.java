package example;

public class Test {
	int a;
	int b;

	public Test(int i) {
		a =i;
	}

	public Test(Test t) {
		a = t.a;
		a+=5;
	}
}
