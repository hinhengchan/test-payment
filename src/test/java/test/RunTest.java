package test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        plugin = { 
                    "pretty",
                    "json:target/json-report.json",
					"junit:target/junit-report.xml",
                    "html:target/html",
                },
        monochrome = true
        )
public class RunTest {
}