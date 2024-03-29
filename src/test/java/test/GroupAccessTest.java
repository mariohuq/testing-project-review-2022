package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.GroupPage;
import pages.LoginPage;
import pages.MainPage;
import pages.SettingsPage;
import utils.User;

import static com.google.common.truth.Truth.assertWithMessage;

// TODO тесты не проходят из-за "//*[@data-l='outlandertarget,USER_EDIT_CONFIG,t,USER_EDIT_CONFIG']"
@DisplayName("Checking age limits in groups")
public class GroupAccessTest extends BaseTest {
    private static final User TEST_USER = new User.UserBuilder()
            .setPhoneNumber("+79019076733")
            .setPassword("google_chrome")
            .build();
    private static final String TEST_ID_GROUP_FOR_ADULT = "64262221398255";

    @DisplayName("Before 18")
    @ParameterizedTest
    // TODO в 2024 тест сломается для 2006 г.р. :)
    @ValueSource(ints = {2006, 2007, 2008})
    void minorUserCheckGroup(int year) {
        LoginPage loginPage = new LoginPage(seleniumUtilities);
        MainPage mainPage = loginPage.loginByPhone(TEST_USER);
        SettingsPage settingsPage = mainPage.receiveSettingsPage();
        settingsPage.setBirthdayYear(year);
        GroupPage groupPage = new GroupPage(seleniumUtilities, TEST_ID_GROUP_FOR_ADULT);
        // TODO сообщение в случае провала теста неинформативно,
        //      предлагаю что-то типа "Access should be denied but was granted"
        assertWithMessage("Access denied").that(groupPage.checkAccessByAge()).isFalse();
    }

    @DisplayName("After 18")
    @ParameterizedTest
    @ValueSource(ints = {1993, 1998, 2002})
    void adultUserCheckGroup(int year) {
        LoginPage loginPage = new LoginPage(seleniumUtilities);
        MainPage mainPage = loginPage.loginByPhone(TEST_USER);
        SettingsPage settingsPage = mainPage.receiveSettingsPage();
        settingsPage.setBirthdayYear(year);
        GroupPage groupPage = new GroupPage(seleniumUtilities, TEST_ID_GROUP_FOR_ADULT);
        // TODO сообщение в случае провала теста неинформативно,
        //      предлагаю что-то типа "Access should be granted but was denied"
        assertWithMessage("Access granted").that(groupPage.checkAccessByAge()).isTrue();
    }
}