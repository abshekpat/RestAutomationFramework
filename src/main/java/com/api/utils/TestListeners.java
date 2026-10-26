package com.api.utils;

import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.*;


public class TestListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Method name is :"+result.getName());
        System.out.println("Test Starting");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Status of Execution: "+result.getStatus());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("Status of Execution: "+result.getStatus());
    }
}
