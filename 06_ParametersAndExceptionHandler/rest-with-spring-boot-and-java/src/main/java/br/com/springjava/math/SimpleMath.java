package br.com.springjava.math;

public class SimpleMath {

	public Double sum(Double numberOne, Double numberTwo) {

		return numberOne + numberTwo;
	}

	public Double minus(Double numberOne, Double numberTwo) {

		return numberOne - numberTwo;
	}

	public Double division(Double numberOne, Double numberTwo) {

		return numberOne / numberTwo;
	}

	public Double times(Double numberOne, Double numberTwo) {

		return numberOne * numberTwo;
	}

	public Double sqrt(Double numberOne) {

		return Math.sqrt(numberOne);
	}

	public Double media(Double numberOne, Double numberTwo) {

		return (numberOne + numberTwo) / 2;
	}

}
