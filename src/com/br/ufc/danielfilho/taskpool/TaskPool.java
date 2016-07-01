package com.br.ufc.danielfilho.taskpool;

import mpi.MPI;

import com.br.ufc.danielfilho.task.Task;
import com.br.ufc.danielfilho.task.TaskMain;

public class TaskPool {
	
	public static final int RANK_MAIN = 0;
	private int taskRank;
	
	public void initEnviroment(String args[]){
		MPI.Init(args);
		
		taskRank = MPI.COMM_WORLD.Rank();
		
		if(taskRank == RANK_MAIN){
			TaskMain main = new TaskMain(MPI.COMM_WORLD.Size(), MPI.ANY_SOURCE);
			main.readFile();
			main.initTaskMain(MPI.COMM_WORLD.Size());
		}else{
			Task task = new Task(taskRank);
			task.initTask();
		}
		
		
		
		
		
	}
	
	
	
}
