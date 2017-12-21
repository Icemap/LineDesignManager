package com.studio.tensor.ldm.test;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.LatLngInfo;
import com.studio.tensor.ldm.utils.CoodUtils;

public class LengthTest
{
	public static void main(String[] args)
	{
		String linePoint = "[{\"longitude\":113.29753875732423,\"latitude\":23.129684933462897},{\"longitude\":113.30238819122316,\"latitude\":23.131381949010372},{\"longitude\":113.30944776535036,\"latitude\":23.131638472748257},{\"longitude\":113.31034898757936,\"latitude\":23.127948430212783}]";
		String resultPoint = "[[113.29753875732423,23.129684933462897],[113.2999634742737,23.130533441236636],"
				+ "[113.2999634742737,23.130533441236636],[113.30238819122316,23.131381949010372],[113.30591797828676,23.131510210879313],"
				+ "[113.30591797828676,23.131510210879313],[113.30944776535036,23.131638472748257],[113.30989837646486,23.12979345148052],"
				+ "[113.30989837646486,23.12979345148052],[113.31034898757936,23.127948430212783]]";
		Double lineLength = 0.0d;
		Double resultLength = 0.0d;
		
		LatLngInfo[] lineArray = new Gson().fromJson(linePoint, LatLngInfo[].class);
		Double[][] resultArray = new Gson().fromJson(resultPoint, Double[][].class);
		for(int i = 1;i < lineArray.length; i++)
		{
			LatLngInfo p1 = CoodUtils.lonLatToMercator(lineArray[i - 1].getLongitude(), lineArray[i - 1].getLatitude());
			LatLngInfo p2 = CoodUtils.lonLatToMercator(lineArray[i].getLongitude(), lineArray[i].getLatitude());
			
			lineLength += getLength(p1, p2);
		}
		
		for(int i = 1;i < resultArray.length; i++)
		{
			LatLngInfo p1 = CoodUtils.lonLatToMercator(resultArray[i - 1][0], resultArray[i - 1][1]);
			LatLngInfo p2 = CoodUtils.lonLatToMercator(resultArray[i][0], resultArray[i][1]);
			
			resultLength += getLength(p1, p2);
		}
		
		System.out.println("lineLength:" + lineLength);
		System.out.println("resultLength:" + resultLength);
	}
	
	public static Double getLength(LatLngInfo p1, LatLngInfo p2)
	{
		Double _x = p1.getLongitude() - p2.getLongitude();
		Double _y = p1.getLatitude() - p2.getLatitude();
		return Math.sqrt(Math.pow(_x, 2.0) + Math.pow(_y, 2.0));
	}
}
