package com.tempus.gss.product.ift.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * <pre>
 * <b>多线程工具类.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月7日 上午10:21:48
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月7日 上午10:21:48    cz
 *         new file.
 * </pre>
 */
public class ThreadExecutor {
	
	private ExecutorService exe = null;
	
	private List<Runnable> runnableList = new ArrayList<Runnable>();
	
	public ThreadExecutor(List<Runnable> runnableList){
		exe = Executors.newFixedThreadPool(runnableList.size());
		this.runnableList=runnableList;
	}
	
	public  void execute() throws InterruptedException {
        for (int i = 0; i < runnableList.size(); i++) {
        	exe.execute(runnableList.get(i));
        } 
        exe.shutdown();  
        while (true) {  
            if (exe.isTerminated()) {  
                break;  
            }  
            Thread.sleep(50);
        }
        if(exe.isShutdown()==false)
        	exe.shutdownNow();
        
	}
}
