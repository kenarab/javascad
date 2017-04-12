package eu.printingin3d.javascad.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.CustomTypeSafeMatcher;

import eu.printingin3d.javascad.context.ColorHandlingContext;
import eu.printingin3d.javascad.models.Abstract3dModel;

public class AssertEx {
	private static final double EPSILON = 0.0001;
	
	public static void assertEqualsWithoutWhiteSpaces(final String expected, String actual) {
		assertThat(actual, new CustomTypeSafeMatcher<String>(expected) {
			
			@Override
			protected boolean matchesSafely(String item) {
				return item.replaceAll("\\s", "").equals(expected.replaceAll("\\s", ""));
			}
		});
	}
	
	public static void assertEqualsWithoutWhiteSpaces(final String expected, Abstract3dModel model) {
		assertEqualsWithoutWhiteSpaces(expected, model.toScad(ColorHandlingContext.DEFAULT).getScad());
	}
	
	public static void assertMatchToExpressionWithoutWhiteSpaces(final String regex, String actual) {
		assertThat(actual, new CustomTypeSafeMatcher<String>(regex) {
			
			@Override
			protected boolean matchesSafely(String item) {
				return item.replaceAll("\\s", "").matches(regex.replaceAll("\\s", ""));
			}
		});
	}
	
	public static void assertMatchCount(String regex, String actual, int number) {
		Matcher matcher = Pattern.compile(regex).matcher(actual);
		int c = 0;
		while (matcher.find()) {
			c++;
		}

		assertEquals(number, c);
	}
	
	public static void assertContainsWithoutWhiteSpaces(final String subString, String actual) {
		assertThat(actual, new CustomTypeSafeMatcher<String>(subString) {
			
			@Override
			protected boolean matchesSafely(String item) {
				return item.replaceAll("\\s", "").contains(subString.replaceAll("\\s", ""));
			}
		});
	}
	
	public static void assertDoubleEquals(double expected, double actual) {
		assertEquals(expected, actual, EPSILON);
	}
	
	public static void assertDoubleEquals(String message, double expected, double actual) {
		assertEquals(message, expected, actual, EPSILON);
	}
}
