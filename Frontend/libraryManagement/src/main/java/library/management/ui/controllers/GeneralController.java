package library.management.ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class GeneralController {
  protected void translate(Node node, double x, Duration duration) {
    TranslateTransition transition = new TranslateTransition(duration, node);
    transition.setByX(x);
    transition.setInterpolator(Interpolator.EASE_BOTH);
    transition.play();
  }

  protected void fade(Node node, double fromValue, double toValue, Duration duration) {
    FadeTransition fade = new FadeTransition(duration, node);
    fade.setFromValue(fromValue);
    fade.setToValue(toValue);
    fade.setInterpolator(Interpolator.EASE_BOTH);
    fade.play();
  }

  protected void transFade(Node pane, double translateX, double fromValue, double toValue, Duration duration) {
    translate(pane, translateX, duration);
    fade(pane, fromValue, toValue, duration);
  }
  protected void transFade(Node pane, double translateX, double fromValue, double toValue, Duration duration,String style) {
    translate(pane, translateX, duration);
    fade(pane, fromValue, toValue, duration);
  }
}
