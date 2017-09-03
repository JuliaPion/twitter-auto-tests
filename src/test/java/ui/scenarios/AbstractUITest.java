package ui.scenarios;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.testng.annotations.BeforeSuite;
import ui.pages.LoginPage;

import java.io.File;
import java.net.URL;

import static com.codeborne.selenide.Selenide.page;
import static org.apache.commons.io.FileUtils.*;
import static org.assertj.core.api.Assertions.assertThat;


public abstract class AbstractUITest {
    private static final File TMP_DIR = getFile(getTempDirectory(), "california-test");

    static {
        String relPath = AbstractUITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        String reportsPath = String.format("%s../../target/reports", relPath);
        System.setProperty("selenide.reports", reportsPath);
        System.setProperty("selenide.baseUrl", "https://twitter.com");
        System.setProperty("browser", "chrome");
        System.setProperty("webdriver.chrome.driver", getChromeDriverBinary());
    }

    private static String getChromeDriverBinary() {
        String resourcePath = System.getProperty("webdriver.chrome.driver", getDefaultChromeDriverPath());
        File file = getFileFromPath(resourcePath);
        assertThat(file).exists();
        assertThat(file.canExecute()).isTrue();

        return file.getAbsolutePath();
    }

    private static File getFileFromPath(String resourcePath) {
        if (resourcePath.startsWith("classpath:")) {
            String fileName = StringUtils.substring(resourcePath, "classpath:".length());
            URL url = AbstractUITest.class.getClassLoader().getResource(fileName);
            assertThat(url).isNotNull();
            return new File(url.getFile());
        } else if (resourcePath.startsWith("file:")) {
            String fileName = StringUtils.substring(resourcePath, "file:".length());
            return new File(fileName);
        } else {
            return new File(resourcePath);
        }
    }

    private static String getDefaultChromeDriverPath() {
        return SystemUtils.IS_OS_WINDOWS ? "classpath:chromedriver2.31_win32.exe" : "classpath:chromedriver_linux32";
    }

    private LoginPage loginPage = page(LoginPage.class);

    @BeforeSuite
    public void login() throws Exception {
        deleteDirectory(TMP_DIR);
        forceMkdir(TMP_DIR);
        loginPage.openPage();
        loginPage.setUsernameInput("userName");
        loginPage.setPasswordInput("password");
        loginPage.getLogInButton().click();
    }

}
