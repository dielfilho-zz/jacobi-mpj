package com.br.ufc.danielfilho.file;

import com.br.ufc.danielfilho.exceptions.InvalidFileException;

public class FileProperties {

	public static final float MIN_COUNT_ROWS = 3;
	public static final float MAX_COUNT_ROWS = 10000;

	public static final float MIN_COUNT_PROCESS = 1;
	public static final float MAX_COUNT_PROCESS = 100;

	public static final float MIN_COUNT_THREADS = 1;
	public static final float MAX_COUNT_THREADS = 100;

	public static final float MIN_COUNT_INTERATIONS = 1;
	public static final float MAX_COUNT_INTERATIONS = 10000;

	public static final float MIN_ROW_VERIFICATION = 1;
	public static final float MAX_ROW_VERIFICATION = MAX_COUNT_ROWS;

	public static final float TOLERANCE = (float) 0.001;

	private static float countRows;
	private float countProccesses;
	private float countThreads;
	private float verification;
	
	static int interations;
	static int countInterations;

	static float[][] matA;
	static float[] vectB;
	static float[] vectX;
	static float[] xPrev;
	
	
	private static FileProperties instance;

	public static FileProperties getInstance() {
		if (instance == null)
			instance = new FileProperties();
		return instance;
	}

	private FileProperties() {
	}

	public static float getCountRows() {
		return countRows;
	}

	public void setCountRows(String countRowsS) throws InvalidFileException {
		if (countRowsS == null)
			throw new InvalidFileException(InvalidFileException.INVALID_N);

		float param = Float.parseFloat(countRowsS);
		if (param < MIN_COUNT_ROWS || param > MAX_COUNT_ROWS)
			throw new InvalidFileException(InvalidFileException.INVALID_N);
		else {
			this.countRows = param;

			vectX = new float[(int)param];
			xPrev = new float[(int)param];
		}
	}

	public float getCountProcesses() {
		return countProccesses;
	}

	public void setCountProcesses(String countProcessesS)
			throws InvalidFileException {
		if (countProcessesS == null)
			throw new InvalidFileException(
					InvalidFileException.INVALID_COUNT_PROCCESSES);

		float param = Float.parseFloat(countProcessesS);
		if (param < MIN_COUNT_PROCESS || param > MAX_COUNT_PROCESS)
			throw new InvalidFileException(
					InvalidFileException.INVALID_COUNT_PROCCESSES);
		else {
			this.countProccesses = param;
		}
	}

	public float getCountThreads() {
		return countThreads;
	}

	public void setCountThreads(String countThreadsS)
			throws InvalidFileException {
		if (countThreadsS == null)
			throw new InvalidFileException(
					InvalidFileException.INVALID_COUNT_THREADS);

		float param = Float.parseFloat(countThreadsS);
		if (param < MIN_COUNT_THREADS || param > MAX_COUNT_THREADS)
			throw new InvalidFileException(
					InvalidFileException.INVALID_COUNT_THREADS);
		else {
			this.countThreads = param;
		}
	}

	public float getVerification() {
		return verification;
	}

	public void setVerification(String verificationS)
			throws InvalidFileException {
		if (verificationS == null)
			throw new InvalidFileException(
					InvalidFileException.INVALID_VERIFICATION_ROW);

		float param = Float.parseFloat(verificationS);
		if (param < MIN_ROW_VERIFICATION || param > MAX_ROW_VERIFICATION)
			throw new InvalidFileException(
					InvalidFileException.INVALID_VERIFICATION_ROW);

		this.verification = param;
	}

	public static int getInterations() {
		return (int) interations;
	}

	public void setInterations(String interationsS) throws InvalidFileException {
		if (interationsS == null)
			throw new InvalidFileException(
					InvalidFileException.INVALID_INTERATION);

		float param = Float.parseFloat(interationsS);
		if (param < MIN_COUNT_INTERATIONS || param > MAX_COUNT_INTERATIONS)
			throw new InvalidFileException(
					InvalidFileException.INVALID_INTERATION);
		else {
			interations = (int)param;
			countInterations = 0;
		}
	}

	public static float[][] getMatA() {
		return matA;
	}

	public static void setMatA(float[][] matA) {
		FileProperties.matA = matA;
	}
	
	public static float[] getVectB() {
		return vectB;
	}

	public static float[] getxPrev() {
		return xPrev;
	}

	public static void setxPrev(float[] xPrev) {
		FileProperties.xPrev = xPrev;
	}

	public static void setVectB(float[] vectB) {
		FileProperties.vectB = vectB;
	}

	public static float[] getVectX() {
		return vectX;
	}

	public static void setVectX(float[] vectX) {
		FileProperties.vectX = vectX;
	}
	
	public static int getCountInterations() {
		return countInterations;
	}

	public static void setCountInterations(int countInterations) {
		FileProperties.countInterations = countInterations;
	}

	

}
