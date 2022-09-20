package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class NewsCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String newsCard_AccessibilityId = "news_item_0_cell_container";
	
	
	By byNewsCard = MobileBy.AccessibilityId(newsCard_AccessibilityId);
	
	
	MobileElement newsCard = null;
	

	public NewsCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To News Card Content Page")
	public void navigateToNewsCardContentPage() throws Exception {
		newsCard = Ad.findElement(byNewsCard);
		TestBase.clickOnElement(byNewsCard, newsCard, "News Card Details");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
		navigateBackToFeedCard();
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "News(details)")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "News(details)")) {
				if (unlimitedInterstitial) {
					handle_Interstitial_Ad();
				} else {
					if (!interStitialDisplayed) {
						/*
						 * Since Exit Interstitial displayed upon click on back icon, handling it once
						 * click on back
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
	
	@Step("Navigate To News Card Content Page And Not to Handle Interstitials")
	public void navigateToNewsCardContentPageAndDontHandleInterstitials() throws Exception {
		newsCard = Ad.findElement(byNewsCard);
		TestBase.clickOnElement(byNewsCard, newsCard, "News Card Details");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
	}

	
	public void navigateToNewsDetails() {

		try {
			//MobileElement newsDetails = Ad.findElementByAccessibilityId("news_item_0_cell_container");
			//newsDetails.click();
			newsCard = Ad.findElement(byNewsCard);
			TestBase.clickOnElement(byNewsCard, newsCard, "News Card Details");
			TestBase.waitForMilliSeconds(6000);
			attachScreen();
			System.out.println("Navigated to News Details ");
			logStep("Navigated to News Details ");
		} catch (Exception e) {
			System.out.println("News Details not displayed");
			logStep("News Details not displayed");
			// attachScreen();
		}
	}
	
	public  void scrollToNewsCard() throws Exception {
		byNewsCard = MobileBy.AccessibilityId("news-card-containerView");
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}
}
