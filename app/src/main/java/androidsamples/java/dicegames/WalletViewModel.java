package androidsamples.java.dicegames;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {
  private static final int INCREMENT = 5;
  private static final int WIN_VALUE = 6;
  private static final String TAG = "WalletViewModel";
  private int balance;
  private Die die;
  private int totalDieRolls;
  private int sixesRolled;
  private int previous;
  private int doubleOthers;
  private int doubleSixes;


  /**
   * The no argument constructor.
   */
  public WalletViewModel() {
    // TODO implement method
    die = new Die6();
    balance = 0;
    sixesRolled = 0;
    totalDieRolls = 0;
    previous = 0;
  }

  /**
   * Reports the current balance.
   *
   */
  public int balance() {
    // TODO implement method
    return balance;
  }

  /**
   * Rolls the {@link Die} in the wallet and implements the changes accordingly.
   */
  public void rollDie() {
    // TODO implement method
    die.roll();
    Log.d(TAG, "Die rolled: "+ die.value());
    totalDieRolls++;
    Log.d(TAG, "Total dice rolled: "+ totalDieRolls);
    if(die.value() == WIN_VALUE){
        sixesRolled++;
        Log.d(TAG, "Sixes rolled: "+ sixesRolled);
        if(previous == WIN_VALUE){
          balance = balance + 2*INCREMENT;
          doubleSixes++;
        }
        else {
          balance += INCREMENT;
        }
        previous = WIN_VALUE;
        Log.d(TAG, "New balance: "+ balance);
    }
    else{
      if(previous == dieValue()){
        balance = balance - INCREMENT;
        doubleOthers++;
      }
      previous = die.value();
    }
  }

  /**
   * Reports the current value of the {@link Die}.
   *
   */
  public int dieValue() {
    // TODO implement method
    return die.value();
  }

  /**
   * Reports the number of single (or first) sixes rolled so far.
   *
   */
  public int singleSixes() {
    // TODO implement method
    return sixesRolled;
  }

  /**
   * Reports the total number of dice rolls so far.
   *
   */
  public int totalRolls() {
    // TODO implement method
    return totalDieRolls;
  }

  /**
   * Reports the number of times two sixes were rolled in a row.
   *
   */
  public int doubleSixes() {
    // TODO implement method
    return doubleSixes;
  }

  /**
   * Reports the number of times any other number was rolled twice in a row.
   *
   */
  public int doubleOthers() {
    // TODO implement method
    return doubleOthers;
  }

  /**
   * Reports the value of the die on the previous roll.
   *
   */
  public int previousRoll() {
    // TODO implement method
    return previous;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    Log.d(TAG, "onCleared");
  }
}
