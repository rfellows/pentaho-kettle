package org.pentaho.di.ui.spoon;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
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
  TextField urlBar;

  public SpoonJavaFxBrowser( Composite parent ) {
    composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new FillLayout() );

    canvas = new FXCanvas( composite, SWT.NONE );
    init();
  }

  protected void init() {
    Group group = new Group();
    group.setAutoSizeChildren( true );
    webView = new WebView();
    webView.setContextMenuEnabled( true );
    final Button backButton = new Button( "<" );
    backButton.setPrefSize(50, 20);
    final Button forwardButton = new Button( ">" );
    forwardButton.setPrefSize(50, 20);
    final Button goButton = new Button( "Go" );
    goButton.setPrefSize(50, 20);
    final Button copyButton = new Button("Copy");
    goButton.setPrefSize(50, 20);

    urlBar = new TextField();
    urlBar.setPrefSize( 400, 20 );

    final HBox toolbar = new HBox();
    toolbar.setAlignment( Pos.CENTER );
    toolbar.getChildren().add( backButton );
    toolbar.getChildren().add( forwardButton );
    toolbar.getChildren().add( urlBar );
    toolbar.getChildren().add( goButton );
    toolbar.getChildren().add( copyButton );
    toolbar.setPrefSize( 1000, 30 );

    group.getChildren().add( toolbar );
    group.getChildren().add( webView );
    toolbar.setLayoutY( 0 );
    webView.setLayoutY( 30 );


    final Scene scene = new Scene( group );
    canvas.setScene( scene );

    scene.widthProperty().addListener( new ChangeListener<Object>()
    {
      public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
      {
        Double width = (Double) newValue;
        toolbar.setPrefWidth( width.doubleValue() );
        urlBar.setPrefWidth( width.doubleValue() - 4*50 );
        webView.setPrefWidth( width.doubleValue() );
      }
    });
    scene.heightProperty().addListener( new ChangeListener<Object>()
    {
      public void changed(ObservableValue<?> observable, Object oldValue, Object newValue)
      {
        Double width = (Double) newValue;
        webView.setPrefHeight( width.doubleValue() - toolbar.getPrefHeight() );
      }
    });

    backButton.setOnAction(event -> {
      WebHistory history = webView.getEngine().getHistory();
      if ( history.getCurrentIndex() > 0 ) {
        history.go( -1 );
        urlBar.setText( webView.getEngine().getLocation() );
      }
    });

    forwardButton.setOnAction( event -> {
      WebHistory history = webView.getEngine().getHistory();
      if ( history.getCurrentIndex() < history.getEntries().size() - 1 ) {
        history.go( 1 );
        urlBar.setText( webView.getEngine().getLocation() );
      }
    });

    goButton.setOnAction( event -> webView.getEngine().load( urlBar.getText() ));

    copyButton.setOnAction( event -> {
      String copiedText = (String) webView.getEngine().executeScript( "window.getSelection().toString()" );
      final Clipboard systemClipboard = Clipboard.getSystemClipboard();
      final ClipboardContent clip = new ClipboardContent();
      clip.putString( copiedText );
      systemClipboard.setContent( clip );
    } );

  }

  public Control getControl() {
    return composite;
  }

  public void setUrl( String url ) {
    urlBar.setText( url );
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
