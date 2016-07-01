package com.br.ufc.danielfilho.exceptions;

import com.br.ufc.danielfilho.file.FileProperties;

public class InvalidFileException extends Exception {
	
	public static final String INVALID_DATA_FORMAT = "Invalid data format.";
	
	public static final String FILE_NOT_FOUND = "This file doesn't exists.";
	public static final String INVALID_N = "Invalid order value. N should be bettwen"
			+ " "+FileProperties.MIN_COUNT_ROWS+" and "+FileProperties.MAX_COUNT_ROWS;
	
	public static final String INVALID_COUNT_PROCCESSES = "Number of nodes in-valid. Number of nodes "
			+ "should be bettwen "+FileProperties.MIN_COUNT_PROCESS+" and "+FileProperties.MAX_COUNT_PROCESS; 
	
	public static final String INVALID_COUNT_THREADS = "Number of cores invalid.Number of cores should "
			+ "be bettwen"+FileProperties.MIN_COUNT_THREADS+" and "+FileProperties.MAX_COUNT_THREADS;
	
	public static final String INVALID_MAIN_DIAGONAL = "Main diagonal must have all elements differents of 0";
	public static final String INVALID_VERIFICATION_ROW = "Verification line invalid. Verification "
			+ "line should be bettwen 1 and "+FileProperties.MAX_COUNT_ROWS;
	
	public static final String INVALID_INTERATION = "Iterations value invalid. Iterations should be"
	+" bettwen "+ FileProperties.MIN_COUNT_INTERATIONS+" and "+FileProperties.MAX_COUNT_INTERATIONS;
	
	public static final String MAT_A_DIFF_VEC_B = "Matrix A size should have same Vector B size.";
	public static final String MAT_A_SAME_VEC_B_DIFF_N = "A and B should have <N> lines.";
	public static final String MAT_A_DIFF_N = "Matrix A should have <N> columns.";
	public static final String VECT_B_DIFF_N = "Vector B should have <N> elements.";
	public static final String VECT_B_ONE_COLUMN = "B should have 1 column.";
	
	public static final String MATRIX_DOEST_CONVERGE = "Especified matrix does not converge.";
	
	
	public InvalidFileException(String message) {
		super("Error: "+message);
	}
}
