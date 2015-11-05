package informationRetrieval.tokenization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegexTokenizer implements Tokenizer {

	public String regex;

	public RegexTokenizer(String regex) {
		this.regex = regex;
	}

	@Override
	public List<String> tokenize(String text) {
		String[] tokens = text.trim().split(regex);
		return new ArrayList<String>(Arrays.asList(tokens));
	}
}
