package androidsamples.java.dicegames;

//import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

//    if(savedInstanceState != null){
//        balance = savedInstanceState.getInt(KEY_BALANCE,0);
//        int dieValue = savedInstanceState.getInt(KEY_DIE_VALUE,0);
//        balanceView.setText(Integer.toString(balance));
//        button.setText(Integer.toString(dieValue));
//        Log.d(TAG, "Restored balance = "+balance +" die = "+ dieValue);
//    }
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

          Toast toast = Toast.makeText(WalletActivity.this, text, duration);
          toast.show();
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
  @Override
  protected void onDestroy(){
    super.onDestroy();
    Log.d(TAG,"onDestroy");
  }
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