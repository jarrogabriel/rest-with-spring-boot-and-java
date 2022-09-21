package br.com.jtpsolution;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jtpsolution.exceptions.UnsuportedMathOperationException;
import jakarta.websocket.server.PathParam;

@RestController
public class MathController {

	private static final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}

	@RequestMapping(value = "/minus/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double minus(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(numberOne) - convertToDouble(numberTwo);
	}

	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(numberOne) / convertToDouble(numberTwo);
	}

	@RequestMapping(value = "/times/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double times(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(numberOne) * convertToDouble(numberTwo);
	}

	@RequestMapping(value = "/sqrt/{numberOne}", method = RequestMethod.GET)
	public Double sqrt(@PathVariable(value = "numberOne") String numberOne) throws Exception {

		if (!isNumeric(numberOne)) {
			throw new UnsuportedMathOperationException("Please set a numeric value!");
		}
		return Math.sqrt(convertToDouble(numberOne));
	}

	@RequestMapping(value = "/media", method = RequestMethod.GET)
	public Double media(@PathParam(value = "number") String[] number) throws Exception {

		Double media = 0D;
		Integer counter = 0;
		for (String num  : number) {
			
			if (!isNumeric(num)) {
				throw new UnsuportedMathOperationException("Query param number .: " + num + " is wrong!" );
			}
			
			media = convertToDouble(num) + media;
			counter ++;
		}
		
		
		return media/counter;
	}

	private Double convertToDouble(String strNumber) {

		if (strNumber == null) {
			return 0D;
		}

		strNumber = strNumber.replaceAll(",", ".");

		if (isNumeric(strNumber)) {
			return Double.parseDouble(strNumber);
		}

		return 0D;
	}

	private boolean isNumeric(String strNumber) {

		if (strNumber == null) {
			return false;
		}

		strNumber = strNumber.replaceAll(",", ".");

		return strNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
