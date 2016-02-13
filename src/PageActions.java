
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by rvseer on 2/11/2016.
 */
public class PageActions extends ShoeStore{

    // Home
    @FindBy(xpath = "//a[text()='Home']")
    public WebElement homeLink;

    @FindBy(xpath = "//a[@href='/months/january']")
    public WebElement januaryLink;

    @FindBy(xpath = "//a[@href='/months/february']")
    public WebElement februaryLink;

    @FindBy(xpath = "//a[@href='/months/march']")
    public WebElement marchLink;

    @FindBy(xpath = "//a[@href='/months/april']")
    public WebElement aprilLink;

    @FindBy(xpath = "//a[@href='/months/may']")
    public WebElement mayLink;

    @FindBy(xpath = "//a[@href='/months/june']")
    public WebElement juneLink;

    @FindBy(xpath = "//a[@href='/months/july']")
    public WebElement julyLink;

    @FindBy(xpath = "//a[@href='/months/august']")
    public WebElement augustLink;

    @FindBy(xpath = "//a[@href='/months/september']")
    public WebElement septemberLink;

    @FindBy(xpath = "//a[@href='/months/october']")
    public WebElement octoberLink;

    @FindBy(xpath = "//a[@href='/months/november']")
    public WebElement novemberLink;

    @FindBy(xpath = "//a[@href='/months/december']")
    public WebElement decemberLink;

    @FindBy(xpath = "//input[@id='remind_email_input']")
    public WebElement emailInput;

    @FindBy(xpath = "//input[@id='remind_email_input']/following-sibling::input[@type='submit']")
    public WebElement emailSubmitButton;

    @FindBy (xpath = "//div[@class='flash notice']")
    public WebElement emailValidationMessage;

    @FindBy (xpath = "//ul[@id='shoe_list']")
    public WebElement shoeList;

    // define list of web elements for tables
    public List<WebElement> tableCount;

    // Story_1:  create array list of month links as web elements
    public ArrayList<WebElement> months() {

        ArrayList<WebElement> monthList = new ArrayList<>();
        monthList.add(0, januaryLink);
        monthList.add(1, februaryLink);
        monthList.add(2, marchLink);
        monthList.add(3, aprilLink);
        monthList.add(4, mayLink);
        monthList.add(5, juneLink);
        monthList.add(6, julyLink);
        monthList.add(7, augustLink);
        monthList.add(8, septemberLink);
        monthList.add(9, octoberLink);
        monthList.add(10, novemberLink);
        monthList.add(11, decemberLink);
        return monthList;
    }

    // Story_1:  method to iterate through month links and perform validation calls within shoeCheck method
    public void validateMonths(WebDriver driver) {
        ArrayList<WebElement> monthList = (ArrayList<WebElement>) months();

        for (int i = 0; i < monthList.size(); i++) {
            WebElement element = monthList.get(i);
            element.click();
            shoeCheck(driver, element);
            homeLink.click();
        }
    }

    // Story_1:  method that performs validation checks for each month on each shoe
    public void shoeCheck(WebDriver driver, WebElement element) {

        // find all tables on page for shoe results if table exists
            tableCount = shoeList.findElements(By.xpath("//table"));
            if (tableCount.size() == 0) {
            System.out.println("Did not find any shoes on the page for " + element.getText() + ".");
            return;
            }

            // for each shoe result table assert item exists for description, image and price
            for (int i = 0; i < tableCount.size(); i++) {
                WebElement currentTable = tableCount.get(i).findElement(By.xpath(".//tbody"));
                WebElement shoeName = currentTable.findElement(By.xpath(".//td[@class='shoe_result_value shoe_name']"));
                WebElement description = null;
                WebElement image = null;
                WebElement price = null;
                try {
                    description = currentTable.findElement(By.xpath(".//td[@class='shoe_result_value shoe_description']"));
                    assert !description.getText().isEmpty();
                    System.out.println("Assertion for description PASSED for month of " + element.getText() + " for " + shoeName.getText() + ".");
                }catch (AssertionError ae){
                    System.out.println("Assertion for description FAILED for " + element.getText() + " for " + shoeName.getText() + ".");
                }
                try {
                    image = currentTable.findElement(By.xpath(".//td[@class='shoe_image']/img"));
                    String exists = image.getAttribute("src");
                    assert !exists.isEmpty();
                    System.out.println("Assertion for image PASSED for " + element.getText() + " for " + shoeName.getText() + ".");
                }catch (AssertionError ae){
                    System.out.println("Assertion for image FAILED for " + element.getText() + " for " + shoeName.getText() + ".");
                }
                try {
                    price = currentTable.findElement(By.xpath(".//td[@class='shoe_result_value shoe_price']"));
                    assert !price.getText().isEmpty();
                    System.out.println("Assertion for price PASSED for " + element.getText() + " for " + shoeName.getText() + ".");
                }catch (AssertionError ae){
                    System.out.println("Assertion for price FAILED for " + element.getText() + " for " + shoeName.getText() + ".");
                }
            }
        }

    // Story_2:  method to validate message received by user after email submission
    public void submitEmail(WebDriver driver){
        String email = "test@email.com";
        String message = "Thanks! We will notify you of our new shoes at this email: ";
        emailInput.sendKeys(email);
        emailSubmitButton.click();
        try {
            assert emailValidationMessage.getText().equals(message + email);
            System.out.println("Email submission message PASSED.");
        }catch (AssertionError ae){
            System.out.println("Email submission message FAILED.");
        }
    }


}