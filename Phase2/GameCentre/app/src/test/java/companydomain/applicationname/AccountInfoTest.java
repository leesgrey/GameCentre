package companydomain.applicationname;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountInfoTest {

    /** a method that tests AccountInfo.
     *todo: getting a null pointer exception because loadAccountInfo method reutrns null.
     */
    @Test
    public void accountInfoTest() {
        AppCompatActivity randomSignInActivity = new SignInActivity();
        AccountInfo testAccountInfo = AccountInfo.loadAccountInfo(randomSignInActivity);
        assertEquals(false, testAccountInfo.accountExists("testemail"));
        testAccountInfo.addAccount("testemail","testpassword");
        assertEquals(true, testAccountInfo.loginValid("testemail","testpassword"));
        assertEquals(true, testAccountInfo.accountExists("testemail"));

        }

}