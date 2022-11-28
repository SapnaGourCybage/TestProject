package utils;

import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseClass {
    public ExtentTest test;


    @BeforeSuite
    public static  void setUp() throws IOException {


    }
    }