package androidsamples.java.dicegames;

import java.util.Random;

/**
 * An implementation of a six-faced {@link Die} using {@link Random}.
 */
public class Die6 implements Die {
  private static final int faces = 6;
  private Random face;
  private int faceVal;
  public Die6() {
    // TODO implement method
    face = new Random();

  }

  @Override
  public void roll() {
    // TODO implement method
    faceVal = face.nextInt(faces) + 1;
  }

  @Override
  public int value() {
    // TODO implement method
    return faceVal;
  }
}
