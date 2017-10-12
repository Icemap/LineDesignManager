package com.studio.tensor.ldm.test;

public class TestAngel
{
	public static void main(String[] args)
	{
		System.out.println(getAngle(new MyLatLng(0,0),new MyLatLng(-1,1),new MyLatLng(1,1)));
	}
	
	public static double getAngle(MyLatLng center, MyLatLng start,MyLatLng end)
	{
		Double aS = getAngle(center, start);
		Double aE = getAngle(center, end);
		
		Double aR = aE - aS;
		return aR < 0 ? aR + 360 : aR;
	}
	
	//A :Center
	public static double getAngle(MyLatLng A,MyLatLng B){  
        double dx=(B.m_RadLo-A.m_RadLo)*A.Ed;  
        double dy=(B.m_RadLa-A.m_RadLa)*A.Ec;  
        double angle=0.0;
        angle=Math.atan(Math.abs(dx/dy))*180./Math.PI;    
        double dLo=B.m_Longitude-A.m_Longitude;  
        double dLa=B.m_Latitude-A.m_Latitude;  
        if(dLo>0&&dLa<=0){  
            angle=(90.-angle)+90;  
        }  
        else if(dLo<=0&&dLa<0){  
            angle=angle+180.;  
        }else if(dLo<0&&dLa>=0){  
            angle= (90.-angle)+270;  
        }  
        return angle;  
    }  
    static class MyLatLng {  
        final static double Rc=6378137;  
        final static double Rj=6356725;  
        double m_LoDeg,m_LoMin,m_LoSec;  
        double m_LaDeg,m_LaMin,m_LaSec;  
        double m_Longitude,m_Latitude;  
        double m_RadLo,m_RadLa;  
        double Ec;  
        double Ed;  
        public MyLatLng(double longitude,double latitude){  
            m_LoDeg=(int)longitude;  
            m_LoMin=(int)((longitude-m_LoDeg)*60);  
            m_LoSec=(longitude-m_LoDeg-m_LoMin/60.)*3600;  
              
            m_LaDeg=(int)latitude;  
            m_LaMin=(int)((latitude-m_LaDeg)*60);  
            m_LaSec=(latitude-m_LaDeg-m_LaMin/60.)*3600;  
              
            m_Longitude=longitude;  
            m_Latitude=latitude;  
            m_RadLo=longitude*Math.PI/180.;  
            m_RadLa=latitude*Math.PI/180.;  
            Ec=Rj+(Rc-Rj)*(90.-m_Latitude)/90.;  
            Ed=Ec*Math.cos(m_RadLa);  
        }  
    }  
}
