package com.br.ufc.danielfilho.main;

import com.br.ufc.danielfilho.taskpool.TaskPool;

public class JacobiMPI {
	public static void main(String[] args) {
		new TaskPool().initEnviroment(args);
	}
}
