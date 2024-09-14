package androidsamples.java.dicegames;

import android.speech.tts.TextToSpeech;
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
  private  int test_val;
  private int in_pointer;
//  private TextToSpeech textToSpeech;

  /**
   * The no argument constructor.
   */
  public WalletViewModel() {
    // TODO implement method
    die = new Die6();
    test_val = 0;
    balance = 0;
    sixesRolled = 0;
    totalDieRolls = 0;
    previous = 0;
    doubleOthers = 0;
    doubleSixes = 0;
  }
  public void setTestValue(int value) {
    test_val = value;
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
  public  void rollDie() {
    // TODO implement method
    die.roll();
    Log.d(TAG, "Die rolled: "+ die.value());
    totalDieRolls++;
    Log.d(TAG, "Total dice rolled: "+ totalDieRolls);
    in_pointer = 0;
    if(die.value() == WIN_VALUE){
        sixesRolled++;
        in_pointer = 1;
        Log.d(TAG, "Sixes rolled: "+ sixesRolled);
        if(previous == WIN_VALUE){
          balance = balance + 2*INCREMENT;
          doubleSixes++;
          in_pointer = 2;
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
        in_pointer = 3;
        doubleOthers++;
      }
      previous = die.value();
    }
  }

  public void rollDie(int val) {
    // TODO implement method
//    StringBuilder message = new StringBuilder("You rolled a " + val + ".");
    test_val = val;
    Log.d(TAG, "Die rolled: "+ val);
    totalDieRolls++;
    Log.d(TAG, "Total dice rolled: "+ totalDieRolls);
    if(val == WIN_VALUE){
      sixesRolled++;
      Log.d(TAG, "Sixes rolled: "+ sixesRolled);
      if(previous == WIN_VALUE){
        balance = balance + 2*INCREMENT;
        doubleSixes++;
//        message.append("You earned 10 coins.");
      }
      else {
        balance += INCREMENT;
//        message.append("You earned 5 coins.");
      }
      previous = WIN_VALUE;
      Log.d(TAG, "New balance: "+ balance);

      // Speak the result
//      message.append("Your balance is "+ balance +"coins.");
//      textToSpeech.speak(message.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }
    else{
      if(previous == dieValue()){
        balance = balance - INCREMENT;
//        message.append("You lost 5 coins.");
        doubleOthers++;
      }
      previous = val;
    }
  }

  /**
   * Reports the current value of the {@link Die}.
   *
   */
  public int dieValue() {
    // TODO implement method
    return (die.value() != 0) ? die.value() : test_val;

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

  public int get_val(){
    return test_val;
  }

  public int increment(){
    if(in_pointer == 0) return 0;
    else if(in_pointer == 1) return 5;
    else if(in_pointer == 2) return 10;
    else if(in_pointer == 3) return -5;
    else return 1;
  }
}
