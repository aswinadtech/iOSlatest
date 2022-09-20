package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class FeedOneCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String feedOneCardAd_AccessibilityId = "weather.feed0-adContainerView";
	String snapshotLogo_AccessibilityId = "SnapshotLogo";
	String buttonClose_AccessibilityId  = "button close";
	
	By byFeedOneCard = MobileBy.AccessibilityId("integrated-ad-card-containerView");
	By byFeedOneCardAd = MobileBy.AccessibilityId(feedOneCardAd_AccessibilityId);
	By bySnapshotLogo = MobileBy.AccessibilityId(snapshotLogo_AccessibilityId);
	By byButtonClose = MobileBy.AccessibilityId(buttonClose_AccessibilityId);
	

	MobileElement feedOneCard = null;
	MobileElement feedOneCardAd = null;
	MobileElement snapshotLogo = null;
	MobileElement buttonClose = null;

	public FeedOneCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To FeedOne Card Content Page")
	public void navigateToFeedOneCardContentPage() throws Exception {

		feedOneCardAd = Ad.findElement(byFeedOneCardAd);
		TestBase.clickOnElement(byFeedOneCardAd, feedOneCardAd, "FeedOneCard Ad");
		Thread.sleep(6000);
		attachScreen();
	}
	
	public  void scrollToFeedOneCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}

}
