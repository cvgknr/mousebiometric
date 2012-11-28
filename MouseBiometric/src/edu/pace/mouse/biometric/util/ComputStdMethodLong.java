package edu.pace.mouse.biometric.util;

import java.util.Arrays;

public class ComputStdMethodLong{
	private double mean=0, median=0, stddev=0;
	private long min=0, max=0;
	public ComputStdMethodLong(long []v){
		if (null == v || 0 == v.length){
			mean = median = stddev = 0;
			min = max = 0;
		}else if (1 == v.length){
			mean = median = stddev = v[0];
			min = max = v[0];
		}else{
			long sum = v[0],sqSum=0;
			min = v[0];
			max = v[0];
			
			for (int i=1;i<v.length;i++) {
				//Minimum
				if (0 == min || v[i] < min)
					min = v[i];
				//Maximum
				if (v[i] > max)
					max = v[i];
				sum += v[i];
				sqSum += (v[i]-v[i-1]) * (v[i]-v[i-1]);
			}
			mean = sum / (double) v.length;
			stddev = Math.sqrt(sqSum/(double)(v.length));
			Arrays.sort(v);
			if (0 == (v.length % 2))
				median = v[(v.length/2) -1];
			else{
				int mid = (v.length/2) - 1;
				median = (v[mid] + v[mid+1])/2;
			}
		}
			
	}
	public double getMean() {
		return mean;
	}
	public double getMedian() {
		return median;
	}
	public double getStddev() {
		return stddev;
	}
	public long getMin() {
		return min;
	}
	public long getMax() {
		return max;
	}
}
