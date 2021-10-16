package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class GooglePF {

    private WebDriver chromeDriver;

    @FindBy(how= How.CLASS_NAME,className="gLFyf")
    WebElement searchField;

    @FindBy(how= How.XPATH, xpath="//*[(@class='gNO89b') or (@class='Tg7LZd')]")
    WebElement searchButton;

    @FindBy(how= How.XPATH, using = "//*[@class='g']")
    List<WebElement> allElements;

    public GooglePF(WebDriver chromeDriver){
        this.chromeDriver=chromeDriver;
    }

    public  void find(String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }

    public List<WebElement> getElements() {
        return allElements;
    }
}
