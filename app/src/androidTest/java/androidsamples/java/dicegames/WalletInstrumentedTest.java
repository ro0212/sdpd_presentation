package androidsamples.java.dicegames;

import static org.junit.Assert.assertEquals;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WalletInstrumentedTest {
  @Test
  public void simple_test() {
    // TO MANAGE THE LIFECYCLE OF ACTIVITY DURING TESTS
    try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(WalletActivity.class)) {
      scenario.onActivity(activity -> {
        WalletViewModel viewModel = new ViewModelProvider(activity).get(WalletViewModel.class);


        Button rollButton = activity.findViewById(R.id.die_face);
        rollButton.performClick();
        rollButton.performClick();
        rollButton.performClick();

        TextView totalDiceRolledView = activity.findViewById(R.id.total_dice_rolls_score);
        assertEquals("3", totalDiceRolledView.getText().toString());  // Example check
      });
    }
  }

  @Test
  public void TestWithMultiple_predefined_inputs1() {
    // TO MANAGE THE LIFECYCLE OF ACTIVITY DURING TESTS
    try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(WalletActivity.class)) {
      scenario.onActivity(activity -> {
        WalletViewModel viewModel = new ViewModelProvider(activity).get(WalletViewModel.class);

        viewModel.setTestValue(6);

        Button rollButton = activity.findViewById(R.id.die_face);
        rollButton.performClick();

        assertEquals("6", rollButton.getText().toString());

        TextView totalDiceRolledView = activity.findViewById(R.id.total_dice_rolls_score);
        assertEquals("1", totalDiceRolledView.getText().toString());  // Example check
      });
    }
  }

  @Test
  public void TestWithMultiple_predefined_inputs2(){
    try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(WalletActivity.class)) {
      scenario.onActivity(activity -> {
        WalletViewModel viewModel = new ViewModelProvider(activity).get(WalletViewModel.class);

        int[] rolls = {1, 2, 4, 5, 1, 9, 1, 4, 4, 1, 6, 6, 6, 1, 1, 2};
        Button rollButton ;
        for (int roll : rolls) {
          viewModel.setTestValue(roll);
          rollButton = activity.findViewById(R.id.die_face);
          rollButton.performClick();
        }

        TextView totalDiceRolledView = activity.findViewById(R.id.total_dice_rolls_score);
        assertEquals(Integer.toString(rolls.length), totalDiceRolledView.getText().toString());
        TextView score = activity.findViewById(R.id.coins_balance_label);
        assertEquals("15", score.getText().toString());
      });

    }
  }

  @Test
  public void TestWithMultiple_predefined_inputs3(){
    try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(WalletActivity.class)) {
      scenario.onActivity(activity -> {
        WalletViewModel viewModel = new ViewModelProvider(activity).get(WalletViewModel.class);

        int[] rolls = {2, 4, 6, 3, 6, 6, 5, 5, 1, 6, 6, 2, 2, 2, 3, 4, 4, 6, 6, 1};
        Button rollButton;
        for (int roll : rolls) {
          viewModel.setTestValue(roll);
          rollButton = activity.findViewById(R.id.die_face);
          rollButton.performClick();
        }

        TextView totalDiceRolledView = activity.findViewById(R.id.total_dice_rolls_score);
        assertEquals(Integer.toString(rolls.length), totalDiceRolledView.getText().toString());
        TextView score = activity.findViewById(R.id.coins_balance_label);
        assertEquals("30", score.getText().toString());
        TextView sixes_rolled = activity.findViewById(R.id.sixes_rolled_score);
        assertEquals("7", sixes_rolled.getText().toString());
        TextView double_sixes = activity.findViewById(R.id.double_sixes_score);
        assertEquals("3", double_sixes.getText().toString());
        TextView double_others = activity.findViewById(R.id.double_others_score);
        assertEquals("4", double_others.getText().toString());
      });

    }
  }

  @Test
  public void TestWithMultiple_predefined_inputs4() {
    try (ActivityScenario<WalletActivity> scenario = ActivityScenario.launch(WalletActivity.class)) {
      scenario.onActivity(activity -> {
        WalletViewModel viewModel = new ViewModelProvider(activity).get(WalletViewModel.class);

        int[] rolls = {2, 4, 6, 3, 6, 6, 5, 5, 1, 6, 6, 2, 2, 2, 3, 4,
                        4, 6, 6, 1, 4, 6, 1, 3, 1, 1, 3, 5, 2, 6, 6, 6};
        Button rollButton ;
        for (int roll : rolls) {
          viewModel.setTestValue(roll);
          rollButton = activity.findViewById(R.id.die_face);
          rollButton.performClick();
        }

        TextView totalDiceRolledView = activity.findViewById(R.id.total_dice_rolls_score);
        assertEquals(Integer.toString(rolls.length), totalDiceRolledView.getText().toString());
        TextView score = activity.findViewById(R.id.coins_balance_label);
        assertEquals("55", score.getText().toString());
        TextView sixes_rolled = activity.findViewById(R.id.sixes_rolled_score);
        assertEquals("11", sixes_rolled.getText().toString());
        TextView double_sixes = activity.findViewById(R.id.double_sixes_score);
        assertEquals("5", double_sixes.getText().toString());
        TextView double_others = activity.findViewById(R.id.double_others_score);
        assertEquals("5", double_others.getText().toString());
      });

    }
  }
}
