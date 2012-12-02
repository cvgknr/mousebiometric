package unittest;

public class PrintTrajectories {
	public static void main(String []args){
		//String path = "C:\\Users\\Administrator\\git\\mouse_dec\\MouseBiometric\\logsamples\\new";
		String path = "..\\MouseBiometric\\logsamples\\new\\wakeup_Other_001.xml";
		TestMouseTrajectory test = new TestMouseTrajectory(path, true);
		test.print();
	}
}
