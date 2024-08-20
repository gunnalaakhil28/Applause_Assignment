package com.Commons

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class CommonMethods {

	@Keyword
	def navigateToUrl(String url) {

		WebUI.openBrowser('')

		WebUI.navigateToUrl(url)

		WebUI.maximizeWindow()

		WebUI.waitForElementPresent(findTestObject('Object Repository/AcceptCookies/button_ALLE ERLAUBEN'), 15)

		WebUI.click(findTestObject('Object Repository/AcceptCookies/button_ALLE ERLAUBEN'))
		
		boolean status = WebUI.waitForElementPresent(findTestObject('Object Repository/AcceptCookies/button_Close'), 5)
		if(status) {
			
			WebUI.click(findTestObject('Object Repository/AcceptCookies/button_Close'), FailureHandling.OPTIONAL)
		}


		WebUI.takeScreenshot()
	}

	@Keyword
	def selctCriteria(String Criteria) {

		boolean status = WebUI.verifyElementClickable(findTestObject('Filters/select_Criteria', [('Criteria') : Criteria]), FailureHandling.OPTIONAL)
		println(status==false)
		if(status) {
			WebUI.click(findTestObject('Filters/button_Next'))
		}
		WebUI.click(findTestObject('Filters/select_Criteria', [('Criteria') : Criteria]))

		WebUI.delay(2)

		WebUI.click(findTestObject('Filters/button_Next'))

		WebUI.takeScreenshot()
	}



	@Keyword
	def selectFilters(String Marke, String Produktart, String GeschenkFur,String FurWen) {

		selectFilter("Marke", Marke)

		WebUI.delay(5)

		selectFilter("Produktart", Produktart)

		WebUI.delay(5)

		selectFilter("Geschenk für", GeschenkFur)

		WebUI.delay(5)

		selectFilter("Für Wen", FurWen)
	}

	def selectFilter(String selectFilter, String filterStatus) {
		if(filterStatus.contains("?")) {
			WebUI.waitForElementClickable(findTestObject('Filters/select_Filter', [('selectFilter') : selectFilter]), 20)
			WebUI.click(findTestObject('Filters/select_Filter', [('selectFilter') : selectFilter]))

			WebUI.mouseOver(findTestObject('Filters/logo'), FailureHandling.OPTIONAL)
			Mobile.delay(2)

			WebUI.waitForElementClickable(findTestObject('Filters/select_checkBox'), 10)
			WebUI.check(findTestObject('Filters/select_checkBox'))
			WebUI.click(findTestObject('Filters/button_Produkte anzeigen'))
			WebUI.takeScreenshot()
		}
	}

	@Keyword
	def listOutTheProductBasedOnFilters() {

		WebUI.waitForElementVisible(findTestObject('Filters/product_Names'), 10)

		WebDriver driver= DriverFactory.getWebDriver()
		List<WebElement> elements = driver.findElements(By.xpath("//div[@class='text top-brand']"))
		List<String> productNames= new ArrayList<String>()

		for (WebElement element : elements) {

			String productName = element.getText()

			KeywordUtil.logInfo("productBrands = " +productName)

			productNames.add(productName)
		}

		for(String productName : productNames) {
			println(productName)
		}

		println(productNames)

		WebUI.takeFullPageScreenshot()
	}
}
