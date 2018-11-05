package pages;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import data.ConfigManager;
import locator.AdhocWait;
import locator.AnnotatedWebElement;
import locator.MobileElementLocatorFactory;
import locator.RelativeUrl;
import locator.RequiredElement;
import robot.Robot;
import robot.RobotUtil;

public class BasePage {
	protected Robot robot = ConfigManager.getRobot();

	protected BasePage() {
		if (robot.isMobile()) {
			PageFactory.initElements(new MobileElementLocatorFactory(robot.getDriver()), this);
		} else {
			PageFactory.initElements(robot.getDriver(), this);
		}
	}

	public void navigateTo() {
		RelativeUrl url = this.getClass().getDeclaredAnnotation(RelativeUrl.class);
		if (url != null) {
			String s = RobotUtil.getString(url.value());
			robot.navigateTo(s);
		} else {
			robot.navigateTo("");
		}
	}

	public BasePage waitUntilLoaded() throws InterruptedException {
		try {
			for (AnnotatedWebElement<RequiredElement> e : this._getAllAnnotatedElements(RequiredElement.class)) {
				WebDriverWait wdw = robot.getWait();
				if (e.getAnnotation().longWait()) {
					wdw = robot.getLongWait();
				}
				robot.waitFor(e.getWebElement(), wdw);
				
			}
			AdhocWait waitTime = this.getClass().getDeclaredAnnotation(AdhocWait.class);
			if (waitTime != null) {
				Thread.sleep(waitTime.value());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return this;

	}

	// returns a list of WebElement annotated with the specified class
	private <T extends Annotation> List<AnnotatedWebElement<T>> _getAllAnnotatedElements(Class<T> annotationClass)
			throws IllegalArgumentException, IllegalAccessException {
		List<AnnotatedWebElement<T>> elems = new ArrayList<AnnotatedWebElement<T>>();
		for (Field field : this.getClass().getDeclaredFields()) {
			T s = field.getAnnotation(annotationClass);
			if (s != null) {
				if (!(field.get(this) instanceof WebElement)) {
					continue;
				}
				WebElement e = (WebElement) field.get(this);
				elems.add(new AnnotatedWebElement<T>(s, e));
			}
		}
		return elems;
	}

	public WebElement getElementByName(String name)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return (WebElement) this.getClass().getField(name).get(this);
	}

	public WebElement getElementByMethod(String method, String param)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,
			NoSuchMethodException, InvocationTargetException {
		Method m = this.getClass().getMethod(method, String.class);
		return (WebElement) m.invoke(this, param);
	}
}
