package androidsamples.java.dicegames;

//import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Locale;

public class WalletActivity extends AppCompatActivity {

  private TextView balanceView;
  private Button button;
  private static final String TAG = "WalletActivity";
  private static final int WIN_VALUE = 6;
  private TextView sixesRolledView;
  private TextView totalDiceRolledView;
  private TextView doubleSixesView;
  private TextView doubleOthersView;
  private TextView previousView;

//  private static final String KEY_BALANCE = "KEY_BALANCE";
//  private static final String KEY_DIE_VALUE = "KEY_DIE_VALUE";
  private TextToSpeech textToSpeech;
  private Button startTalkingButton;
  private static final int REQUEST_CODE_SPEECH_INPUT = 100;


  private WalletViewModel WalletVM;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    setContentView(R.layout.activity_wallet);

    balanceView = findViewById(R.id.coins_balance_label);
    button = findViewById(R.id.die_face);
    sixesRolledView = findViewById(R.id.sixes_rolled_score);
    totalDiceRolledView = findViewById(R.id.total_dice_rolls_score);
    doubleOthersView = findViewById(R.id.double_others_score);
    doubleSixesView = findViewById(R.id.double_sixes_score);
    previousView = findViewById(R.id.previous_roll_score);

    startTalkingButton = findViewById(R.id.start_talking_button);
//    if(savedInstanceState != null){
//        balance = savedInstanceState.getInt(KEY_BALANCE,0);
//        int dieValue = savedInstanceState.getInt(KEY_DIE_VALUE,0);
//        balanceView.setText(Integer.toString(balance));
//        button.setText(Integer.toString(dieValue));
//        Log.d(TAG, "Restored balance = "+balance +" die = "+ dieValue);
//    }
    textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
          textToSpeech.setLanguage(Locale.US);
        }
      }
    });

    startTalkingButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startVoiceRecognition();
      }
    });

    WalletVM = new ViewModelProvider(this).get(WalletViewModel.class);
    updateUI();

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int test_val = WalletVM.get_val();
        if(test_val!=0){
          WalletVM.rollDie(test_val);
        }
        else { WalletVM.rollDie();}

        updateUI();

        if(WalletVM.dieValue() == WIN_VALUE){
          CharSequence text = "Congratulations!";
          int duration = Toast.LENGTH_SHORT;

          Toast.makeText(WalletActivity.this, "Congratulations.", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
  @SuppressLint("SetTextI18n")

  private void updateUI(){
    balanceView.setText(Integer.toString(WalletVM.balance()));
    button.setText(Integer.toString(WalletVM.dieValue()));
    sixesRolledView.setText(Integer.toString(WalletVM.singleSixes()));
    totalDiceRolledView.setText(Integer.toString(WalletVM.totalRolls()));
    previousView.setText(Integer.toString(WalletVM.previousRoll()));
    doubleSixesView.setText(Integer.toString(WalletVM.doubleSixes()));
    doubleOthersView.setText(Integer.toString(WalletVM.doubleOthers()));
  }

  private void startVoiceRecognition() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say 'roll the die' or 'my coins'");
    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
      ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      String recognizedText = results.get(0).toLowerCase();
//      if (results != null && results.contains("roll the die")) {
//        // Simulate dice roll
//        WalletVM.rollDie();
//        updateUI();
//        speakUpdatedValues();  // Speak the updated values after roll
//      }
      if (recognizedText.contains("roll") && recognizedText.contains("die")) {
        WalletVM.rollDie();
        updateUI();
        speakUpdatedValues();
      }
      else if(recognizedText.contains("number") && recognizedText.contains("coins")){
        speakCoins();
      }
      else if(recognizedText.contains("sixes")){
        speakSixes();
      }
      else if(recognizedText.contains("total") && recognizedText.contains("rolls")){
        speakTotal();
      }
      else {
        // Handle cases where the input is not recognized as a dice roll command
        Toast.makeText(this, "Command not recognized. Please say 'roll the die'.", Toast.LENGTH_SHORT).show();
      }


    }
  }

  private void speakTotal() {
    String message = "You rolled  " + WalletVM.totalRolls() + " times. ";
    // Speak the message
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakSixes() {
    String message = "You got " + WalletVM.singleSixes() + " single sixes. ";

    message += "You got " + WalletVM.doubleSixes() + " double sixes. ";
    // Speak the message
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakCoins() {
    String message = "You have " + WalletVM.balance() + " coins. ";
    // Speak the message
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakUpdatedValues() {
    String message = "You rolled a " + WalletVM.dieValue() + ". ";
    if(WalletVM.increment() == 0){
      message += "You didn't get any coins. ";
    }
    else if(WalletVM.increment() == 5){
      message += "You got 5 coins. ";
    }
    else if(WalletVM.increment() == 10){
      message += "You got 10 coins. ";
    }
    else if(WalletVM.increment() == -5){
      message += "You lost 5 coins. ";
    }
    // Speak the message
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  @Override
  protected void onDestroy() {
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }
    super.onDestroy();
    Log.d(TAG,"onDestroy");
  }

  @Override
  protected void onStart(){
    super.onStart();
    Log.d(TAG,"onStart");
  }
  @Override
  protected void onResume(){
    super.onResume();
    Log.d(TAG,"onResume");
  }
  @Override
  protected void onStop(){
    super.onStop();
    Log.d(TAG,"onStop");
  }
//  @Override
//  protected void onDestroy(){
//    super.onDestroy();
//
//  }
  @Override
  protected void onPause(){
    super.onPause();
    Log.d(TAG,"onPause");
  }
//  @Override
//  protected void onSaveInstanceState(@NonNull Bundle outState) {
//    super.onSaveInstanceState(outState);
//    Log.d(TAG, "onSaveInstanceState");
//    outState.putInt(KEY_BALANCE, balance);
//    outState.putInt(KEY_DIE_VALUE, die.value());
//    Log.d(TAG, "Saved: balance = "+balance + ", die = "+ die.value());
//  }
}