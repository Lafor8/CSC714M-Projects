package informationExtraction;

public class ExtractionResult {

	private String goal = "";
	private String subject = "";
	private String scope = "";
	private String constraint = "";
	private String jurisdiction = "";

	private String section = "";
	private String originalSentence = "";
	private String clause = "";

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = "\"" + goal + "\"";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = "\"" + subject + "\"";
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = "\"" + scope + "\"";
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = "\"" + constraint + "\"";
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = "\"" + jurisdiction + "\"";
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = "\"" + section + "\"";
	}

	public String getOriginalSentence() {
		return originalSentence;
	}

	public void setOriginalSentence(String originalSentence) {
		this.originalSentence = "\"" + originalSentence + "\"";
	}

	public String getClause() {
		return clause;
	}

	public void setClause(String clause) {
		this.clause = "\"" + clause + "\"";
	}

	public String getAsCSVFormat() {
		StringBuilder sb = new StringBuilder();
		sb.append(section).append(",").append(goal).append(",").append(scope).append(",").append(subject).append(",")
				.append(constraint).append(",").append(jurisdiction).append(",").append(clause).append(",")
				.append(originalSentence).append("\n");

		return sb.toString();
	}
}
