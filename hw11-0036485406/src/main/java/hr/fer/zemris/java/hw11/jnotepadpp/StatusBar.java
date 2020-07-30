package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.GridLayout;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lengthLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel LnColSelLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel clockLabel = new JLabel("", SwingConstants.RIGHT);
	
	public StatusBar() {
		setLayout(new GridLayout(1, 3));
		add(lengthLabel);
		add(LnColSelLabel);
		add(clockLabel);
		
		new Thread(updateClockLabelEverySec).start();
	}
	
	Runnable updateClockLabelEverySec = new Runnable() {
		
		@Override
		public void run() {
			while(true) {
				updateLnClockLabel();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// ignoring exception
				}
			}
		}
	};
	
	private SingleDocumentListener listener = new SingleDocumentListener() {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			// ...
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
		}
	};

	public StatusBar(MultipleDocumentModel model) {
		super(new GridLayout(1, 3));
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel == null) {
					return;
				}
				
				currentModel.addSingleDocumentListener(listener);

				JTextArea editor = currentModel.getTextComponent();
				editor.addCaretListener(new CaretListener() {

					@Override
					public void caretUpdate(CaretEvent e) {
						
					}
				});
			}
		});
	}
	
	private void updateLengthLabel(SingleDocumentModel model) {
		JTextArea editor = model.getTextComponent();
		Document doc = editor.getDocument();
		int len = doc.getLength();
		
		lengthLabel.setText("length:" + len);
	}
	
	private void updateLnColSelLabel(SingleDocumentModel model) {
		JTextArea editor = model.getTextComponent();
		Document doc = editor.getDocument();
		
		String text = null;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
		int numberOfLines = text.split(System.getProperty("line.separator")).length;
		Caret caret = editor.getCaret();
		Point point = caret.getMagicCaretPosition();
		if (point == null) {
			return;
		}			
		
		int column = point.y;
		int sel = Math.abs(caret.getDot() - caret.getMark());
		
		LnColSelLabel.setText("Ln:" + numberOfLines + "  Col:" + column + "  Sel:" + sel);
	}
	
	private void updateLnClockLabel() {
		String clock = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		clockLabel.setText(clock);
	}

}
