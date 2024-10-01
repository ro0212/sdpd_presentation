
package androidsamples.java.dicegames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Locale;

public class WalletActivity extends AppCompatActivity {

  private TextView balanceView;
  //private Button button;
  private static final String TAG = "WalletActivity";
  private static final int WIN_VALUE = 6;
  private TextView sixesRolledView;
  private TextView totalDiceRolledView;
  private TextView doubleSixesView;
  private TextView doubleOthersView;
  private TextView previousView;

  private TextToSpeech textToSpeech;
  private static final int REQUEST_CODE_SPEECH_INPUT = 100;

  private WalletViewModel walletVM;  // Follow camelCase naming conventions
  ImageView dieFaceImageView;
  // Variables for speech recognition
  private SpeechRecognizer speechRecognizer;
  private Intent recognizerIntent;
  private boolean isVoiceCommandActive = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    setContentView(R.layout.activity_wallet);

    // UI Elements
    balanceView = findViewById(R.id.coins_balance_label);
    //button = findViewById(R.id.die_face);
    dieFaceImageView = findViewById(R.id.die_face_image);
    sixesRolledView = findViewById(R.id.sixes_rolled_score);
    totalDiceRolledView = findViewById(R.id.total_dice_rolls_score);
    doubleOthersView = findViewById(R.id.double_others_score);
    doubleSixesView = findViewById(R.id.double_sixes_score);
    previousView = findViewById(R.id.previous_roll_score);

    // Initialize TextToSpeech
    textToSpeech = new TextToSpeech(this, status -> {
      if (status == TextToSpeech.SUCCESS) {
        textToSpeech.setLanguage(Locale.US);
      }
    });

    // Initialize ViewModel
    walletVM = new ViewModelProvider(this).get(WalletViewModel.class);
    updateUI();

    // Set button click to roll the die
    dieFaceImageView.setOnClickListener(view -> {
      int test_val = walletVM.get_val();
      if (test_val != 0) {
        walletVM.rollDie(test_val);
      } else {
        walletVM.rollDie();
      }
      updateUI();

      if (walletVM.dieValue() == WIN_VALUE) {
        Toast.makeText(WalletActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
      }
    });
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_SPEECH_INPUT);
      }
    }



    // Initialize SpeechRecognizer
    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
    setupSpeechRecognizer();

    // Setup recognizer intent
    recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);

    // Start listening when the app starts
    startListening();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        startListening();  // Permission granted
      } else {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
      }
    }
  }
// Hello world
  @SuppressLint("SetTextI18n")
  private void updateUI() {
    balanceView.setText(Integer.toString(walletVM.balance()));
    sixesRolledView.setText(Integer.toString(walletVM.singleSixes()));
    totalDiceRolledView.setText(Integer.toString(walletVM.totalRolls()));
    previousView.setText(Integer.toString(walletVM.previousRoll()));
    doubleSixesView.setText(Integer.toString(walletVM.doubleSixes()));
    doubleOthersView.setText(Integer.toString(walletVM.doubleOthers()));
    switch (walletVM.dieValue()) {
      case 1:
        dieFaceImageView.setImageResource(R.drawable.dice1);
        break;
      case 2:
        dieFaceImageView.setImageResource(R.drawable.dice2);
        break;
      case 3:
        dieFaceImageView.setImageResource(R.drawable.dice3);
        break;
      case 4:
        dieFaceImageView.setImageResource(R.drawable.dice4);
        break;
      case 5:
        dieFaceImageView.setImageResource(R.drawable.dice5);
        break;
      case 6:
        dieFaceImageView.setImageResource(R.drawable.dice6);
        break;
    }

  }

  // Start listening for voice commands
  private void startListening() {
    if (speechRecognizer != null) {
      speechRecognizer.startListening(recognizerIntent);
    }
  }

  // Setup SpeechRecognizer
  private void setupSpeechRecognizer() {
    speechRecognizer.setRecognitionListener(new RecognitionListener() {
      @Override
      public void onReadyForSpeech(Bundle params) { }

      @Override
      public void onBeginningOfSpeech() { }

      @Override
      public void onRmsChanged(float rmsdB) { }

      @Override
      public void onBufferReceived(byte[] buffer) { }

      @Override
      public void onEndOfSpeech() { }

      @Override
      public void onError(int error) {
        startListening();
      }

      @Override
      public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null && !matches.isEmpty()) {
          String command = matches.get(0).toLowerCase();
          Log.d(TAG, "Recognized command: " + command);  // Log the command

          if ((command.contains("dice games") || command.contains("dice") ||
                  command.contains("games"))&& !isVoiceCommandActive) {
            voice_commands_activated();
            isVoiceCommandActive = true;
            Toast.makeText(WalletActivity.this, "Voice commands activated", Toast.LENGTH_SHORT).show();
          } else if (command.contains("close") && isVoiceCommandActive) {
            voice_commands_deactivated();
            isVoiceCommandActive = false;
            Toast.makeText(WalletActivity.this, "Voice commands deactivated", Toast.LENGTH_SHORT).show();
          } else if (isVoiceCommandActive) {
            processVoiceCommand(command);
          }
        }
        startListening();
      }


      @Override
      public void onPartialResults(Bundle partialResults) { }

      @Override
      public void onEvent(int eventType, Bundle params) { }
    });
  }

  // Handle recognized voice commands
  private void handleVoiceCommand(String command) {
    if (command.contains("hello")) {
      Toast.makeText(WalletActivity.this, "Hello recognized", Toast.LENGTH_SHORT).show();
    }

    if (command.contains("dice") && command.contains("games") && !isVoiceCommandActive) {
      isVoiceCommandActive = true;
      Toast.makeText(WalletActivity.this, "Voice command activated", Toast.LENGTH_SHORT).show();
    }
    else if (command.contains("close dice games") && isVoiceCommandActive) {
      isVoiceCommandActive = false;
      Toast.makeText(WalletActivity.this, "Voice command deactivated", Toast.LENGTH_SHORT).show();
    } else if (isVoiceCommandActive) {
      processVoiceCommand(command);
    }
  }

  // Process voice commands
  private void processVoiceCommand(String command) {
    if (command.contains("roll") && command.contains("die") || command.contains("roll ")) {
      walletVM.rollDie();
      updateUI();
      speakUpdatedValues();
    } else if (command.contains("number") && command.contains("coins")) {
      speakCoins();
    } else if (command.contains("sixes")) {
      speakSixes();
    } else if (command.contains("total") && command.contains("rolls")) {
      speakTotal();
    } else if (command.contains("rules") || command.contains("game rules")) {
      speakRules();
    } else {
      Toast.makeText(this, "Command not recognized. Please try again.", Toast.LENGTH_SHORT).show();
    }
  }

  // Voice feedback methods...
  // (No changes needed in the speak methods)
  // Voice feedback methods
  private  void voice_commands_activated(){
    String message = "Voice Command Activated";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }
  private  void voice_commands_deactivated(){
    String message = "Voice Command deactivated";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }
  private void speakRules() {
    String message = "When you roll the die, if you get a six, you earn five coins. " +
            "If you roll consecutive sixes, you earn ten coins. " +
            "Rolling any other number consecutively loses you five coins.";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakTotal() {
    String message = "You have rolled the die " + walletVM.totalRolls() + " times.";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakSixes() {
    String message = "You have rolled " + walletVM.singleSixes() + " single sixes and " +
            walletVM.doubleSixes() + " double sixes.";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakCoins() {
    String message = "You have " + walletVM.balance() + " coins.";
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  private void speakUpdatedValues() {
    String message = "You rolled a " + walletVM.dieValue() + ". ";
    int increment = walletVM.increment();
    if (increment == 0) {
      message += "You didn't get any coins.";
    } else if (increment == 5) {
      message += "You got 5 coins.";
    } else if (increment == 10) {
      message += "You got 10 coins.";
    } else if (increment == -5) {
      message += "You lost 5 coins.";
    }
    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
  }

  @Override
  protected void onPause() {
    if (speechRecognizer != null) {
      speechRecognizer.stopListening();
    }
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    startListening();
  }


  @Override
  protected void onDestroy() {
    if (speechRecognizer != null) {
      speechRecognizer.destroy();
    }
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }
    super.onDestroy();
  }
}



