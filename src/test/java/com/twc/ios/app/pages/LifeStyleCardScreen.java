package com.twc.ios.app.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

public class LifeStyleCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String lifeStyleCardAllIndexes_Xpath = "(//XCUIElementTypeTable[@name='lifestyle_combo_container'])[1]/XCUIElementTypeCell";
	String cancelButton_AccessibilityId = "Cancel";
	String articlesLink_Xpath = "(//XCUIElementTypeStaticText[@label='Latest News']/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther//XCUIElementTypeButton)[2]";
	String articlesHeader_AccessibilityId = "view_articleCategoryHeader";
	String allergyContentNavigationBar_Xpath = "//XCUIElementTypeNavigationBar[@name='Allergy']";
	String fluContentNavigationBar_Xpath = "//XCUIElementTypeNavigationBar[@name='Flu']";
	String lifeStyleCardDynamicIndex_Xpath = "((//XCUIElementTypeTable[@name='lifestyle_combo_container'])[1]/XCUIElementTypeCell)[";
	String allergyIndex_Xpath = "//XCUIElementTypeCell[@name='allergy_cell']";
	String allergyIndexBreathing_Xpath = "//XCUIElementTypeCell[@name='breathing_cell']";

	By byLifeStyleCardAllIndexes = MobileBy.xpath(lifeStyleCardAllIndexes_Xpath);
	By byArticlesLink = MobileBy.xpath(articlesLink_Xpath);
	By byArticlesHeader = MobileBy.xpath(articlesHeader_AccessibilityId);
	By byAllergyContentNavigationBar = MobileBy.xpath(allergyContentNavigationBar_Xpath);
	By byFluContentNavigationBar = MobileBy.xpath(fluContentNavigationBar_Xpath);
	
	

	MobileElement cancelButton = null;
	MobileElement articlesLink = null;
	MobileElement articlesHeader = null;
	MobileElement allergyContentNavigationBar = null;
	MobileElement fluContentNavigationBar = null;
	MobileElement lifeStyleCardDynamicIndex = null;
	MobileElement lifeStyleCardDynamicIndexLabel = null;
	MobileElement allergyIndex = null;

	public LifeStyleCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigae To All Indexes of LifeStyle Card")
	public void navigateToLifeStyleCardIndexes() throws Exception {

		try {

			List<MobileElement> ls;
			/*
			 * ls = Ad.findElementsByXPath(
			 * "(//XCUIElementTypeTable[@name='lifestyle_combo_container'])[1]/XCUIElementTypeCell"
			 * );
			 */
			ls = Ad.findElementsByXPath(lifeStyleCardAllIndexes_Xpath);
			int lssize = ls.size();
			for (int l = 1; l <= lssize; l++) {
				By byLifeStyleCardDynamicIndex = MobileBy.xpath(lifeStyleCardDynamicIndex_Xpath + l + "]");
				By byLifeStyleCardDynamicIndexLabel = MobileBy.xpath("("+lifeStyleCardDynamicIndex_Xpath + l + "]/XCUIElementTypeStaticText)[2]");
				
				lifeStyleCardDynamicIndexLabel = Ad.findElement(byLifeStyleCardDynamicIndexLabel);

				
				String currentIndex = lifeStyleCardDynamicIndexLabel.getAttribute("label");

				System.out.println("Current subindex : " + currentIndex);
				logStep("Current subindex : " + currentIndex);
				
				/*
				 * if (subIndex.equalsIgnoreCase("Flu")) { continue; //
				 * System.out.println("Current Context is: "+ Ad.getContext()); // this if
				 * condition is added to skip flu validation as flu details page // crashing the
				 * app while inspecting by appium. // to be removed when flu details can be
				 * inspected. }
				 */
				

			
				lifeStyleCardDynamicIndex = Ad.findElement(byLifeStyleCardDynamicIndex);
				
				TestBase.clickOnElement(byLifeStyleCardDynamicIndex, lifeStyleCardDynamicIndex,
						"LifeStyle Card Index " + l);
				TestBase.waitForMilliSeconds(6000);

				// System.out.println("Current Context is: "+ Ad.getContext());
				/*
				 * try { Ad.findElementByAccessibilityId("Cancel").click(); } catch (Exception
				 * e) {
				 * 
				 * }
				 */
				
				
				if (currentIndex.equalsIgnoreCase("Running") || currentIndex.equalsIgnoreCase("Boat & Beach")) {
					CharlesProxy.proxy.stopRecording();
					Functions.archive_folder("Charles");
					TestBase.waitForMilliSeconds(5000);
					CharlesProxy.proxy.getXml();
					Utils.createXMLFileForCharlesSessionFile();
					String sheetName = "Test";
					if (currentIndex.equalsIgnoreCase("Running")) {
						sheetName = "Health(goRun)";
					} else if (currentIndex.equalsIgnoreCase("Boat & Beach")){
						sheetName = "Health(boatAndBeach)";
					}
					if (Utils.isInterStitialAdCalExists("Smoke", sheetName)) {

						if (Utils.isInterstitialCall_hasResponse("Smoke", sheetName)) {
							if (unlimitedInterstitial) {
								handle_Interstitial_Ad();
							} else {
								if (!interStitialDisplayed) {
									/*
									 * Since Entry Interstitial displayed upon navigating to Running/Boat & Beat Index pages
									 */
									handle_Interstitial_Ad();
								} else {
									System.out.println("Interstitial Ad is already handled, hence not handling again");
									logStep("Interstitial Ad is already handled, hence not handling again");

								}
							}
						}
					}
				Functions.delete_folder("Charles");
				CharlesProxy.proxy.startRecording();
				}
				
				

				/*
				 * below swipe_up() snippet of code is added to swipe till end of corresponding
				 * index page to generate amazon calls for applicable pages line Running and
				 * Boat & Beach
				 */
				swipe_Up(Ad);
				swipe_Up(Ad);
				swipe_Up(Ad);
				swipe_Up(Ad);
				swipe_Up(Ad);
				if (currentIndex.equalsIgnoreCase("Flu") || currentIndex.equalsIgnoreCase("Allergy")) {
					// to navigate to article pages
					try {
						/*
						 * Ad.findElementByXPath(
						 * "(//XCUIElementTypeStaticText[@label='Latest News']/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther//XCUIElementTypeButton)[2]"
						 * ) .click();
						 */
						navigateToArticlesPage();
						TestBase.waitForMilliSeconds(3000);
						try {
							
							verifyArticlesPageHeader();
							navigateBackToFeedCard();
							TestBase.waitForMilliSeconds(3000);
						} catch (Exception ex) {
							System.out.println("Not able to navigated to article page on : " + currentIndex);
							logStep("Not able to navigated to article page on : " + currentIndex);
						}

						checkForIndexContentPage(currentIndex);

					} catch (Exception e) {
						System.out.println("Article links are not diplayed on : " + currentIndex);
						logStep("Article links are not diplayed on : " + currentIndex);
					}

				}
				attachScreen();
				navigateBackToFeedCard();
								
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Step("Navigate To Articles Page")
	public void navigateToArticlesPage() {
		articlesLink = Ad.findElement(byArticlesLink);
		TestBase.clickOnElement(byArticlesLink, articlesLink, "Articles Link");
	}

	@Step("Verify Articles Page Header")
	public void verifyArticlesPageHeader() {
		articlesHeader = Ad.findElement(byArticlesHeader);
	}

	@Step("Verify Whether App on Articles Page or Index Content Page")
	public void checkForIndexContentPage(String subIndex) {
		if (subIndex.equalsIgnoreCase("Allergy")) {
			try {
				/*
				 * this block is to confirm whether on article page, if yes click on backbutton
				 */
				/*
				 * MobileElement allergyNavigationBar = Ad.findElementByXPath(
				 * "//XCUIElementTypeNavigationBar[@name='Allergy']");
				 */

				allergyContentNavigationBar = Ad.findElement(byAllergyContentNavigationBar);

			} catch (Exception e) {
				System.out.println("Looks app navigated to article page, though exception thrown");
				logStep("Looks app navigated to article page, though exception thrown");
				navigateBackToFeedCard();
			}
		} else if (subIndex.equalsIgnoreCase("Flu")) {
			try {
				/*
				 * this block is to confirm whether on article page, if yes click on backbutton
				 */
				/*
				 * MobileElement fluNavigationBar = Ad
				 * .findElementByXPath("//XCUIElementTypeNavigationBar[@name='Flu']");
				 */

				fluContentNavigationBar = Ad.findElement(byFluContentNavigationBar);
			} catch (Exception e) {
				System.out.println("Looks app navigated to article page, though exception thrown");
				logStep("Looks app navigated to article page, though exception thrown");
				navigateBackToFeedCard();
			}
		}

	}
	
	@Step("Navigate to Allergy Details Page from Lifestyle Card")
	public void navigateToAllergyDetailsPage_from_lifestyle_card() throws Exception {
		try {
			By byAllergyIndex = null;
			try {
				byAllergyIndex = MobileBy.xpath(allergyIndex_Xpath);
				allergyIndex = Ad.findElement(byAllergyIndex);
				
			} catch (Exception e) {
				byAllergyIndex = MobileBy.xpath(allergyIndexBreathing_Xpath);
				allergyIndex = Ad.findElement(byAllergyIndex);
				
			}
			TestBase.clickOnElement(byAllergyIndex, allergyIndex, "Allergy Index");
			TestBase.waitForMilliSeconds(5000);

		} catch (Exception e) {
			System.out.println("An exception while navigating to allergy details page.");
			logStep("An exception while navigating to allergy details page.");
			e.printStackTrace();
		}

	}

}
