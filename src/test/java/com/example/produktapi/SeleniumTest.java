package com.example.produktapi;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;


public class SeleniumTest {

    /*
    @BeforeAll
    public static void init() {
         // Hämta in en webdriver som ska användas
        driver = new ChromeDriver();
        // Navigera till den webbsida som ska testas
        driver.navigate().to("https://java22.netlify.app/");
    }


     */

    /* TODO
        G - krav:
        a. Kontrollera att webbplatsens titel stämmer. <-- KLAR
        b. Kolla att det totala antalet produkter stämmer. <-- KLAR
        c. Kontrollera att priset blir rätt på minst 3 produkter. <-- KLAR
        -------------------------------------------------------------
        VG - krav:
        a. Skriv ut alla kategorier och kolla att de har rätt namn. <-- KLAR
        b. Kontrollera priset på ytterligare 3 produkter. <-- KLAR
        c. Kontrollera att bilderna skrivs ut/visas till minst 3 produkter. <-- KLAR
        d. Kontrollera att bilden har rätt src-attribut på minst 3 produkter. <-- KLAR
        e. Kontrollera att alla produkter som skrivs ut har rätt namn. <-- KLAR
        f. Gör produktkategorierna klickbara och testa att rätt antal produkter
           visas vid klick på respektive kategori. <-- KLAR
        -------------------------------------------------------------
        lägg till BeforeAll och AfterAll.
     */


    // G - krav:
    // a. Kontrollera att webbplatsens titel stämmer. <- KLAR
    @Test
    public void checkTitleWithChromeJava22() {
        // Hämta in en webdriver som ska användas
        WebDriver driver = new ChromeDriver();


        driver.get("https://java22.netlify.app/");


        // Testa om förväntad titel matchar webbplatsens title
        assertEquals("Webbutik", driver.getTitle(), "Titeln stämmer inte med förväntat");

        driver.quit();
    }

    // G - krav:
    // b. Kolla att det totala antalet produkter stämmer. <- KLAR
    @Test
    public void numberOfProductsShouldBeTwenty() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // Vänta i 10 sekunder så att sidan hinner ladda in elementen jag vill få ut
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("productItem")));

        List<WebElement> products = driver.findElements(By.className("productItem"));

        assertEquals(20, products.size(), "Antalet produkter stämmer inte");

        driver.quit();
    }


    // G - krav:
    // c. Kontrollera att priset blir rätt på minst 3 produkter <- KLAR
    // checkPrice 1/3
    @Test
    public void checkPriceOfFjallravenBackpack() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Fin väska me plats för dator')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d.]", "");

        assertEquals("109.95", price, "Priset stämmer inte!");

        driver.quit();
    }



    // checkPrice 2/3
    @Test
    public void checkPriceOfSandiskSSD() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Den här kan vara bra att ha också.')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d]", ""); // när texten man vill ta bort avslutas med en punk, måste man ta bort punkten efter d i regex

        assertEquals("109", price, "Priset stämmer inte!");

        driver.quit();
    }

    // checkPrice 3/3
    @Test
    public void checkPriceOfPiercedOwl() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Något med en uggla, och i guld och lite dubbel stål')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d.]", ""); // när texten man vill ta bort avslutas med en punk, måste man ta bort punkten efter d i regex

        assertEquals("10.99", price, "Priset stämmer inte!");

        driver.quit();
    }

    //VG - krav:
    // a. Skriv ut alla kategorier och kolla att de har rätt namn.
    // checkIfCategoryHaveCorrectName 1/4
    @Test
    public void checkIfCategoryElectronicsHaveCorrectName() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement electronics = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='root']/div/div[2]/a"
                )));

        assertEquals("electronics", electronics.getText(), "Kategorin stämmer inte!");

        driver.quit();

    }

    // checkIfCategoryHaveCorrectName 2/4
    @Test
    public void checkIfCategoryJeweleryHaveCorrectName() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement jewelery = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/div[3]/a"
                )));

        assertEquals("jewelery", jewelery.getText(), "Kategorin stämmer inte!");

        driver.quit();

    }

    // checkIfCategoryHaveCorrectName 3/4
    @Test
    public void checkIfCategoryMensClothingHaveCorrectName() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement mensClothing = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/div[4]/a"
                )));

        assertEquals("men's clothing", mensClothing.getText(), "Kategorin stämmer inte!");

        driver.quit();

    }

    // checkIfCategoryHaveCorrectName 4/4
    @Test
    public void checkIfCategoryWomensClothingHaveCorrectName() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement womensClothing = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/div[5]/a"
                )));

        assertEquals("women's clothing", womensClothing.getText(), "Kategorin stämmer inte!");

        driver.quit();

    }

    // checkIfCategoryHaveCorrectName 5/4 kollar även startsida för att vara safe
    @Test
    public void checkIfCategoryStartsidaHaveCorrectName() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement startsida = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/a"
                )));

        assertEquals("Startsida", startsida.getText(), "Kategorin stämmer inte!");

        driver.quit();

    }

    // VG - krav:
    // b. Kontrollera priset på ytterligare 3 produkter. <- KLAR
    // checkPriceVG 1/3
    @Test
    public void checkPriceOfMensSlimFitTShirt() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Vilken härlig t-shirt, slim fit o casual i ett!')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d.]", ""); // när texten man vill ta bort avslutas med en punk, måste man ta bort punkten efter d i regex

        assertEquals("22.3", price, "Priset stämmer inte!");

        driver.quit();
    }

    // checkPriceVG 2/3
    @Test
    public void checkPriceOfSamsung49Inch() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'En lite böjd skär Men den funkar ändå!')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d.]", ""); // när texten man vill ta bort avslutas med en punk, måste man ta bort punkten efter d i regex

        assertEquals("999.99", price, "Priset stämmer inte!");

        driver.quit();
    }

    // checkPriceVG 3/3
    @Test
    public void checkPriceOfSolGold() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        WebElement cardTextContainingPrice = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//p[contains(text(), 'Denna blir man glad av.')]"
                )));

        String priceToText = cardTextContainingPrice.getText();
        String price = priceToText.replaceAll("[^\\d]", ""); // när texten man vill ta bort avslutas med en punk, måste man ta bort punkten efter d i regex

        assertEquals("168", price, "Priset stämmer inte!");

        driver.quit();
    }

    // VG - krav:
    // c. Kontrollera att bilderna skrivs ut/visas till minst 3 produkter.
    // checkIfImageIsPrinted 1/3
    @Test
    public void checkIfImageOfFjallravenIsPrinted() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // hitta bild-element med hjälp av xpath
        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='productsContainer']/div/div[1]/div/img"
                )));

        // Kontrollerar om bilden är synlig på webbsidan
        boolean isPrinted = imageElement.isDisplayed();
        assertTrue(isPrinted);

        // stäng webbläsaren
        driver.quit();
    }

    // checkIfImageIsPrinted 2/3
    @Test
    public void checkIfImageOfPiercedOwlIsPrinted() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // hitta bild-element med hjälp av xpath
        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/div[6]/div/div/div/div[4]/div/img" // <-- från jewelery kategorin. Från Startsidan --> //*[@id="productsContainer"]/div/div[8]/div/img
                )));

        // Kontrollerar om bilden är synlig på webbsidan
        boolean isPrinted = imageElement.isDisplayed();
        assertTrue(isPrinted);

        // stäng webbläsaren
        driver.quit();
    }

    // checkIfImageIsPrinted 3/3
    @Test
    public void checkIfImageOfDanvouyWomensTShirtIsPrinted() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // hitta bild-element med hjälp av xpath
        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"root\"]/div/div[6]/div/div/div/div[6]/div/img" // <-- från jewelery kategorin. Från Startsidan --> //*[@id="productsContainer"]/div/div[20]/div/img
                )));

        // Kontrollerar om bilden är synlig på webbsidan
        boolean isPrinted = imageElement.isDisplayed();
        assertTrue(isPrinted);

        // stäng webbläsaren
        driver.quit();
    }

    // VG - krav:
    // d. Kontrollera att bilden har rätt src-attribut på minst 3 produkter. <-- KLAR
    // checkIfImageHasCorrectSRCAttribute 1/3
    // får det endast att funka från Startsidan, inte om jag tar xpath från annan kategori. Varför?
    @Test
    public void checkIfImageFjallravenHasCorrectSRCAttribute() {

        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"productsContainer\"]/div/div[1]/div/img"
                )));


        String srcAttribute = imageElement.getAttribute("src");
        String expectedSrcAttribute = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg";

        assertEquals(expectedSrcAttribute, srcAttribute);

        // stäng webbläsaren
        driver.quit();

    }

    // checkIfImageHasCorrectSRCAttribute 2/3
    @Test
    public void checkIfImagePiercedOwlHasCorrectSRCAttribute() {

        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"productsContainer\"]/div/div[8]/div/img"
                )));

        String srcAttribute = imageElement.getAttribute("src");
        String expectedSrcAttribute = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg";

        assertEquals(expectedSrcAttribute, srcAttribute);

        // stäng webbläsaren
        driver.quit();

    }

    // checkIfImageHasCorrectSRCAttribute 3/3
    @Test
    public void checkIfImageDanvouyWomensTShirtHasCorrectSRCAttribute() {

        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        WebElement imageElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"productsContainer\"]/div/div[20]/div/img"
                )));

        String srcAttribute = imageElement.getAttribute("src");
        String expectedSrcAttribute = "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg";

        assertEquals(expectedSrcAttribute, srcAttribute);

        // stäng webbläsaren
        driver.quit();

    }

    // VG - krav:
    // e. Kontrollera att alla produkter som skrivs ut har rätt namn. <-- KLAR
    @Test
    public void checkIfAllProductsHaveCorrectTitle() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // Vänta i 10 sekunder så att sidan hinner ladda in elementen jag vill få ut
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

        // Hitta alla produkters namn baserat på html klass attribut och lagra dom i en lista
        List<WebElement> productTitles = driver.findElements(By.className("card-title"));

        // Array som definierar alla förväntade namn på produkterna
        String[] expectedTitles = {
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Mens Casual Premium Slim Fit T-Shirts",
                "Mens Cotton Jacket",
                "Mens Casual Slim Fit",
                "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
                "SolGold Petite Micropave",
                "White Gold Plated Princess",
                "Pierced Owl Rose Gold Plated Stainless Steel Double",
                "WD 2TB Elements Portable External Hard Drive - USB 3.0",
                "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
                "Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5",
                "WD 4TB Gaming Drive Works with Playstation 4 Portable External Hard Drive",
                "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                "Samsung 49-Inch CHG90 144Hz Curved Gaming Monitor (LC49HG90DMNXZA) – Super Ultraw Screen QLED",
                "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",
                "Lock and Love Women's Removable Hooded Faux Leather Moto Biker Jacket",
                "Rain Jacket Women Windbreaker Striped Climbing Raincoats",
                "MBJ Women's SolShort Sleeve Boat Neck V",
                "Opna Women's Short Sleeve Moisture",
                "DANVOUY Womens T Shirt Casual Cotton Short"
        };

        // Kontrollera att antalet produkt titlar matchar med det förväntade antalet
        assertEquals(expectedTitles.length, productTitles.size());

       // Kontrollera varje produkt titel mot dess förväntade titel
        for (int i = 0; i < expectedTitles.length; i++) {
            String actualTitle = productTitles.get(i).getText();
            assertEquals(expectedTitles[i], actualTitle);
        }

        // Avsluta WebDriver
        driver.quit();

    }

    // VG - krav:
    // f. Gör produktkategorierna klickbara och testa att rätt antal produkter
    // visas vid klick på respektive kategori. <-- KLAR
    // testLinkDisplayCorrectAmountOfProducts 1/4
    @Test
    public void testElectronicsLinkDisplaysCorrectAmountOfProducts() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // Vänta tills länken är hittad och klickad
        WebElement electronicsLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("electronics")));
        electronicsLink.click();

        // Kontrollera att rätt mängd produkter visas på webbsidan
        List<WebElement> products = driver.findElements(By.className("productItem"));
        int amountOfProducts = products.size();

        // Säkerställ att det finns rätt mängd produkter på sidan
        assertEquals(6, amountOfProducts);

        // Avsluta WebDriver
        driver.quit();

    }

    // testLinkDisplayCorrectAmountOfProducts 2/4
    @Test
    public void testJeweleryLinkDisplaysCorrectAmountOfProducts() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // Vänta tills länken är hittad och klickad
        WebElement jeweleryLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("jewelery")));
        jeweleryLink.click();

        // Kontrollera att rätt mängd produkter visas på webbsidan
        List<WebElement> products = driver.findElements(By.className("productItem"));
        int amountOfProducts = products.size();

        // Säkerställ att det finns rätt mängd produkter på sidan
        assertEquals(4, amountOfProducts);

        // Avsluta WebDriver
        driver.quit();

    }

    // testLinkDisplayCorrectAmountOfProducts 3/4
    @Test
    public void testMensClothingLinkDisplaysCorrectAmountOfProducts() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // Vänta tills länken är hittad och klickad
        WebElement mensClothingLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("men's clothing")));
        mensClothingLink.click();

        // Kontrollera att rätt mängd produkter visas på webbsidan
        List<WebElement> products = driver.findElements(By.className("productItem"));
        int amountOfProducts = products.size();

        // Säkerställ att det finns rätt mängd produkter på sidan
        assertEquals(4, amountOfProducts);

        // Avsluta WebDriver
        driver.quit();

    }

    // testLinkDisplayCorrectAmountOfProducts 4/4
    @Test
    public void testWomensClothingLinkDisplaysCorrectAmountOfProducts() {
        // Hämta in den webdriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testa
        driver.get("https://java22.netlify.app/");

        // Vänta tills länken är hittad och klickad
        WebElement womensClothingLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("women's clothing")));
        womensClothingLink.click();

        // Kontrollera att rätt mängd produkter visas på webbsidan
        List<WebElement> products = driver.findElements(By.className("productItem"));
        int amountOfProducts = products.size();

        // Säkerställ att det finns rätt mängd produkter på sidan
        assertEquals(6, amountOfProducts);

        // Avsluta WebDriver
        driver.quit();

    }

}
