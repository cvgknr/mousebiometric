package unittest;


import java.io.File;
import java.util.ArrayList;

import edu.pace.mouse.biometric.data.MouseDragDropTrajectory;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMoveClickTrajectory;
import edu.pace.mouse.biometric.data.MouseTrajectory;
import edu.pace.mouse.biometric.features.MouseMoveClickTrajectoryFeatures;
import edu.pace.mouse.biometric.features.MoveDragDropTrajectoryFeatures;
import edu.pace.mouse.biometric.features.SystemWakeUpTrajectoryFeatures;

public class TestMouseTrajectory extends BaseTest{
	private boolean printFeatures;
	public TestMouseTrajectory(String path, boolean printFeatuers) {
		super(path);
		this.printFeatures = printFeatuers;
	}

	public void print(File file){
		MouseLogParser parser = new MouseLogParser(file.getAbsolutePath());
		ArrayList<MouseDragDropTrajectory> mddt = parser.getMouseDragDropTrajectory();
		System.out.println("========"+ file.getName() +"========================================================");
		for (MouseDragDropTrajectory t : mddt) {
			System.out.println("\n================MouseDragDropTrajectory: MousePointer Index: " + t.getMousePointer().getId() + "===============================================================");
			System.out.print("Drag: ");
			printClick(t.getMouseClick());
			System.out.println("");
			System.out.println("Number of points: " + t.getNumberOfPoints());
			printTrajectory(t);
			System.out.println("");
		}
		if(printFeatures){
			if(0 != mddt.size()){
				MoveDragDropTrajectoryFeatures tf = new MoveDragDropTrajectoryFeatures(parser);
				print(tf.extract());
			}else
				System.out.println("No DragDrop Trajectories");
		}
		ArrayList<MouseMoveClickTrajectory> mmc = parser.getMouseMoveAndClickTrajectory();
		for (MouseMoveClickTrajectory t : mmc) {
			System.out.println("\n================MouseMoveClick Index: " + t.getMousePointer().getId() + "===============================================================");
			System.out.print("Click: ");
			printClick(t.getClick());
			System.out.println("");
			System.out.println("Number of points: " + t.getNumberOfPoints());
			printTrajectory(t);
			System.out.println("");
		}
		if(printFeatures){
			if(0 != mmc.size()){
				MouseMoveClickTrajectoryFeatures mmct = new MouseMoveClickTrajectoryFeatures(parser);
				print(mmct.extract());
			}else
				System.out.println("No Mouse Move click Trajectories");
		}
		ArrayList<MouseTrajectory> mwt = parser.getSystemWakeUpTranjectories();
		for (MouseTrajectory t : mwt) {
			System.out.println("\n================SystemWakeUpTrajectory Index: " + t.getMousePointer().getId() + "===============================================================");
			System.out.println("");
			System.out.println("Number of points: " + t.getNumberOfPoints());
			printTrajectory(t);
			System.out.println("");
		}
		if(printFeatures){
			if(0 != mwt.size()){
				SystemWakeUpTrajectoryFeatures swt = new SystemWakeUpTrajectoryFeatures(parser);
				print(swt.extract());
			}else
				System.out.println("No System wake up Trajectories");
		}
		ArrayList<MouseTrajectory> it = parser.getOtherTrajectory();
		for (MouseTrajectory t : it) {
			System.out.println("\n================OtherTrajectory Index: " + t.getMousePointer().getId() + "===============================================================");
			System.out.println("");
			System.out.println("Number of points: " + t.getNumberOfPoints());
			printTrajectory(t);
			System.out.println("");
		}
	}
}
