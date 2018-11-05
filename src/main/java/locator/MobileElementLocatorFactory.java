package locator;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class MobileElementLocatorFactory implements ElementLocatorFactory {
	private final SearchContext searchContext;

	public MobileElementLocatorFactory(SearchContext searchContext) {
		this.searchContext = searchContext;
	}

	@Override
	public ElementLocator createLocator(Field field) {
		return new MobileElementLocator(searchContext, field);
	}

	public class MobileElementLocator extends DefaultElementLocator {
		public MobileElementLocator(SearchContext searchContext, Field field) {
			super(searchContext, new MobileAnnotations(field));
		}
	}

	public class MobileAnnotations extends Annotations {
		private Field field;

		public MobileAnnotations(Field field) {
			super(field);
			this.field = field;
		}

		@Override
		public By buildBy() {
			By ans = null;

			MobileFindBy mobileFindBy = field.getAnnotation(MobileFindBy.class);
			if (mobileFindBy != null) {
				if (mobileFindBy.id() != null && !mobileFindBy.id().isEmpty()) {
					ans = By.id(mobileFindBy.id());
				} else if (mobileFindBy.css() != null && !mobileFindBy.css().isEmpty()) {
					ans = By.cssSelector(mobileFindBy.css());
				} else if (mobileFindBy.xpath() != null && !mobileFindBy.xpath().isEmpty()) {
					ans = By.xpath(mobileFindBy.xpath());
				} else if (mobileFindBy.name() != null && !mobileFindBy.name().isEmpty()) {
					ans = By.name(mobileFindBy.name());
				}
			}

			if (ans == null) {
				ans = super.buildBy();
			}

			return ans;
		}

	}
}
