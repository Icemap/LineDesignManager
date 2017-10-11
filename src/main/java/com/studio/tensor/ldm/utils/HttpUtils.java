package com.studio.tensor.ldm.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class HttpUtils
{
	public static byte[] getRequest(String url, String repType)
	{
		String result = "";
		byte[] resByt = null;
		try
		{
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

			// 连接超时
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(25000);

			// 读取超时 --服务器响应比较慢,增大时间
			conn.setReadTimeout(25000);
			conn.setRequestMethod("GET");
			conn.connect();

			PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"), true);

			if ("image/jpeg".equals(repType))
			{
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				BufferedImage bufImg = ImageIO.read(conn.getInputStream());
				ImageIO.write(bufImg, "jpg", outputStream);
				resByt = outputStream.toByteArray();
				outputStream.close();
			}
			else
			{
				// 取得输入流，并使用Reader读取
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				System.out.println("=============================");
				System.out.println("Contents of get request");
				System.out.println("=============================");
				String lines = null;
				while ((lines = reader.readLine()) != null)
				{
					System.out.println(lines);
					result += lines;
					result += "\r";
				}
				resByt = result.getBytes();
				reader.close();
			}
			out.print(resByt);
			out.flush();
			out.close();
			// 断开连接
			conn.disconnect();
			System.out.println("=============================");
			System.out.println("Contents of get request ends");
			System.out.println("=============================");
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resByt;
	}
}
