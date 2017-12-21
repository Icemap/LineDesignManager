package com.studio.tensor.ldm.test;

import java.util.Vector;

public class ThreadTest
{
	public static void main(String[] args)
	{
		// 使用线程安全的Vector
		Vector<Thread> threads = new Vector<Thread>();
		for (int i = 0; i < 100; i++)
		{

			Thread iThread = new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						Thread.sleep(1000);
						// 模拟子线程任务
					}
					catch (InterruptedException e)
					{
					}
					System.out.println("子线程" + Thread.currentThread() + "执行完毕");
				}
			});
			threads.add(iThread);
			iThread.start();
		}

		for (Thread iThread : threads)
		{
			try
			{
				// 等待所有线程执行完毕
				iThread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("主线执行。");
	}
}
