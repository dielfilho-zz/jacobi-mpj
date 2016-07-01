package com.br.ufc.danielfilho.task;

import com.br.ufc.danielfilho.taskpool.TaskPool;
import com.br.ufc.danielfilho.util.Message;
import com.br.ufc.danielfilho.util.MessageHandle;

public class Task {
	private boolean canContinue;
	private int rank;
	private MessageHandle mHandle;
	private int currentRow;
	private float[][] matA;
	private float[] xPrev;
	
	
	public Task(int rank) {
		this.rank = rank;
		this.canContinue = false;
		this.mHandle = MessageHandle.getInstance();
	}

	private void calcJacobi(int initCol, int endCol, int n){
		
		Message message = new Message();
		
		this.xPrev = new float[n];
		
		
		int currentRow = 0;
		
		while(canContinue){
			float rowSum = 0;
			
			for(int j = initCol; j < endCol; j++){
				if(currentRow != j){
					rowSum += (matA[currentRow][j] * xPrev[j]) / matA[currentRow][currentRow];
				}
			}
			
			//Send message with sum of row...
			message.setType(Message.ROW_SUM_ENDS);
			message.setObject(Message.KEY_ROW_SUM, rowSum);
			mHandle.sendMessage(message, TaskPool.RANK_MAIN);
			
			
			//Wait for the message to continue or stop...
			Message response = mHandle.receiveMessage(TaskPool.RANK_MAIN);
			if(response.getType() == Message.END_PROCCESS){
				break;
			}else{
				currentRow = (int) response.getData(Message.KEY_CURRENT_ROW);
				xPrev = (float[]) response.getData(Message.KEY_VET_XPREV);
			}
		
		}
		System.out.println("Task "+rank+" ends...");
	}
	
	public void setCurrentRow(int row){
		this.currentRow = row;
	}
	
	private void startTask(int colInit, int colEnd, int n){
		canContinue = true;
		calcJacobi(colInit, colEnd, n);
	}

	public void initTask() {
		System.out.println("Task "+rank+" waiting for data...");
		
		Message message = mHandle.receiveMessage(TaskPool.RANK_MAIN); 
		int cols [] = (int[]) message.getObject(Message.KEY_COLUMNS);
		int countRows = (int) message.getObject(Message.KEY_COUNT_ROWS);
		this.matA = (float[][]) message.getObject(Message.KEY_MAT_A);
		System.out.println("Task "+rank+" starts calc...");
		startTask(cols[0], cols[1], countRows);
		
	}
}
