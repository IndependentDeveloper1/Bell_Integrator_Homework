package pages;

import drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import utils.PageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GooglePageWithSearchPOFactory implements Page {

    protected WebDriver driver;

    @FindAll(@FindBy(xpath = "//div[@class='g' and not(@data-hveid)]"))
    private List<WebElement> searchItems;

    @FieldName("Строка поиска")
    @FindBy(name = "q")
    private WebElement searchField;

    private String selectorURL = ".//a[@href]";
    private String selectorNamePage = ".//h3";
    private String selectorDescription = ".//div[@class='IsZvec']";

    private List<Map<String,Object>> collectedResults = new ArrayList<>();

    public GooglePageWithSearchPOFactory() {
        initPage(driver);
        this.driver = WebDriverManager.getCurrentDriver();
    }

    @Override
    public boolean isPageLoaded() {
        return PageUtils.isVisible(searchField);
    }

    public void find(String findKeys) {
        searchField.clear();
        searchField.sendKeys(findKeys + Keys.ENTER);
    }

    public List<Map<String,Object>> getSearchResults() {
        PageUtils.waitUntilElementsCountWillBeMoreThen("//div[@class ='tF2Cxc']//div[@class = 'TbwUpd']", 5);
        for (WebElement result : searchItems)
            collectedResults.add(Map.of(
                    "WEB_ELEMENT", result,
                    "URL", result.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    "NAME_PAGE", result.findElement(By.xpath(selectorNamePage)).getText(),
                    "DESCRIPTION", result.findElement(By.xpath(selectorDescription)).getText()
            ));
        return collectedResults;
    }

    public boolean goPage(String namePage){
        WebElement pageLink = (WebElement) collectedResults.stream()
                .filter(x->x.get("NAME_PAGE").toString().contains(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        pageLink.findElement(By.xpath(selectorURL)).click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs){
            driver.switchTo().window(tab);
            if(driver.getTitle().contains(namePage))
                return true;
        }
        return false;
    }
}
