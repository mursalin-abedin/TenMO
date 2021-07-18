package com.techelevator.tenmo.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.techelevator.tenmo.services.ConsoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsoleServiceTest {

	private ByteArrayOutputStream output;

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
	}

	@Test
	public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
		Object[] options = new Object[] { Integer.valueOf(3), "Blind", "Mice" };

		ConsoleService console = getServiceForTesting();

		console.getChoiceFromOptions(options);

		String expected = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> " + System.lineSeparator();
		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void returns_object_corresponding_to_user_choice() {
		Integer expected = Integer.valueOf(456);
		Integer[] options = new Integer[] { Integer.valueOf(123), expected, Integer.valueOf(789) };
		ConsoleService console = getServiceForTestingWithUserInput("2" + System.lineSeparator());

		Integer result = (Integer) console.getChoiceFromOptions(options);

		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void prints_a_blank_line_after_successful_choice() {
		Integer[] options = new Integer[] { 123, 456, 789 };
		ConsoleService console = getServiceForTestingWithUserInput("2" + System.lineSeparator());

		Integer result = (Integer) console.getChoiceFromOptions(options);

		String expected = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> " + System.lineSeparator();
		
		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_does_not_choose_valid_option() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		ConsoleService console = getServiceForTestingWithUserInput("4" + System.lineSeparator() + "1" + System.lineSeparator());

		console.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 4 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay + System.lineSeparator();

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_chooses_option_less_than_1() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		ConsoleService console = getServiceForTestingWithUserInput("0" + System.lineSeparator() + "1" + System.lineSeparator());

		console.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 0 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay + System.lineSeparator();

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_enters_garbage() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		ConsoleService console = getServiceForTestingWithUserInput("Mickey Mouse" + System.lineSeparator() + "1" + System.lineSeparator());

		console.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** Mickey Mouse is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay + System.lineSeparator();

		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void displays_prompt_for_user_input() {
		ConsoleService console = getServiceForTesting();
		String prompt = "Your Name";
		String expected = "Your Name: ";
		console.getUserInput(prompt);
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void returns_user_input() {
		String expected = "Juan";
		ConsoleService console = getServiceForTestingWithUserInput(expected);
		String result = console.getUserInput("Your Name");
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void displays_prompt_for_user_input_integer() {
		ConsoleService console = getServiceForTesting();
		String prompt = "Your Age";
		String expected = "Your Age: ";
		console.getUserInputInteger(prompt);
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void returns_user_input_for_integer() {
		Integer expected = 27;
		ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
		Integer result = console.getUserInputInteger("Enter a number");
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void shows_error_and_redisplays_prompt_if_user_enters_invalid_integer() {
		ConsoleService console = getServiceForTestingWithUserInput("bogus" + System.lineSeparator() + "1" + System.lineSeparator());
		String prompt = "Your Age";
		String expected = "Your Age: " + System.lineSeparator() + "*** bogus is not valid ***" + System.lineSeparator() + System.lineSeparator() + "Your Age: ";
		console.getUserInputInteger(prompt);
		Assert.assertEquals(expected, output.toString());
	}

	private ConsoleService getServiceForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new ConsoleService(input, output);
	}

	private ConsoleService getServiceForTesting() {
		return getServiceForTestingWithUserInput("1" + System.lineSeparator());
	}
}
