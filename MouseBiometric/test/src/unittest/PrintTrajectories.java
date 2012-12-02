package unittest;

public class PrintTrajectories {
	public static void main(String []args){
		String path = "..\\MouseBiometric\\logsamples\\new\\wakeup_Other_001.xml";
		TestMouseTrajectory test = new TestMouseTrajectory(path, true);
		test.print();
	}
}
