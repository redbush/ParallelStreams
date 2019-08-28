package brian.stream.core.impl;

public class StreamProcessorConfig {

	private final String outputFileOne;
	private final String outputFileTwo;
	
	private StreamProcessorConfig(final Builder builder) {
		outputFileOne = builder.outputFileOne;
		outputFileTwo = builder.outputFileTwo;
	}
	
	public String getOutputFileOne() {
		return outputFileOne;
	}

	public String getOutputFileTwo() {
		return outputFileTwo;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		
		private String outputFileOne;
		private String outputFileTwo;
		
		public Builder withOutputFileOne(final String outputFileOneIn) {
			outputFileOne = outputFileOneIn;
			return this;
		}
		
		public Builder withOutputFileTwo(final String outputFileTwoIn) {
			outputFileTwo = outputFileTwoIn;
			return this;
		}
		
		public StreamProcessorConfig build() {
			return new StreamProcessorConfig(this);
		}
		
	}
	
}
