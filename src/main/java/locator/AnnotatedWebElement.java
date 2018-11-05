package locator;

import org.openqa.selenium.WebElement;

public class AnnotatedWebElement<T> {

	public T annotation;
	public WebElement webElement;

	public AnnotatedWebElement(T annotation, WebElement webElement) {
		super();
		this.annotation = annotation;
		this.webElement = webElement;
	}

	public T getAnnotation() {
		return annotation;
	}

	public WebElement getWebElement() {
		return webElement;
	}
	
	
}
