package com.br.ufc.danielfilho.task;

import com.br.ufc.danielfilho.file.ArchiveReader;
import com.br.ufc.danielfilho.file.FileProperties;
import com.br.ufc.danielfilho.util.Message;
import com.br.ufc.danielfilho.util.MessageHandle;

public class TaskMain {
	
	private MessageHandle meHandle;
	private int currentRow;
	private float[] xPrev;
	private float[] vetX;
	private float[][] matA;
	private float[] vetB;
	private int countTasksEnds;
	private float rowSum;
	private float tolerance;
	
	private int interations;
	private int worldSize;
	private int anySource;
	
	public TaskMain(int worldSize, int anySource) {
		this.meHandle = MessageHandle.getInstance();
		this.currentRow = 0;
		this.countTasksEnds = 0;
		this.rowSum = 0;
		this.tolerance = FileProperties.TOLERANCE;
		this.worldSize = worldSize; 
		this.anySource = anySource;
	}


	public void readFile(){
		ArchiveReader reader = new ArchiveReader();
		reader.showAndReadFile();
		this.matA = FileProperties.getMatA();
		this.xPrev = FileProperties.getxPrev();
		this.vetB = FileProperties.getVectB();
		this.vetX = FileProperties.getVectX();
		this.interations = FileProperties.getInterations();
	}
	
	
	public void initTaskMain(int processes){
		if(processes == 1)
			return;
		
		//Take of the main proccess
		int ammountProcess = processes - 1;
		
		int offset = (int)( FileProperties.getCountRows() / ammountProcess );
		int j = 0;
		int start = 1;
		int rest = (int) (FileProperties.getCountRows() % ammountProcess);
		
		if(!(rest == 0)){
			j += (offset+rest);
			
			int[] cols = {0, j};
			
			Message msg = new Message();
			msg.setObject(Message.KEY_COLUMNS, cols);
			msg.setObject(Message.KEY_COUNT_ROWS, (int)FileProperties.getCountRows());
			msg.setObject(Message.KEY_MAT_A, matA);
			
			
			meHandle.sendMessage(msg, 1);
			System.out.println("Sending to task 1: 0, "+j);
			
			start = 2;
			
		}
		
		for(int i = start; i < processes; i++, j+=offset){
			int[] cols = {j, j+offset};
			
			Message msg = new Message();
			msg.setObject(Message.KEY_COLUMNS, cols);
			msg.setObject(Message.KEY_COUNT_ROWS, (int)FileProperties.getCountRows());
			msg.setObject(Message.KEY_MAT_A, matA);
			
			System.out.println("Sending to task "+i+": "+j+", "+(j+offset));
			meHandle.sendMessage(msg, i);
		}
		startListen();
	}
	
	public void startListen(){
		System.out.println("Stating listenning...");
		while(true){
			Message msg = meHandle.receiveMessage(anySource);
			if(!handleTaskMessage(msg))
				break;
		}
		System.out.println("Main task stopped to listen...");
	}
	
	public synchronized boolean handleTaskMessage(Message msg){
		try{
			if(msg.getType() == Message.ROW_SUM_ENDS){
				countTasksEnds = (countTasksEnds % (worldSize - 1)) + 1;
				
				float taskRowSum = (float) msg.getData(Message.KEY_ROW_SUM);
				this.rowSum += taskRowSum;
				
				if(countTasksEnds == (worldSize - 1)){
					
					xPrev[currentRow] = vetX[currentRow];
					vetX[currentRow] = (vetB[currentRow] / matA[currentRow][currentRow]) - this.rowSum;
	
					this.rowSum = 0;
					
					currentRow++;
					
					if(currentRow == FileProperties.getCountRows() ){
						currentRow = (int) (currentRow % FileProperties.getCountRows());
						boolean res = onTaskInterationEnd();
						if(!res){
							return false;
						}
					}
					//SENDING THE NEW CURRENT ROW AND THE NEW XPREV VECT TO ALL TASKS..
					Message message = new Message();
					message.setObject(Message.KEY_CURRENT_ROW, currentRow);
					message.setObject(Message.KEY_VET_XPREV, xPrev);
					sendMessageToAllTasks(message);
					
				}else{
					return true;
				}
			}else{
				System.err.println("Received a weird message D:");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	private boolean onTaskInterationEnd() {
		float maxElement = calcTolerance();
		
		if( maxElement <= tolerance || FileProperties.getCountInterations() >= FileProperties.getInterations()){
			showResult();
			sendMessageToAllTasks(new Message(Message.END_PROCCESS));
			return false;
		}else{
			FileProperties.setCountInterations(FileProperties.getCountInterations() + 1);
			if(FileProperties.getCountInterations() >= interations){
				System.err.println("Interations passed by....");
				sendMessageToAllTasks(new Message(Message.END_PROCCESS));
				return false;
			}else{
				return true;
			}
		}	
	}
	
	private void sendMessageToAllTasks(Message message){
		for(int i = 1; i < worldSize; i++){
			meHandle.sendMessage(message, i);
		}
	}
	
	private float calcTolerance(){
		float newX = 0;
		float prevX = 0;
		
		newX = vetX[0] < 0 ? vetX[0] * -1 : vetX[0];
		prevX = xPrev[0] < 0 ? xPrev[0] * -1 : xPrev[0];
		
		float maxElement = (newX - prevX) < 0 ? (newX - prevX) * -1 : (newX - prevX);
		float element = 0;	
		
		for(int i = 1; i < vetX.length; i++){
			newX = vetX[i] < 0 ? vetX[i] * -1 : vetX[i];
			prevX = xPrev[i] < 0 ? xPrev[i] * -1 : xPrev[i];
			element = (newX - prevX) < 0 ? (newX - prevX) * -1 : (newX - prevX);
			if(element > maxElement)
				maxElement = element;
		}
		
		return maxElement;
	}
	
	private void showResult(){
		float[] vet = FileProperties.getVectX();
		for(int i = 0; i < vet.length; i++){
			System.out.println(Math.round(vet[i]));
		}
	}
	
	
}
