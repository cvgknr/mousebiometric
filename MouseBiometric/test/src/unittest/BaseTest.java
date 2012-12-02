package unittest;

import java.io.File;
import java.util.ArrayList;

import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseClick;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MousePointer;
import edu.pace.mouse.biometric.data.MouseTrajectory;

public abstract class BaseTest {
	private String path;
	public BaseTest(String path){
		this.path = path;
	}
	public void print(ArrayList<FeatureResult> f){
		for(FeatureResult fr:f)
			System.out.println(fr.toString());
	}
	abstract protected void print(File file);
	public void print(){
		File inFolder = new File(path);
		if (inFolder.isDirectory()){
			File[] inFilesList = inFolder.listFiles();
			for(File file: inFilesList){
				if (file.getName().endsWith(".xml"))
					print(file);
			}
		}else{
			File file = new File(path);
			if (file.getName().endsWith(".xml"))
				print(file);
		}
	}
	protected void printPixel(long x, long y, long time){
		System.out.print("(x=" + x + ",y=" + y + ",t=" + time + ")");
	}
	protected void printClick(MouseClick click){
		System.out.print("(x=" + click.getXpix()+ ",y=" + click.getYpix() + ",ptime=" +  click.getMousepresstime()+",rtime=" + click.getMousereleasetime() + ")");
	}
	protected void printTrajectory(MouseTrajectory mt){
		MousePointer mp = mt.getMousePointer();
		System.out.print("Trajectory Points: (");
		ArrayList<MouseMove> mm = mt.getMouseMoves();
		if (0 == mm.size() || (mp.getXpix() != mm.get(0).getXpix() || mp.getYpix() != mm.get(0).getYpix() || mp.getStarttime() != mm.get(0).getStarttime())){
			printPixel(mp.getXpix(), mp.getYpix(), mp.getStarttime());
		}
		for (MouseMove mouseMove : mm) {
			printPixel(mouseMove.getXpix(), mouseMove.getYpix(), mouseMove.getStarttime());
		}
		int lastindex = mm.size()-1;
		if (0 == mm.size() || (mp.getXfinalpix() != mm.get(lastindex).getXpix() ||
				mp.getYfinalpix() != mm.get(lastindex).getYpix() ||
				mp.getEndtime() != mm.get(lastindex).getStarttime())){
			printPixel(mp.getXfinalpix(), mp.getYfinalpix(), mp.getEndtime());
		}
		System.out.print(")");
	}
}
