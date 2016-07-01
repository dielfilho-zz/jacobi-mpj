package com.br.ufc.danielfilho.util;

import mpi.MPI;

public class MessageHandle {

	private static MessageHandle instance;
	
	public static MessageHandle getInstance(){
		if(instance == null)
			instance = new MessageHandle();
		return instance;
	}
	
	private MessageHandle(){}
	
	public Message receiveMessage(int from){
		Object[] messages = new Object[1];
		MPI.COMM_WORLD.Recv(messages, 0, 1, MPI.OBJECT, from, 99);
		Message message = (Message) messages[0];
		
		return message;
				
	}
	
	public void sendMessage(Message message, int destiny){
		Object[] data = new Object[1];
		data[0] = message;
		MPI.COMM_WORLD.Send(data, 0, 1, MPI.OBJECT, destiny, 99);
	}
	
}
