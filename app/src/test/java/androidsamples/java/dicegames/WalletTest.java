package androidsamples.java.dicegames;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockStatic;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WalletTest {

  private MockedStatic<Log> logMock;

  @Before
  public void setup() {
    // Initialize the mock before each test
    logMock = mockStatic(Log.class);
  }

  @After
  public void tearDown() {
    // Close the mock after each test to avoid conflicts
    logMock.close();
  }

  @Test
  public void test1() {
    WalletViewModel model = new WalletViewModel();
    model.rollDie(6);
    model.rollDie(2);
    model.rollDie(2);
    assertEquals(0, model.balance());
    assertEquals(1, model.singleSixes());
    assertEquals(1, model.doubleOthers());
  }


  @Test
  public void test2(){
    WalletViewModel test2 = new WalletViewModel();
    int[] rolls = {1,2,3,6,5,5,6,6,1};
      for (int j : rolls) {
          test2.rollDie(j);
      }

    assertEquals(15,test2.balance());
    assertEquals(3,test2.singleSixes());
    assertEquals(1,test2.doubleSixes());
    assertEquals(1,test2.doubleOthers());
  }

  @Test
  public void test3(){
    WalletViewModel test3 = new WalletViewModel();
    assertEquals(0,test3.balance());
    int[] rolls = {1,2,4,5,1,9,1,4,4,1,6,6,6,1,1,2};
    for (int j : rolls) {
      test3.rollDie(j);
    }

    assertEquals(15,test3.balance());
    assertEquals(3,test3.singleSixes());
    assertEquals(2,test3.doubleSixes());
    assertEquals(2,test3.doubleOthers());
    assertEquals(rolls.length,test3.totalRolls());
  }

  @Test
  public void test4() {
    WalletViewModel model = new WalletViewModel();
    int[] rolls = {2, 4, 6, 3, 6, 6, 5, 5, 1, 6, 6, 2, 2, 2, 3, 4, 4, 6, 6, 1};

    for (int roll : rolls) {
      model.rollDie(roll);
    }

    assertEquals(30, model.balance());
    assertEquals(7, model.singleSixes());
    assertEquals(3, model.doubleSixes());
    assertEquals(4, model.doubleOthers());
    assertEquals(rolls.length, model.totalRolls());
  }

  @Test
  public void test5() {
    WalletViewModel model = new WalletViewModel();
    int[] rolls = {2, 4, 6, 1, 6, 6, 5, 5, 3, 3, 6, 2, 2, 6, 6, 1, 4, 4, 2, 6,
            6, 6, 3, 3, 5, 5, 6, 6, 1, 1};

    for (int i=0; i<rolls.length; i++) {
      if(i>0)
        assertEquals(rolls[i-1],model.previousRoll());
      model.rollDie(rolls[i]);
    }

    assertEquals(45, model.balance());
    assertEquals(11, model.singleSixes());
    assertEquals(5, model.doubleSixes());
    assertEquals(7, model.doubleOthers());
    assertEquals(rolls.length, model.totalRolls());
  }

  @Test
  public void test6() {
    WalletViewModel model = new WalletViewModel();
    int[] rolls = {2, 4, 6, 1, 6, 6, 5, 5, 3, 3, 6, 2, 2, 6, 6, 1, 4, 4, 2, 6,
            6, 6, 3, 3, 5, 5, 6, 6, 1, 1};

    for (int i=0; i<10; i++) {
      if(i>0)
        assertEquals(rolls[i-1],model.previousRoll());
      model.rollDie(rolls[i]);
    }

    assertEquals(10, model.balance());
    assertEquals(3, model.singleSixes());
    assertEquals(1, model.doubleSixes());
    assertEquals(2, model.doubleOthers());
    assertEquals(10, model.totalRolls());

    for (int i=10; i<20; i++) {
        assertEquals(rolls[i-1],model.previousRoll());
      model.rollDie(rolls[i]);
    }

    assertEquals(25, model.balance());
    assertEquals(7, model.singleSixes());
    assertEquals(2, model.doubleSixes());
    assertEquals(4, model.doubleOthers());
    assertEquals(20, model.totalRolls());

    for (int i=20; i<30; i++) {
        assertEquals(rolls[i-1],model.previousRoll());
      model.rollDie(rolls[i]);
    }

    assertEquals(45, model.balance());
    assertEquals(11, model.singleSixes());
    assertEquals(5, model.doubleSixes());
    assertEquals(7, model.doubleOthers());
    assertEquals(rolls.length, model.totalRolls());
  }
  @Test
  public void test7() {
    WalletViewModel model = new WalletViewModel();
    int[] rolls = {1,1,2,2,3,3,4,4,5,5,6,6,6};

    for (int i=0; i<rolls.length; i++) {
      if(i>0)
        assertEquals(rolls[i-1],model.previousRoll());
      model.rollDie(rolls[i]);
    }

    assertEquals(0, model.balance());
    assertEquals(3, model.singleSixes());
    assertEquals(2, model.doubleSixes());
    assertEquals(5, model.doubleOthers());
    assertEquals(rolls.length, model.totalRolls());
  }


}
