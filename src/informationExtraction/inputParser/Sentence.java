package informationExtraction.inputParser;

public class Sentence {

	private String text;
	private String section;
	private String chapter;

	private String scope;
	private String jurisdicion;
	private String subject;
	private String goal;
	private String constraint;

	public Sentence(String text, String section, String chapter) {
		this.text = text;
		this.section = section;
		this.chapter = chapter;
	}

	public String toString() {
		return "Chapter " + chapter + " - Section " + section + " - " + text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getJurisdicion() {
		return jurisdicion;
	}

	public void setJurisdicion(String jurisdicion) {
		this.jurisdicion = jurisdicion;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}

}
