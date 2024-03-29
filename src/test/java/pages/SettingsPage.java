package pages;

import org.openqa.selenium.By;

import utils.SeleniumUtilities;

public class SettingsPage extends Page {
    private static final By USER_SETTINGS_XPATH = By.xpath("//*[@class='user-settings __profile']//*[@class='user-settings_i_tx textWrap']");
    private static final By CONFIRM_USER_SETTINGS_XPATH = By.xpath("//*[@data-l='t,confirm']");


    public SettingsPage(SeleniumUtilities seleniumUtilities) {
        super(seleniumUtilities);
    }

    // TODO неиспользуемый метод
    @Override
    public String getUrl() {
        return seleniumUtilities.getCurrentURL();
    }

    public void setBirthdayYear(int year) {
        seleniumUtilities.click(USER_SETTINGS_XPATH);
        seleniumUtilities.click(By.xpath("//select[@name='fr.byear']" + "//option[@value='" + year + "']"));
        seleniumUtilities.click(CONFIRM_USER_SETTINGS_XPATH);
    }
}
