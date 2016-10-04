package com.nenov.appiumframework.tests.basetests;

import com.nenov.appiumframework.logging.Logger;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{

    @Test
    public void doMagic() {
        Logger.log("The magic is done!");
    }
}
