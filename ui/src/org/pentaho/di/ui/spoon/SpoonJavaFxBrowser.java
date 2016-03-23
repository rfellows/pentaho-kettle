package org.pentaho.di.ui.spoon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.di.core.EngineMetaInterface;
import org.pentaho.di.core.exception.KettleException;

/**
 * Created by rfellows on 3/23/16.
 */
public class SpoonJavaFxBrowser implements TabItemInterface {

  FXCanvas canvas;
  WebView webView;
  Composite composite;

  public SpoonJavaFxBrowser( Composite parent ) {
    composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new FillLayout() );

    canvas = new FXCanvas( composite, SWT.NONE );
    init();
  }

  protected void init() {
    Group group = new Group();
    final Scene scene = new Scene( group );
    webView = new WebView();
    group.getChildren().add( webView );
    webView.setContextMenuEnabled( true );
    final Button backButton = new Button( "Back" );
    final Button forwardButton = new Button( "Forward" );
    final TextField urlBar = new TextField();
    group.getChildren().add( backButton );

    canvas.setScene( scene );

    scene.widthProperty().addListener( new ChangeListener<Object>()
    {
      public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
      {
        Double width = (Double) newValue;
        webView.setPrefWidth( width.doubleValue() );
      }
    });
    scene.heightProperty().addListener( new ChangeListener<Object>()
    {
      public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
      {
        Double width = (Double) newValue;
        webView.setPrefHeight( width.doubleValue() );
      }
    });

  }

  public Control getControl() {
    return composite;
  }

  public void setUrl(String url ) {
    webView.getEngine().load( url );
  }

  @Override
  public boolean canBeClosed() {
    return false;
  }

  @Override
  public boolean canHandleSave() {
    return false;
  }

  @Override
  public Object getManagedObject() {
    return null;
  }

  @Override
  public boolean hasContentChanged() {
    return false;
  }

  @Override
  public ChangedWarningInterface getChangedWarning() {
    return null;
  }

  @Override
  public int showChangedWarning() throws KettleException {
    return 0;
  }

  @Override
  public boolean applyChanges() throws KettleException {
    return false;
  }

  @Override
  public EngineMetaInterface getMeta() {
    return null;
  }

  @Override
  public void setControlStates() {

  }

  @Override
  public boolean setFocus() {
    return false;
  }
}
